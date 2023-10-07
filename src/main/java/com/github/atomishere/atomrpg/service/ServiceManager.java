package com.github.atomishere.atomrpg.service;

import com.github.atomishere.atomrpg.AtomRPG;
import com.github.atomishere.atomrpg.service.graph.Graph;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Singleton
public class ServiceManager {
    private static final Logger log = Logger.getLogger(ServiceManager.class.getName());

    private List<Service> services = new ArrayList<>();

    @Inject
    private AtomRPG plugin;

    private boolean started = false;

    public void addService(Service service) {
        if(services.contains(service)) {
            throw new RuntimeException("Tried to register " + service.getClass().getSimpleName() + " twice");
        }

        services.add(service);
    }

    @NotNull
    public List<Service> getServices() {
        return services;
    }

    public boolean isStarted() {
        return started;
    }

    public void startServices() {
        if(!checkDependencies()) {
            return;
        }

        if(isStarted()) {
            return;
        }

        for(Service service : services) {
            log.finer("enable " + service.getClass().getSimpleName());

            try {
                service.start();
            } catch(Exception ex) {
                log.severe("error while starting " + service.getClass().getSimpleName());
                ex.printStackTrace();
                return;
            }

            if(service instanceof Listener) {
                Bukkit.getServer().getPluginManager().registerEvents((Listener) service, plugin);
            }
        }

        started = true;
    }

    public void stopServices() {
        if(!isStarted()) {
            return;
        }

        for(Service service : services) {
            log.finer("disable " + service.getClass().getSimpleName());

            if(service instanceof Listener) {
                HandlerList.unregisterAll((Listener) service);
            }

            try {
                service.stop();
            } catch(Exception ex) {
                log.severe("error while stopping " + service.getClass().getSimpleName());
                ex.printStackTrace();
                return;
            }
        }

        started = false;
    }

    private boolean checkDependencies() {
        List<Class<? extends Service>> orderedServices = new ArrayList<>();
        List<Class<? extends Service>> added = new ArrayList<>();
        try {
            Graph<Class<? extends Service>> graph = new Graph<>(orderedServices::add);

            for(Service service : getServices()) {
                for(Class<? extends Service> dependency : service.getDependencies()) {
                    if(dependency.equals(service.getClass())) {
                        log.severe(service.getClass().getName() + " tried to depend on itself...");
                        continue;
                    }
                    graph.addDependency(service.getClass(), dependency);

                    added.add(service.getClass());
                    added.add(dependency);

                    if(!services.stream().anyMatch(s -> s.getClass().equals(dependency))) {
                        log.severe("Could not find dependency " + dependency.getName() + " for service " + service.getClass().getName());
                        return false;
                    }
                }
            }

            for(Service service : getServices()) {
                if(!added.contains(service.getClass())) {
                    orderedServices.add(service.getClass());
                }
            }
            added.clear();

            if(graph.size() != 0) {
                graph.generateDependencies();
            }
        } catch(RuntimeException ex) {
            log.severe("error while trying to generate dependency graph: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }

        if(services.size() != orderedServices.size()) {
            throw new RuntimeException("Ordered service does not have the same size as main services list");
        }

        Collections.reverse(orderedServices);
        services = orderedServices.stream()
                .map(sc -> services.stream()
                        .filter(s -> s.getClass().equals(sc))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("This shouldn't happen...")))
                .collect(Collectors.toList());

        return true;
    }
}
