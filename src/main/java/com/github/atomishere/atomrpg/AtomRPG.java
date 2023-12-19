package com.github.atomishere.atomrpg;

import co.aikar.commands.PaperCommandManager;
import com.github.atomishere.atomrpg.player.PlayerManager;
import com.github.atomishere.atomrpg.service.ServiceManager;
import com.github.atomishere.atomrpg.skills.SkillManager;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import io.github.classgraph.ClassGraph;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Singleton
public class AtomRPG extends JavaPlugin {
    private Injector injector;

    @Inject
    private ServiceManager serviceManager;

    @Inject
    private PlayerManager playerManager;
    @Inject
    private SkillManager skillManager;

    @Override
    public void onLoad() {
        ClassGraph.CIRCUMVENT_ENCAPSULATION = ClassGraph.CircumventEncapsulationMethod.NARCISSUS;

        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
    }

    @Override
    public void onEnable() {
        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.enableUnstableAPI("brigadier");
        commandManager.enableUnstableAPI("help");

        AtomRPGModule module = new AtomRPGModule(this, commandManager, getDataFolder());
        injector = module.createInjector();
        injector.injectMembers(this);

        serviceManager.addService(playerManager);
        serviceManager.addService(skillManager);

        serviceManager.startServices();
    }

    @Override
    public void onDisable() {
        serviceManager.stopServices();

        injector = null;
    }

    public void loadConfig(String file) {
        File configFile = new File(getDataFolder(), file);
        if (!configFile.exists()) {
            saveResource(file, false);
        }
    }
}
