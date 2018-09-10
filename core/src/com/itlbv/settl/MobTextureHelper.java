package com.itlbv.settl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.enums.GameObjectType;

public class MobTextureHelper {

    private static Texture man01 = new Texture("textures/mobs/human/man01.png");

    public static Texture getMobTexture(GameObjectType type) { //TODO cast to MobObjectType
        TextureRegion[] allFrames = TextureRegion.split(man01, 20, 20)[0];
        TextureRegion idleFrame = allFrames[0];
        TextureRegion[] walkFrames = {allFrames[0],
                                      allFrames[1],
                                      allFrames[0],
                                      allFrames[2]};
        TextureRegion[] attackFrames = {allFrames[3],
                                        allFrames[4],
                                        allFrames[5]};
        TextureRegion[] gotHit = {allFrames[6],
                                  allFrames[7]};
        TextureRegion deadFrame = allFrames[8];
        return null;
    }
}
