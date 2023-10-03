package com.github.atomishere.atomrpg.attribute;

public record BaseModifier(String name, Operation operation, /* this is ignored in some contexts, such as adding a base player value */ ItemSlot slot, double value) {
}
