package com.itlbv.settl.ui.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.itlbv.settl.Game;

public class UiUtil {
    public static Vector2 screenToWorldCoord(int screenX, int screenY) {
        Vector3 pos = new Vector3(screenX, screenY, 0);
        Game.camera.unproject(pos);
        return new Vector2(pos.x, pos.y);
    }

    public static Vector2 worldToScreenCoordWithDebugCamera(Vector2 worldPosition, OrthographicCamera debugCamera) {
        Vector3 screenPosition = new Vector3(worldPosition.x, worldPosition.y, 0);
        Game.camera.project(screenPosition, 0, 0, debugCamera.viewportWidth, debugCamera.viewportHeight);
        return new Vector2(screenPosition.x, screenPosition.y);
    }

    public static String vectorToString(Vector2 vector) {
        String x = String.format("%.3f", vector.x);
        String y = String.format("%.3f", vector.y);
        return (x + "; " + y);
    }
}
