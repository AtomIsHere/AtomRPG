package com.github.atomishere.atomrpg.attribute;

import org.jetbrains.annotations.NotNull;

public interface CustomAttributable {
    @NotNull
    CustomAttributeInstance getAttributeInstance(AtomAttribute attribute);
}
