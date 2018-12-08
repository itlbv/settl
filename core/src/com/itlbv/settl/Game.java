package com.itlbv.settl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.map.Map;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.mobs.utils.MobFactory;
import com.itlbv.settl.util.CollisionHandler;
import com.itlbv.settl.util.InputController;
import com.itlbv.settl.util.Player;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {
    private static InputController inputController;
    private static OrthographicCamera worldCamera;
    private static OrthographicCamera uiCamera;

    public static World world;
    public static Map map;
    private static OrthogonalTiledMapRenderer mapRenderer;
    private static Box2DDebugRenderer debugRenderer;
    public static SpriteBatch batch;

    public static ArrayList<Mob> mobs;
    public static ArrayList<Mob> humans;
    public static ArrayList<Mob> orcs;
    private static ArrayList<Mob> deadMobs;

    private static final int VIEWPORT = 40;
    public static float DELTA_TIME = 0;
    public static long RENDER_ITERATION = 0;

    public static Player player;
    public static Player player2;
    public static Array<GameObject> testObjects = new Array<>(); //TODO test objects
    public static BitmapFont font; //TODO font


    @Override
    public void create() {
        initializeClassFields();
        createWorldCamera();
        createUiCamera();
        createMap();
        createMapRenderer();
        createMobs();

        //player = new Player(3,3);
        //player2 = new Player(6,6);
    }

    private void initializeClassFields() {
        world = new World(new Vector2(.0f, .0f), true);
        world.setContactListener(new CollisionHandler());
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        mobs = new ArrayList<Mob>();
        humans = new ArrayList<Mob>();
        orcs = new ArrayList<Mob>();
        deadMobs = new ArrayList<Mob>();
        font = new BitmapFont(Gdx.files.internal("font26.fnt"), false);
        //font.getData().setScale(.1f);
    }

    private void createWorldCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        worldCamera = new OrthographicCamera();
        worldCamera.setToOrtho(false, VIEWPORT * (w/h), VIEWPORT);
        worldCamera.position.set(VIEWPORT/2 * (w/h) - 6, VIEWPORT/2 - 5, 0); //todo MAGIC NUMBERS
        inputController = new InputController(worldCamera);
        Gdx.input.setInputProcessor(inputController);
    }

    private void createUiCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, 1024 * (w/h), 1024);
    }

    private void createMap() {
        map = new Map("maps/map.tmx");
        map.init();
    }

    private void createMapRenderer() {
        float unitScale = (float) 1/GameConstants.TILE_SIZE_PXL;
        mapRenderer = new OrthogonalTiledMapRenderer(map.getMap(), unitScale);
        mapRenderer.setView(worldCamera);
    }

    private void createMobs() {
        for (int i = 0; i < 5; i++) {
            MobFactory.createMobAtRandomPosition(false, MobObjectType.KNIGHT);
        }
        for (int i = 0; i < 5; i++) {
            MobFactory.createMobAtRandomPosition(true, MobObjectType.ORC);
        }
    }

    @Override
    public void render() {
        RENDER_ITERATION++;
        updateDeltaTime();
        updateWorldCamera();
        uiCamera.update();
        updateMobs();

        mapRenderer.setView(worldCamera);
        mapRenderer.render();

        //player.update();
        //player2.update();

        renderDebugInfo();

        batch.begin();

        drawDeadMobs();
        drawMobs();
        drawTestObjects(); //TODO test objects
        //player.draw();
        //player2.draw();

        drawText();

        batch.end();

        world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2);
        world.clearForces(); //TODO should it be here?
    }

    private void drawText() {
        batch.setProjectionMatrix(uiCamera.combined);
        for (Mob mob : mobs) {
            //font.draw(batch, Integer.toString(mob.getId()), mob.getRenderPosition().x, mob.getRenderPosition().y);
            font.draw(batch, "test text", 20, 20);
        }
    }

    private void renderDebugInfo() {
        if (inputController.debugMode) {
            debugRenderer.render(world, worldCamera.combined);
        }
    }

    private void updateWorldCamera() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(worldCamera.combined);

        int directionX = 0;
        int directionY = 0;
        int cameraSpeed = 1;

        if(inputController.down) directionY = -1 ;
        if(inputController.up) directionY = 1 ;
        if(inputController.left) directionX = -1;
        if(inputController.right) directionX = 1;

        if (inputController.zoomIn) worldCamera.zoom -= .02;
        if (inputController.zoomOut) worldCamera.zoom += .02;

        worldCamera.position.x += directionX * cameraSpeed;
        worldCamera.position.y += directionY * cameraSpeed;
        worldCamera.update();
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

    private void drawTestObjects() {
        testObjects.forEach(GameObject::draw);
    }

    private void drawDeadMobs() {
        deadMobs.forEach(Mob::draw);
    }

    private void drawMobs() {
        for (Mob mob : mobs) {
            mob.draw();
            //if (inputController.debugMode) {
            //    font.draw(batch, Integer.toString(mob.getId()), mob.getRenderPosition().x, mob.getRenderPosition().y);
            //}
        }
    }

    @Override
    public void resize(int width, int height) {
        worldCamera.viewportWidth = VIEWPORT;
        worldCamera.viewportHeight = VIEWPORT * height/width;
        worldCamera.update();
    }


    /*
        worldCamera.zoom = MathUtils.clamp(worldCamera.zoom, 0.1f, 100/worldCamera.viewportWidth);

        float effectiveViewportWidth = worldCamera.viewportWidth * worldCamera.zoom;
        float effectiveViewportHeight = worldCamera.viewportHeight * worldCamera.zoom;

        worldCamera.position.x = MathUtils.clamp(worldCamera.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        worldCamera.position.y = MathUtils.clamp(worldCamera.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
    }
    */
    private void updateDeltaTime() {
        DELTA_TIME = Gdx.graphics.getDeltaTime();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
