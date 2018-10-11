package com.itlbv.settl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.map.Map;
import com.itlbv.settl.map.MapParserFromTxt;
import com.itlbv.settl.map.Tile;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.mobs.utils.MobFactory;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {
    public static SpriteBatch batch;
    private static OrthographicCamera camera;
    public static Map map;
    public static ArrayList<Mob> mobs;
    public static Array<GameObject> testObjects = new Array<>(); //TODO test objects

    private static final int VIEWPORT = 40;
    public static float DELTA_TIME = 0;
    public static long RENDER_ITERATION = 0;

    public Player player;

    @Override
    public void create() {
        initializeClassFields();
        createMap();
        createMobs();

        player = new Player();
    }

    private void initializeClassFields() {
        batch = new SpriteBatch();
        map = Map.getInstance();
        mobs = new ArrayList<Mob>();
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

    private void createMobs() {
        for (int i = 0; i < 1; i++) {
            Mob mob = MobFactory.createMobAtRandomPosition(false, MobObjectType.HUMAN_KNIGHT);
            mobs.add(mob);
        }
        for (int i = 0; i < 0; i++) {
            Mob mob = MobFactory.createMobAtRandomPosition(true, MobObjectType.ORC_SHAMAN);
            mobs.add(mob);
        }
    }

    @Override
    public void render() {
        RENDER_ITERATION++;
        updateDeltaTime();
        updateCamera();
        updateMobs();

        player.update();

        batch.begin();
        drawMap();
        drawMobs();
        drawTestObjects(); //TODO test objects
        player.draw();
        batch.end();

        GameWorld.tick(camera);
    }

    private void updateDeltaTime() {
        DELTA_TIME = Gdx.graphics.getDeltaTime();
    }

    private void updateMobs() {
        mobs.forEach(Mob::update);
        //mobs.get(0).update();
        //mobs.get(1).update();
    }

    private void drawTestObjects() {
        testObjects.forEach(GameObject::draw);
    }

    private void updateCamera() {
        handleInput();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //this magic clears the screen
    }

    private void drawMobs() {
        mobs.forEach(Mob::draw);
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
}
