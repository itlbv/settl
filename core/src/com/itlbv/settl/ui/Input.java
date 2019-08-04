package com.itlbv.settl.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.itlbv.settl.Game;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.util.GameUtil;

public class Input extends InputAdapter {

    private OrthographicCamera camera;
    private UiStage uiStage;

    public Input(OrthographicCamera camera, UiStage uiStage) {
        this.camera = camera;
        this.uiStage = uiStage;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (button) {
            case 0:         //LEFT BUTTON
                selectObject(screenX, screenY);
                break;
            case 1:         //RIGHT BUTTON
                doAction();
                break;
            default:
                return false;
        }
        return true;
    }

    private void selectObject(int screenX, int screenY) {
        Vector3 clickCoord = new Vector3(screenX, screenY, 0);
        camera.unproject(clickCoord);
        for (Mob mob : Game.mobs) {
            Rectangle selectingRect = mob.getSelectingRectangle();
            if (selectingRect.contains(clickCoord.x, clickCoord.y)) {
                uiStage.selectedObject = mob;
                uiStage.labelSelectedMob.setText(mob.toString());
                break;
            }
        }
    }

    private void doAction() {

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
            camera.translate(0, 1);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)) {
            camera.translate(-1, 0);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) {
            camera.translate(0, -1);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) {
            camera.translate(1, 0);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.Q)) {
            camera.zoom += .02;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.E)) {
            camera.zoom -= .02;
        }
    }
}
