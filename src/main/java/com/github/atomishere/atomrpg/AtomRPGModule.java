package com.github.atomishere.atomrpg;

import co.aikar.commands.PaperCommandManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Names;

import java.io.File;

public class AtomRPGModule extends AbstractModule {
    private final AtomRPG plugin;
    private final PaperCommandManager commandManager;

    private final File dataFolder;

    public AtomRPGModule(AtomRPG plugin, PaperCommandManager commandManager, File dataFolder) {
        this.plugin = plugin;
        this.commandManager = commandManager;
        this.dataFolder = dataFolder;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        bind(AtomRPG.class).toInstance(plugin);
        bind(PaperCommandManager.class).toInstance(commandManager);

        bind(File.class).annotatedWith(Names.named("ConfigFolder")).toInstance(dataFolder);
        bind(File.class).annotatedWith(Names.named("PlayerDataFolder"))
                .toInstance(new File(dataFolder.getAbsoluteFile(), "players"));
    }

    @Provides
    public Gson getGson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }
}
