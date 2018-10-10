package com.itlbv.settl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.mobs.HumanKnight;

public class Player extends HumanKnight {
    private Vector2 UP, DOWN, LEFT, RIGHT;


    public Player() {
        super(2, 2);
        super.setTexture(new TextureRegion(new Texture("black_dot.png")));
        UP = new Vector2(0, 0);
        DOWN = new Vector2(0, 0);
        LEFT = new Vector2(0, 0);
        RIGHT = new Vector2(0, 0);
    }

    public void update() {

        if (Gdx.input.isKeyPressed(Input.Keys.T)) {
            UP.set(0, 3);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.G)) {
            DOWN.set(0, -3);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            LEFT.set(-3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            RIGHT.set(3, 0);
        }


        if (!Gdx.input.isKeyPressed(Input.Keys.T)) {
            UP.set(0, 0);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.G)) {
            DOWN.set(0, 0);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.F)) {
            LEFT.set(0, 0);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.H)) {
            RIGHT.set(0, 0);
        }
        float x = UP.x + DOWN.x + LEFT.x + RIGHT.x;
        float y = UP.y + DOWN.y + LEFT.y + RIGHT.y;
        setLinearVelocity(new Vector2(x, y));
        updateRenderPosition();
    }
}
