package com.github.atomishere.atomrpg.player;

import com.github.atomishere.atomrpg.attribute.AtomAttribute;
import com.github.atomishere.atomrpg.skills.Skill;
import com.github.atomishere.atomrpg.skills.SkillInstance;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {
    private final UUID uuid;

    private final Map<AtomAttribute, Double> baseAttributes = new HashMap<>();
    private final Map<Skill, SkillInstance> playerSkills = new HashMap<>();

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

    public Double getBaseValue(AtomAttribute attribute) {
        return baseAttributes.getOrDefault(attribute, 0.0D);
    }

    public void setBaseValue(AtomAttribute attribute, Double value) {
        baseAttributes.put(attribute, value);
    }

    public UUID getUuid() {
        return uuid;
    }

    @NotNull
    public SkillInstance getSkillData(Skill skill) {
        if(!playerSkills.containsKey(skill)) {
            playerSkills.put(skill, new SkillInstance(skill));
        }

        return playerSkills.get(skill);
    }
}
