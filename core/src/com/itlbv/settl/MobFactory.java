package com.itlbv.settl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.enums.MobObjectType;
import com.itlbv.settl.enumsStateMachine.MobState;
import com.itlbv.settl.mobs.Human;

public class MobFactory {

    public static Human createHuman(float x, float y) {
        TextureRegion texture = MobTextureHelper.getTexture(MobObjectType.HUMAN, MobState.IDLE);
        return new Human(x, y, texture);
    }
}
