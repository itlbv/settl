package com.itlbv.settl.util;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input.Keys;

public class MouseKeyboardInput extends InputAdapter{

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    private boolean zoomIn;
    private boolean zoomOut;

    public boolean debugMode = true;
    public boolean drawPath = true;

    public float mouseX;
    public float mouseY;

    private OrthographicCamera camera;
    public MouseKeyboardInput(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void handleInput() {
        updateCameraPosition();
    }

    private void updateCameraPosition() {
        int directionX = 0;
        int directionY = 0;
        int cameraSpeed = 1;

        if(down) directionY = -1 ;
        if(up) directionY = 1 ;
        if(left) directionX = -1;
        if(right) directionX = 1;

        if (zoomIn) camera.zoom -= .02;
        if (zoomOut) camera.zoom += .02;

        camera.position.x += directionX * cameraSpeed;
        camera.position.y += directionY * cameraSpeed;
         /*
        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100/camera.viewportWidth);

        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
    }
    */
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseX = screenX;
        mouseY = screenY;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.W:
                up = false;
                break;
            case Keys.S:
                down = false;
                break;
            case Keys.A:
                left = false;
                break;
            case Keys.D:
                right = false;
                break;
            case Keys.Q:
                zoomOut = false;
                break;
            case Keys.E:
                zoomIn = false;
                break;
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.W:
                up = true;
                break;
            case Keys.S:
                down = true;
                break;
            case Keys.A:
                left = true;
                break;
            case Keys.D:
                right = true;
                break;
            case Keys.Q:
                zoomOut = true;
                break;
            case Keys.E:
                zoomIn = true;
                break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        switch (character) {
            case '\b': //BACKSPACE
                debugMode = !debugMode;
            case 't':
                drawPath = !drawPath;
        }
        return false;
    }
}
