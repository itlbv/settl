package com.itlbv.settl.mob.util;

public enum MobType {
    PEASANT(4, 3),
    KNIGHT(4, 3),
    ORC(4, 3),;

    private int speed, hitpoints;

    MobType(int speed, int hitpoints) {
        this.speed = speed;
        this.hitpoints = hitpoints;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHitpoints() {
        return hitpoints;
    }
}
