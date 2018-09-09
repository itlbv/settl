package com.itlbv.settl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.itlbv.settl.enums.GameObjectType;

public abstract class GameObject{
    public Vector2 position;
    private Texture texture;
    private Body body;
    private Body sensor;
    private GameObjectType type;

    public GameObject(float x, float y, Texture texture, GameObjectType type) {
        this.position = new Vector2(x, y);
        this.texture = texture;
        this.type = type;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public void createBody(BodyType bodyType, float bodyWidth, float bodyHeight) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(this.position.x + bodyWidth/2, this.position.y + bodyHeight/2); //TODO change GameObject.position to Vector2?
        body = GameWorld.world.createBody(bodyDef); //TODO Gameworld class refactoring?

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(bodyWidth/2, bodyHeight/2);
        fixtureDef.shape = polygonShape;

        body.createFixture(fixtureDef);
        polygonShape.dispose();
    }

    //***Getters & setters***
    //TODO getters & setters
}
