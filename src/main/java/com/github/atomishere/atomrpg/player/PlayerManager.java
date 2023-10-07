package com.github.atomishere.atomrpg.player;

import com.github.atomishere.atomrpg.attribute.AtomAttribute;
import com.github.atomishere.atomrpg.service.Service;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class PlayerManager implements Service, Listener {
    private static final Logger log = Logger.getLogger(PlayerManager.class.getName());

    // TODO: Move this to a config value eventually
    private static final ImmutableMap<AtomAttribute, Double> DEFAULT_ATTRIBUTES = ImmutableMap.<AtomAttribute, Double>builder()
            .put(AtomAttribute.DEFENCE, 5.0D)
            .put(AtomAttribute.STRENGTH, 10.0D)
            .put(AtomAttribute.CRIT_CHANCE, 10.0D)
            .put(AtomAttribute.CRIT_DAMAGE, 20.0D)
            .put(AtomAttribute.INTELLIGENCE, 100.0D)
            .build();

    private final Map<UUID, AtomPlayer> players = new HashMap<>();
    private final Map<UUID, PlayerData> playersData = new HashMap<>();

    @Inject
    @Named("PlayerDataFolder")
    private File playerDataFolder;

    @Inject
    private Gson gson;

    @Override
    public void start() {
        if(!playerDataFolder.exists() || !playerDataFolder.isDirectory()) {
            playerDataFolder.mkdir();
        }

        for(File jsonFile : playerDataFolder.listFiles()) {
            if(jsonFile.isFile()) {
                try(JsonReader reader = new JsonReader(new FileReader(jsonFile))) {
                    PlayerData playerData = gson.fromJson(reader, PlayerData.class);

                    playersData.put(playerData.getUuid(), playerData);
                } catch (IOException e) {
                    log.log(Level.WARNING, "Could not serialize json player file: " + jsonFile.getName(), e);
                }
            }
        }
    }

    @Override
    public void stop() {
        for(PlayerData data : playersData.values()) {
            File jsonFile = new File(playerDataFolder, data.getUuid().toString() + ".json");

            try(FileWriter writer = new FileWriter(jsonFile)) {
                gson.toJson(data, writer);
            } catch (IOException e) {
                log.log(Level.WARNING, "Unable to save player data for uuid: " + data.getUuid(), e);
            }

            try {
                jsonFile.createNewFile();
            } catch (IOException e) {
                log.log(Level.WARNING, "Unable to create file: " + jsonFile.getName(), e);
            }
        }

        players.clear();
        playersData.clear();
    }

    @NotNull
    public Optional<AtomPlayer> getPlayer(UUID player) {
        if(!players.containsKey(player)) {
            return Optional.empty();
        }

        return Optional.of(players.get(player));
    }

    public Optional<PlayerData> getPlayerData(UUID player) {
        if(!playersData.containsKey(player)) {
            return Optional.empty();
        }

        return Optional.of(playersData.get(player));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID playerUid = event.getPlayer().getUniqueId();

        players.put(playerUid, new AtomPlayer(playerUid, getPlayerData(playerUid).orElseGet(() -> {
            PlayerData data = new PlayerData(playerUid);
            DEFAULT_ATTRIBUTES.forEach(data::setBaseValue);

            playersData.put(playerUid, data);
            return data;
        })));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        players.remove(event.getPlayer().getUniqueId());
    }
}

