package com.github.atomishere.atomrpg.player;

import com.github.atomishere.atomrpg.attribute.AtomAttribute;
import com.github.atomishere.atomrpg.attribute.BaseModifier;
import com.github.atomishere.atomrpg.attribute.CustomAttributeInstance;
import com.github.atomishere.atomrpg.utils.MathUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PlayerAttributeInstance implements CustomAttributeInstance {
    private final Map<String, BaseModifier> modifiers = new HashMap<>();
    private final AtomAttribute attribute;
    private final AtomPlayer player;

    public PlayerAttributeInstance(AtomAttribute attribute, AtomPlayer player) {
        this.attribute = attribute;
        this.player = player;
    }

    @Override
    public @NotNull AtomAttribute getAttribute() {
        return attribute;
    }

    @Override
    public double getBaseValue() {
        return player.getData().getBaseValue(attribute);
    }

    @Override
    // TODO: Add item attributes
    public double getValue() {
        double base = getBaseValue();
        double scalar = 1;

        for(BaseModifier modifier : modifiers.values()) {
            switch (modifier.operation()) {
                case ADDITION -> base += modifier.value();
                case SUBTRACTION -> base -= modifier.value();
                case MULTIPLICATION -> scalar *= modifier.value();
                case DIVISION -> scalar /= modifier.value();
            }
        }

        return MathUtils.clamp(base * scalar, attribute.getMin(), attribute.getMax());
    }

    @Override
    public void setBaeValue(double value) {
        player.getData().setBaseValue(attribute, value);
    }

    @Override
    public void addModifier(BaseModifier modifier) {
        modifiers.put(modifier.id(), modifier);
    }

    @Override
    public void removeModifier(String id) {
        modifiers.remove(id);
    }
}
