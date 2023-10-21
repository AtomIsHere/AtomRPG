package com.github.atomishere.atomrpg.skills;

public class SkillInstance {
    private final Skill skill;

    private long level = 0;
    private double xp = 0;

    public SkillInstance(Skill skill) {
        this.skill = skill;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public double getXp() {
        return xp;
    }

    public void setXp(double xp) {
        double levelUpXp = skill.xpRequiredForLevel(level + 1);

        if(xp > levelUpXp) {
            this.level += 1;
            this.xp = xp - levelUpXp;
        } else {
            this.xp = xp;
        }
    }

    public void addXp(double add) {
        setXp(this.xp + add);
    }
}
