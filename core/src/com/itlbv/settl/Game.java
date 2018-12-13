package com.itlbv.settl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
import com.itlbv.settl.ui.UiStage;
import com.itlbv.settl.util.CollisionHandler;
import com.itlbv.settl.util.MouseKeyboardInput;
import com.itlbv.settl.util.Player;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {
    public static World world;
    public static Map map;

    private static OrthogonalTiledMapRenderer mapRenderer;
    private static MouseKeyboardInput mouseKeyboardInput;
    private static OrthographicCamera camera;
    private static BitmapFont font;
    private static UiStage uiStage;
    private static SpriteBatch batch;

    private static Box2DDebugRenderer box2dBodyRenderer;
    private static UiShapeRenderer uiShapeRenderer;
    private static OrthographicCamera debugCamera;

    public static ArrayList<Mob> mobs;
    private static ArrayList<Mob> deadMobs;

    private static final int VIEWPORT = 40;
    public static float DELTA_TIME = 0;
    private static long RENDER_ITERATION = 0;

    // Test stuff
    private static Player player;
    private static Player player2;
    private static void createPlayers() {
        player = new Player(3,3);
        player2 = new Player(6,6);
    }

    @Override
    public void create() {
        initializeClassFields();
        setCamera();
        setShapeRenderer();
        setDebugCamera();
        setUiStage();
        setInputProcessor();
        setMap();
        setMapRenderer();
        createMobs();

        //createPlayers();
    }

    private void initializeClassFields() {
        world = new World(new Vector2(.0f, .0f), true);
        world.setContactListener(new CollisionHandler());
        box2dBodyRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font26.fnt"));
        mobs = new ArrayList<>();
        deadMobs = new ArrayList<>();
    }

    private void setCamera() {
        float screenRatio = Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT * screenRatio, VIEWPORT);
        camera.position.set(VIEWPORT/2 * screenRatio - 6, VIEWPORT/2 - 5, 0); //todo MAGIC NUMBERS
    }

    private void setShapeRenderer() {
        uiShapeRenderer = new UiShapeRenderer(camera.combined);
    }

    private void setDebugCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        debugCamera = new OrthographicCamera();
        debugCamera.setToOrtho(false, w, h);
    }

    private void setUiStage() {
        uiStage = new UiStage();
    }

    private void setInputProcessor() {
        mouseKeyboardInput = new MouseKeyboardInput(camera);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(mouseKeyboardInput);
        inputMultiplexer.addProcessor(uiStage);
        Gdx.input.setInputProcessor(inputMultiplexer);
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

        handleInput();
        updateCamera();
        updateMobs();

        drawGameObjects();
        drawDebugInfo();

        world.step(DELTA_TIME, 6, 2);
        world.clearForces(); //TODO should it be here?
    }

    private void handleInput() {
        mouseKeyboardInput.handleInput();
        handleSelectedMob();
        uiStage.act(DELTA_TIME);
    }

    private void handleSelectedMob() {
        if (!mouseKeyboardInput.clickHappened) return;
        mouseKeyboardInput.clickHappened = false;
        Vector3 clickCoord = new Vector3(mouseKeyboardInput.mouseClickCoord, 0);
        camera.unproject(clickCoord);
        for (Mob mob : mobs) {
            if (mob.getSprite().getBoundingRectangle().contains(clickCoord.x, clickCoord.y)) {
                uiStage.labelSelectedMob.setText(mob.toString());
            }
        }
    }

    private void updateCamera() {
        batch.setProjectionMatrix(camera.combined);
        camera.update();
    }

    private void drawGameObjects() {
        mapRenderer.setView(camera);
        mapRenderer.render();
        batch.begin();
        drawMobs();
        //drawPlayer();
        batch.end();
        uiStage.draw();
    }

    private void drawDebugInfo() {
        if (!mouseKeyboardInput.debugMode) return;
        if (mouseKeyboardInput.drawPath) {
          drawMobsRoutes();
        }
        drawMobsBoundingRects();
        debugCamera.update();
        batch.begin();
        drawMobId();
        batch.end();
        box2dBodyRenderer.render(world, camera.combined);
    }

    private void drawMobsRoutes() {
        mobs.forEach(m -> uiShapeRenderer.drawRoute(m));
    }

    private void drawMobsBoundingRects() {
        mobs.forEach(m -> uiShapeRenderer.drawBoundingRect(m));
    }

    private void drawMobId() {
        batch.setProjectionMatrix(debugCamera.combined);
        Vector3 fontPos = new Vector3();
        for (Mob mob : mobs) {
            fontPos.set(mob.getPosition(), 0);
            camera.project(fontPos,0,0, debugCamera.viewportWidth, debugCamera.viewportHeight);
            font.draw(batch, Integer.toString(mob.getId()), fontPos.x, fontPos.y);
        }
    }

    private void updateMobs() {
        cleanMobsFromDead();
        mobs.forEach(Mob::update);
        //mobs.get(0).update();
        //mobs.get(1).update();
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
        //player.draw();
        //player2.draw();
    }

    private void drawMobs() {
        deadMobs.forEach(m -> m.getSprite().draw(batch));
        mobs.forEach(m -> m.getSprite().draw(batch));
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = VIEWPORT;
        camera.viewportHeight = VIEWPORT * height/width;
        camera.update(); //TODO should it be here?
    }

    private void updateDeltaTime() {
        DELTA_TIME = Gdx.graphics.getDeltaTime();
    }

    @Override
    public void dispose() {
        uiStage.dispose();
        batch.dispose();
    }
}
