package com.itlbv.settl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.itlbv.settl.enums.GameObjectType;

public abstract class GameObject {
    private Vector3 position;
    private float width;
    private float height;
    private Texture texture;
    private Body body;
    private Body sensor;
    private GameObjectType type;

    public GameObject(float x, float y, float width, float height, Texture texture, GameObjectType type) {
        this.position = new Vector3(x, y, 0);
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.type = type;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, width, height);
    }

    //***Getters & setters***
    //TODO getters & setters

}
