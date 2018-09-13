package com.itlbv.settl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.itlbv.settl.map.Map;
import com.itlbv.settl.map.MapParserFromTxt;
import com.itlbv.settl.map.Tile;
import com.itlbv.settl.mobs.Human;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.mobs.MobFactory;

public class Game extends ApplicationAdapter {
    private static SpriteBatch batch;
    private OrthographicCamera camera;
    private Map map;
    public static Array<Mob> mobs; //TODO remove static&public when state machine is ready
    public static Array<GameObject> testObjects = new Array<>(); //TODO test objects

    private static final int VIEWPORT = 40;

    @Override
    public void create() {
        initializeClassFields();
        createMap();
        createTestObjects();
    }

    private void initializeClassFields() {
        batch = new SpriteBatch();
        map = Map.getInstance();
        mobs = new Array<Mob>();
        initializeCamera();
    }

    private void createMap() {
        MapParserFromTxt.createMap();
        map.assignCodes();
        map.initGraph();
    }

    private void initializeCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(VIEWPORT, VIEWPORT * (h / w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
    }

    private void createTestObjects() {
        Human human01 = MobFactory.createHuman(3, 3);
        Human human02 = MobFactory.createHuman(50, 50);
        //human01.setTarget(human02);

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
        drawTestObjects(); //TODO test objects
        batch.end();

        GameWorld.tick(camera);
    }

    private void updateTestObjects() {
        mobs.get(0).update();
    }

    private void drawTestObjects() {
        for (GameObject o : testObjects) {
            o.draw();
        }
    }

    private void updateCamera() {
        handleInput();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //this magic clears the screen
    }

    private void drawMobs() {
        for (Mob mob : mobs) {
            mob.draw();
        }
    }

    private void drawMap() {
        for (Array<Tile> row : map.getTiles()) {
            for (Tile tile : row) {
                tile.draw();
            }
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-.5f, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(.5f, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0, -.5f, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0, .5f, 0);
        }

        //camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100/camera.viewportWidth);

        //float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        //float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        //camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        //camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static SpriteBatch getBatch() {
        return batch;
    }
}
