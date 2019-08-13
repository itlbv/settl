package com.itlbv.settl.ui.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.itlbv.settl.Game;

public class UiUtil {
    public static Vector2 screenToWorldCoord(int screenX, int screenY) {
        Vector3 pos= new Vector3(screenX, screenY, 0);
        Game.camera.unproject(pos);
        return new Vector2(pos.x, pos.y);
    }
}
