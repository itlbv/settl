package com.itlbv.settl.mobs.utils;

public enum MobState {
    IDLE(false),
    WALK(false),
    ATTACK(true),
    GOT_HIT(true),
    DEAD(false);

    private boolean oneTime;

    MobState(boolean oneTime) {
        this.oneTime = oneTime;
    }

    public boolean isOneTime() {
        return oneTime;
    }
}
