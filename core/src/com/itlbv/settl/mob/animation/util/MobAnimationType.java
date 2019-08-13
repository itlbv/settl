package com.itlbv.settl.mob.animation.util;

public enum MobAnimationType {
    IDLE(true),
    WALK(true),
    ATTACK(false),
    GET_HIT(false),
    DEAD(true);

    private boolean looping;

    MobAnimationType(boolean looping) {
        this.looping = looping;
    }

    public boolean isLooping() {
        return looping;
    }
}
