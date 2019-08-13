package com.itlbv.settl.mob.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.mob.animation.util.AnimationUtil;
import com.itlbv.settl.mob.animation.util.MobAnimationType;
import com.itlbv.settl.mob.util.MobType;
import lombok.Data;

@Data
class MobAnimation {
    private Animation<TextureRegion> animation;
    private MobAnimationType type;

    MobAnimation(MobType mobType, MobAnimationType animationType) {
        animation = AnimationUtil.getAnimation(mobType, animationType);
        this.type = animationType;
    }
}
