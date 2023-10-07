package com.github.atomishere.atomrpg.player;

import com.github.atomishere.atomrpg.attribute.AtomAttribute;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {
    private final UUID uuid;

    private final Map<AtomAttribute, Double> baseAttributes = new HashMap<>();

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

    public Double getBaseValue(AtomAttribute attribute) {
        return baseAttributes.getOrDefault(attribute, 0.0D);
    }

    public void setBaseValue(AtomAttribute attribute, Double value) {
        baseAttributes.put(attribute, value);
    }

    public UUID getUuid() {
        return uuid;
    }
}
