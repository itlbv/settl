package com.itlbv.settl.enumsStateMachine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.itlbv.settl.Game;
import com.itlbv.settl.mobs.MobTextureHelper;
import com.itlbv.settl.mobs.Mob;

public enum MobState implements State<Mob> {
    IDLE(){
        @Override
        public void update(Mob mob) {
            if (Gdx.input.isKeyPressed(Input.Keys.T)) {
                mob.setTarget(Game.mobs.get(1));
                mob.getStateMachine().changeState(APPROACHING);
                mob.setAnimation(MobTextureHelper.getAnimation(mob.getType(), APPROACHING));
            }
        }
    },
    APPROACHING(){};

    @Override
    public void enter(Mob entity) {

    }

    @Override
    public void update(Mob entity) {

    }

    @Override
    public void exit(Mob entity) {

    }

    @Override
    public boolean onMessage(Mob entity, Telegram telegram) {
        return false;
    }
}
