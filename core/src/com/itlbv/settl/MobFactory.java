package com.itlbv.settl;

import com.badlogic.gdx.graphics.Texture;
import com.itlbv.settl.mobs.Human;

public class MobFactory {

    public static Human createHuman(float x, float y) {
        Texture texture = new Texture("man1.png");
        return new Human(x, y, texture);
    }
}
