package com.itlbv.settl.mobs.utils;

public enum MobState {
    IDLE(false),
    WALK(true),
    ATTACK(false),
    GOT_HIT(false),
    DEAD(false);

    private boolean looping;

    MobState(boolean looping) {
        this.looping = looping;
    }

    public boolean isLooping() {
        return looping;
    }
}
