package com.github.atomishere.atomrpg.skills;

import com.github.atomishere.atomrpg.AtomRPG;
import com.github.atomishere.atomrpg.player.PlayerManager;
import com.github.atomishere.atomrpg.service.Service;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class SkillManager implements Service, Listener {
    private static final Logger log = Logger.getLogger(SkillManager.class.getName());

    @Inject
    private PlayerManager playerManager;
    @Inject
    private AtomRPG plugin;

    private final Map<Material, Double> miningXpTable = new HashMap<>();

    @Override
    public void start() {
        plugin.loadConfig("mining_xp_table.yml");

        File miningXpTableFile = new File(plugin.getDataFolder(), "mining_xp_table.yml");
        if(miningXpTableFile.exists()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(miningXpTableFile);
            config.getKeys(false)
                    .stream()
                    .filter(config::isDouble)
                    .forEach(key -> {
                        try {
                            miningXpTable.put(Material.valueOf(key), config.getDouble(key));
                        } catch (IllegalArgumentException ex) {
                            log.log(Level.WARNING, "Invalid material in mining_xp_table.yml: " + key);
                        }
                    });
        } else {
            log.severe("mining_xp_table.yml not loaded");
        }
    }

    @Override
    public void stop() {
        miningXpTable.clear();
    }

    @Override
    public List<Class<? extends Service>> getDependencies() {
        return List.of(PlayerManager.class);
    }

    // TODO: Handle announcing skill xp gain
    public void addPlayerSkillXp(Skill skill, Player player, Double xp) {
        playerManager.getPlayerData(player.getUniqueId()).ifPresent(pd -> pd.getSkillData(skill).addXp(xp));
    }

    // TODO: Check entity level once custom entites are introduced
    @EventHandler
    public void onPlayerKillEntity(EntityDeathEvent deathEvent) {
        Player killer = deathEvent.getEntity().getKiller();
        if(killer != null) {
            addPlayerSkillXp(Skill.COMBAT, killer, 10.0D);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        addPlayerSkillXp(Skill.MINING, event.getPlayer(), miningXpTable.getOrDefault(event.getBlock().getType(), 0.0D));
    }
}
