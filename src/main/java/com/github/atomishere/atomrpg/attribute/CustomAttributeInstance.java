package com.github.atomishere.atomrpg.attribute;

import org.jetbrains.annotations.NotNull;

public interface CustomAttributeInstance {
    @NotNull
    AtomAttribute getAttribute();

    double getBaseValue();
    double getValue();

    void setBaeValue(double value);

    void addModifier(BaseModifier modifier);
    void removeModifier(String id);
}
