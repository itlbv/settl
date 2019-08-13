package com.itlbv.settl.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.Game;
import com.itlbv.settl.ui.util.UiUtil;
import com.itlbv.settl.util.GameUtil;

public class Input extends InputAdapter {

    private UiStage uiStage;

    public Input(UiStage uiStage) {
        this.uiStage = uiStage;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 clickWorldCoord = UiUtil.screenToWorldCoord(screenX, screenY);
        switch (button) {
            case 0:         //LEFT BUTTON
                uiStage.leftMouseClick(clickWorldCoord);
                break;
            case 1:         //RIGHT BUTTON
                uiStage.rightMouseClick(clickWorldCoord);
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        switch (character) {
            case '\b': uiStage.debugMode = !uiStage.debugMode; break;// BACKSPACE
            case 't': uiStage.routeDrawing =! uiStage.routeDrawing; break;
            case 'x': GameUtil.increaseGameSpeed(); break;
            case 'z': GameUtil.decreaseGameSpeed(); break;
            case ' ': GameUtil.changePauseState(); break;// SPACEBAR
        }
        return true;
    }

    public void updateCameraPosition() {
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) {
            Game.camera.translate(0, 1);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)) {
            Game.camera.translate(-1, 0);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) {
            Game.camera.translate(0, -1);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) {
            Game.camera.translate(1, 0);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.Q)) {
            Game.camera.zoom += .02;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.E)) {
            Game.camera.zoom -= .02;
        }
    }
}
