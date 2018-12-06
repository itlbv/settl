package com.itlbv.settl.util;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input.Keys;

public class InputController extends InputAdapter{

    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;

    public boolean debugMode = true;

    private OrthographicCamera camera;
    public InputController(OrthographicCamera camera) {
        this.camera = camera;
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
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        switch (character) {
            case '\b': //BACKSPACE
                debugMode = !debugMode;
                break;
        }
        return false;
    }
}
