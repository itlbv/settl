package com.itlbv.settl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.itlbv.settl.enums.GameObjectType;

public abstract class GameObject{
    public Vector2 position; //TODO make private
    private Texture texture;
    private SteerableBody body;
    private GameObjectType type;

    public GameObject(float x, float y, Texture texture, GameObjectType type) {
        this.position = new Vector2(x, y);
        this.texture = texture;
        this.type = type;
    }

    public void createBody(BodyType bodyType, float bodyWidth, float bodyHeight) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(this.position.x + texture.getWidth()/2, this.position.y + bodyHeight/2);
        body = new SteerableBody(bodyDef, this);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(bodyWidth/2, bodyHeight/2);
        fixtureDef.shape = polygonShape;

        body.createFixture(fixtureDef);
        body.body.setUserData(bodyHeight); //TODO done for updatePosition method. should be deleted
        polygonShape.dispose();
    }

    public void updatePosition() { //TODO redo this mess
        Vector2 bodyPosition = getBody().getPosition();
        float x = bodyPosition.x - texture.getWidth()/2;
        float y = bodyPosition.y - Float.parseFloat(getBody().body.getUserData().toString())/2;
        position.set(x, y);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    //***Getters & setters***
    public SteerableBody getBody() {
        return body;
    }
}
