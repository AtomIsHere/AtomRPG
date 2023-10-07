package com.github.atomishere.atomrpg;

import co.aikar.commands.PaperCommandManager;
import com.github.atomishere.atomrpg.service.ServiceManager;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.github.classgraph.ClassGraph;
import org.bukkit.plugin.java.JavaPlugin;

public class AtomRPG extends JavaPlugin {
    private Injector injector;

    @Inject
    private ServiceManager serviceManager;

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

        serviceManager.startServices();
    }

    @Override
    public void onDisable() {
        serviceManager.stopServices();

        injector = null;
    }
}
