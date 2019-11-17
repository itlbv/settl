package com.itlbv.settl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.itlbv.settl.map.Map;
import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.mob.util.MobFactory;
import com.itlbv.settl.mob.util.MobType;
import com.itlbv.settl.ui.Input;
import com.itlbv.settl.ui.UiStage;
import com.itlbv.settl.util.CollisionHandler;
import com.itlbv.settl.util.GameConstants;
import com.itlbv.settl.util.GameUtil;
import com.itlbv.settl.util.Player;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {
    public static World world;
    public static Map map;
    public static OrthographicCamera camera;

    private static OrthogonalTiledMapRenderer mapRenderer;
    private static UiStage uiStage;
    private static Input input;
    public static SpriteBatch batch;

    public static ArrayList<Mob> mobs;
    private static ArrayList<Mob> deadMobs;

    private static final float VIEWPORT = 60;
    public static float DELTA_TIME = 0;
    //private static long RENDER_ITERATION = 0;

    // Test stuff
    private static Player player;
    private static void createPlayers() {
        player = new Player(5,5);
        //player2 = new Player(6,6);
    }

    @Override
    public void create() {
        initializeClassFields();
        setCamera();
        setUiStage();
        setInput();
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
        int screenRatio = Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT * screenRatio, VIEWPORT);
        camera.position.set(VIEWPORT/2 * screenRatio - 6, VIEWPORT/2 - 5, 0); //todo MAGIC NUMBERS
    }

    private void setUiStage() {
        uiStage = new UiStage();
    }

    private void setInput() {
        input = new Input(uiStage);
        Gdx.input.setInputProcessor(input);
    }

    private void setMap() {
        map = new Map("maps/map.tmx");
        map.init();
    }

    private void setMapRenderer() {
        float unitScale = (float) 1/ GameConstants.TILE_TEXTURE_SIZE_PXL;
        mapRenderer = new OrthogonalTiledMapRenderer(map.getMap(), unitScale);
        mapRenderer.setView(camera);
    }

    private void createMobs() {
        for (int i = 0; i < 1; i++) {
            MobFactory.createMobAtRandomPosition(false, MobType.KNIGHT);
        }

        for (int i = 0; i < 0; i++) {
            MobFactory.createMobAtRandomPosition(true, MobType.ORC);
        }
    }

    @Override
    public void render() {
        updateDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clears the screen
        //RENDER_ITERATION++;

        uiStage.act(DELTA_TIME);
        uiStage.update();
        updateCamera();
        updateMobs();

        drawMap();
        drawMobs();
        uiStage.draw();

        world.step(DELTA_TIME, 6, 2);
        world.clearForces(); //TODO should it be here?
    }

    private void updateCamera() {
        batch.setProjectionMatrix(camera.combined);
        input.updateCameraPosition();
        camera.update();
    }

    private void updateMobs() {
        separateDeadMobs();
        mobs.forEach(Mob::update);
        /*
        for (Mob mob : mobs) {
            if (mob.getType() == MobType.KNIGHT) {
                mob.update();
            }
        }
        */
    }

    private void separateDeadMobs() {
        for (int i = 0; i < mobs.size(); i++) {
            Mob mob = mobs.get(i);
            if (!mob.isAlive()) {
                deadMobs.add(mob);
                mobs.remove(mob);
                i--;
            }
        }
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
        if (GameUtil.gameSpeed == 0) {
            DELTA_TIME = 0;
        } else {
            DELTA_TIME = Gdx.graphics.getDeltaTime() / GameUtil.gameSpeed;
        }
    }

    @Override
    public void dispose() {
        uiStage.dispose();
        batch.dispose();
    }
}
