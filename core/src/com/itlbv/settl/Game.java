package com.itlbv.settl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.map.Map;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.mobs.util.MobFactory;
import com.itlbv.settl.ui.UiShapeRenderer;
import com.itlbv.settl.ui.InputStage;
import com.itlbv.settl.util.CollisionHandler;
import com.itlbv.settl.util.Player;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {
    public static World world;
    public static Map map;

    private static OrthogonalTiledMapRenderer mapRenderer;
    private static OrthographicCamera camera;
    private static InputStage inputStage;
    public static SpriteBatch batch;

    public static ArrayList<Mob> mobs;
    private static ArrayList<Mob> deadMobs;

    private static final int VIEWPORT = 40;
    public static float DELTA_TIME = 0;
    private static long RENDER_ITERATION = 0;

    // Test stuff
    private static Player player;
    private static Player player2;
    private static void createPlayers() {
        player = new Player(5,5);
        //player2 = new Player(6,6);
    }

    @Override
    public void create() {
        initializeClassFields();
        setCamera();
        setInputStage();
        setMap();
        setMapRenderer();
        createMobs();

        createPlayers();
    }

    private void initializeClassFields() {
        world = new World(new Vector2(.0f, .0f), true);
        world.setContactListener(new CollisionHandler());
        batch = new SpriteBatch();
        mobs = new ArrayList<>();
        deadMobs = new ArrayList<>();
    }

    private void setCamera() {
        float screenRatio = Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT * screenRatio, VIEWPORT);
        camera.position.set(VIEWPORT/2 * screenRatio - 6, VIEWPORT/2 - 5, 0); //todo MAGIC NUMBERS
    }

    private void setInputStage() {
        inputStage = new InputStage(camera);
        Gdx.input.setInputProcessor(inputStage);
    }

    private void setMap() {
        map = new Map("maps/map.tmx");
        map.init();
    }

    private void setMapRenderer() {
        float unitScale = (float) 1/GameConstants.TILE_TEXTURE_SIZE_PXL;
        mapRenderer = new OrthogonalTiledMapRenderer(map.getMap(), unitScale);
        mapRenderer.setView(camera);
    }

    private void createMobs() {
        for (int i = 0; i < 10; i++) {
            MobFactory.createMobAtRandomPosition(false, MobObjectType.KNIGHT);
        }
        for (int i = 0; i < 10; i++) {
            MobFactory.createMobAtRandomPosition(true, MobObjectType.ORC);
        }
    }

    @Override
    public void render() {
        updateDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clears the screen
        RENDER_ITERATION++;

        inputStage.act(DELTA_TIME);
        updateCamera();
        updateMobs();

        drawMap();
        drawMobs();
        inputStage.draw();
        inputStage.drawAdditionalInfo();

        world.step(DELTA_TIME, 6, 2);
        world.clearForces(); //TODO should it be here?
    }

    private void updateCamera() {
        batch.setProjectionMatrix(camera.combined);
        inputStage.updateCameraPosition();
        camera.update();
    }

    private void updateMobs() {
        cleanMobsFromDead();
        //mobs.forEach(Mob::update);
        //mobs.get(0).update();
        //mobs.get(1).update();
    }

    private void drawMap() {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    private void drawMobs() {
        batch.begin();
        deadMobs.forEach(m -> m.getSprite().draw(batch));
        mobs.forEach(m -> m.getSprite().draw(batch));
        drawPlayer();
        batch.end();
    }

    private void cleanMobsFromDead() {
        for (int i = 0; i < mobs.size(); i++) {
            Mob mob = mobs.get(i);
            if (mob.isDead()) {
                deadMobs.add(mob);
                mobs.remove(i);
                i--;
                mob.die();
            }
        }
    }

    private void drawPlayer() {
        player.update();
        player.getSprite().draw(batch);
        //player2.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = VIEWPORT;
        camera.viewportHeight = VIEWPORT * height/width;
        camera.update(); //TODO should it be here?
    }

    private void updateDeltaTime() {
        if (inputStage.gameSpeed == 0) {
            DELTA_TIME = 0;
        } else {
            DELTA_TIME = Gdx.graphics.getDeltaTime() / inputStage.gameSpeed;
        }
    }

    @Override
    public void dispose() {
        inputStage.dispose();
        batch.dispose();
    }
}
