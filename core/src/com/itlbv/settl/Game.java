package com.itlbv.settl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.itlbv.settl.map.Map;
import com.itlbv.settl.map.MapParserFromTxt;
import com.itlbv.settl.map.Tile;
import com.itlbv.settl.mobs.Human;
import com.itlbv.settl.mobs.Mob;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {
    private static final int WORLD_WIDTH = 10;
    private static final int WORLD_HEIGHT = 10;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private float rotationSpeed;
    private Map map;
    private ArrayList<Mob> mobs;

	@Override
	public void create () {
		batch = new SpriteBatch();
        map = Map.getInstance();
        mobs = new ArrayList<Mob>();

        rotationSpeed = .5f;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        int viewport = 400;
		camera = new OrthographicCamera(viewport, viewport * (h / w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update(); //TODO is it necessary here?

        MapParserFromTxt.createMap();
        mobs.add(MobFactory.createHuman(10, 10));
        mobs.add(MobFactory.createHuman(300, 300));
	}

	@Override
	public void render () {
	    handleInput();
	    camera.update();
	    batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //this magic clears the screen

       	batch.begin();
		drawMap();
		drawMobs();
		batch.end();

		Human human01 = (Human) mobs.get(0);
		Human human02 = (Human) mobs.get(1);
		human01.setTarget(human02);
		human01.update();
		GameWorld.tick(camera);
	}

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            camera.rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            camera.rotate(rotationSpeed, 0, 0, 1);
        }

        //camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100/camera.viewportWidth);

        //float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        //float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        //camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        //camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
    }

    private void drawMobs() {
        for (Mob mob : mobs) {
            mob.draw(batch);
        }
    }

	private void drawMap() {
        for (ArrayList<Tile> row : map.getTiles()) {
            for (Tile tile : row) {
                tile.draw(batch);
            }
        }
    }
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
