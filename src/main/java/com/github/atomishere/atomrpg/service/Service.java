package com.github.atomishere.atomrpg.service;

import java.util.Collections;
import java.util.List;

public interface Service {
    void start();
    void stop();

    default List<Class<? extends Service>> getDependencies() {
        return Collections.emptyList();
    }
}
