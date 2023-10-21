package com.github.atomishere.atomrpg.skills;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public enum Skill {
    COMBAT("Combat", NamedTextColor.RED, 50, 50,  50, 3),
    MINING("Mining", NamedTextColor.GREEN, 50, 50, 50, 3),
    FORAGING("Foraging", NamedTextColor.GREEN, 50, 50, 50, 3),
    ENCHANTING("Enchanting", NamedTextColor.DARK_PURPLE, 50, 50, 50, 3),
    ALCHEMY("Alchemy", NamedTextColor.LIGHT_PURPLE, 50, 50, 50, 3);

    private final String displayName;
    private final TextColor displayColor;

    private final long maxLevel;
    private final long initialXp;

    private final double percentIncrease;
    private final long levelsUntilIncrease;

    Skill(String displayName, TextColor displayColor, int maxLevel, int initialXp, int percentIncrease, int levelsUntilIncrease) {
        this.displayName = displayName;
        this.displayColor = displayColor;
        this.maxLevel = maxLevel;
        this.initialXp = initialXp;
        this.percentIncrease = percentIncrease;
        this.levelsUntilIncrease = levelsUntilIncrease;
    }

    public String getDisplayName() {
        return displayName;
    }

    public TextColor getDisplayColor() {
        return displayColor;
    }

    public double getMaxLevel() {
        return maxLevel;
    }

    public long xpRequiredForLevel(int level) {
        double gradient = initialXp*Math.pow(1+(percentIncrease / 100.0D), Math.floorDiv(level-1, levelsUntilIncrease));

        return Math.round(gradient * (level-1)+initialXp);
    }
}
