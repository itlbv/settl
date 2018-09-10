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
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Map map;
    private ArrayList<Mob> mobs;

    @Override
    public void create() {
        initializeClassVariables();

        MapParserFromTxt.createMap();

        createTestObjects();
    }

    private void initializeClassVariables() {
        batch = new SpriteBatch();
        map = Map.getInstance();
        mobs = new ArrayList<Mob>();
        initializeCamera();
    }

    private void initializeCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        int viewport = 400;
        camera = new OrthographicCamera(viewport, viewport * (h / w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
    }

    private void createTestObjects() {
        Human human01 = MobFactory.createHuman(10, 10);
        Human human02 = MobFactory.createHuman(300, 300);
        human01.setTarget(human02);

        mobs.add(human01);
        mobs.add(human02);
    }

    @Override
    public void render() {
        updateCamera();

        updateTestObjects();

        batch.begin();
        drawMap();
        drawMobs();
        batch.end();

        GameWorld.tick(camera);
    }

    private void updateCamera() {
        handleInput();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //this magic clears the screen
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

        //camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100/camera.viewportWidth);

        //float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        //float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        //camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        //camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
    }

    private void updateTestObjects() {
        mobs.get(0).update();
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
    public void dispose() {
        batch.dispose();
    }
}
