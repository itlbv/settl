package com.itlbv.settl.mobs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.enumsStateMachine.MobState;

public class MobFactory {

    public static Human createHuman(float x, float y) {
        TextureRegion texture = MobTextureHelper.getTexture(MobObjectType.HUMAN, MobState.IDLE);
        return new Human(x, y, texture);
    }
}
