package com.github.atomishere.atomrpg.attribute;

import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;

// Adds the ability to add custom slots to add attributes to later on down the road.
public enum ItemSlot {
    HAND(EquipmentSlot.HAND),
    OFF_HAND(EquipmentSlot.OFF_HAND),
    FEET(EquipmentSlot.FEET),
    LEGS(EquipmentSlot.LEGS),
    CHEST(EquipmentSlot.CHEST),
    HEAD(EquipmentSlot.HEAD);

    @Nullable
    private final EquipmentSlot mapsTo;

    ItemSlot(EquipmentSlot mapsTo) {
        this.mapsTo = mapsTo;
    }

    public static Optional<ItemSlot> convertFrom(EquipmentSlot slot) {
        Optional<EquipmentSlot> compare = Optional.of(slot);

        return Arrays.stream(values())
                .filter(is -> is.convertTo().isPresent() && is.convertTo().equals(compare))
                .findAny();
    }

    public Optional<EquipmentSlot> convertTo() {
        if(mapsTo == null) {
            return Optional.empty();
        } else {
            return Optional.of(mapsTo);
        }
    }
}
