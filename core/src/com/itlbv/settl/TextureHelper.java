package com.itlbv.settl;

import com.badlogic.gdx.graphics.Texture;
import com.itlbv.settl.enums.GameObjectType;
import com.itlbv.settl.enums.MapObjectType;
import com.itlbv.settl.enums.MobType;

public class TextureHelper {
    public static Texture getTexture(GameObjectType type) {
        if (type instanceof MobType) {
            return MobTextureHelper.getMobTexture(type);
        } else if (type instanceof MapObjectType) {
            return null; //TODO boilerplate
        }
        return null;
    }
}
