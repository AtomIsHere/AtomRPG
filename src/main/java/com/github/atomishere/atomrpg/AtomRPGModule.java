package com.github.atomishere.atomrpg;

import co.aikar.commands.PaperCommandManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;

public class AtomRPGModule extends AbstractModule {
    private final AtomRPG plugin;
    private final PaperCommandManager commandManager;

    public AtomRPGModule(AtomRPG plugin, PaperCommandManager commandManager) {
        this.plugin = plugin;
        this.commandManager = commandManager;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        bind(AtomRPG.class).toInstance(plugin);
        bind(PaperCommandManager.class).toInstance(commandManager);
    }

    @Provides
    public Gson getGson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }
}
