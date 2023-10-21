package com.github.atomishere.atomrpg.utils;

public final class MathUtils {
    private MathUtils() {
        throw new AssertionError();
    }

    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}
