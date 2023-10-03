package com.github.atomishere.atomrpg.attribute;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public enum AtomAttribute {
    DEFENCE("Defence", NamedTextColor.GREEN, 0.0D, Double.MAX_VALUE),
    STRENGTH("Strength", NamedTextColor.RED, 0.0D, Double.MAX_VALUE),
    INTELLIGENCE("Intelligence", NamedTextColor.AQUA, 0.0D, Double.MAX_VALUE),
    CRIT_DAMAGE("Crit Damage", NamedTextColor.BLUE, 0.0D, Double.MAX_VALUE),
    CRIT_CHANCE("Crit Chance", NamedTextColor.BLUE, 0.0D, 100.0D);

    private final String displayName;
    private final TextColor displayColor;

    private final double min;
    private final double max;

    AtomAttribute(String displayName, TextColor displayColor, double min, double max) {
        this.displayName = displayName;
        this.displayColor = displayColor;
        this.min = min;
        this.max = max;
    }

    public String getDisplayName() {
        return displayName;
    }

    public TextColor getDisplayColor() {
        return displayColor;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}
