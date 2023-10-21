package com.github.atomishere.atomrpg.player;

import com.github.atomishere.atomrpg.attribute.AtomAttribute;
import com.github.atomishere.atomrpg.attribute.CustomAttributable;
import com.github.atomishere.atomrpg.attribute.CustomAttributeInstance;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AtomPlayer implements CustomAttributable {
    private final UUID player;
    private final PlayerData data;

    private final Map<AtomAttribute, CustomAttributeInstance> attributes = new HashMap<>();

    public AtomPlayer(UUID player, PlayerData data) {
        this.player = player;
        this.data = data;
    }

    public UUID getPlayer() {
        return player;
    }

    public PlayerData getData() {
        return data;
    }

    @Override
    public @NotNull CustomAttributeInstance getAttributeInstance(AtomAttribute attribute) {
        if(!attributes.containsKey(attribute)) {
            attributes.put(attribute, new PlayerAttributeInstance(attribute, this));
        }

        return attributes.get(attribute);
    }
}
