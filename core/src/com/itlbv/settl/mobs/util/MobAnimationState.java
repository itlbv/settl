package com.itlbv.settl.mobs.util;

public enum MobAnimationState {
    IDLE(false),
    WALK(false),
    ATTACK(true),
    GOT_HIT(true),
    DEAD(false);

    private boolean oneTime;

    MobAnimationState(boolean oneTime) {
        this.oneTime = oneTime;
    }

    public boolean isOneTime() {
        return oneTime;
    }
}
