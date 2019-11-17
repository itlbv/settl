package com.itlbv.settl.mob.movement.util;

import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.Target;
import com.itlbv.settl.ui.util.UiUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class Destination extends Target {
    private Vector2 position;

    public String toString() {
        return UiUtil.vectorToString(position);
    }
}
