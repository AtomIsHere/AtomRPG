package com.github.atomishere.atomrpg.player;

import java.util.UUID;

public class AtomPlayer {
    private final UUID player;
    private final PlayerData data;

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
}
