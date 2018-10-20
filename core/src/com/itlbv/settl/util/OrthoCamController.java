package com.itlbv.settl.util;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input.Keys;

public class OrthoCamController extends InputAdapter{

    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;

    private OrthographicCamera camera;
    public OrthoCamController (OrthographicCamera camera) {
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
}
