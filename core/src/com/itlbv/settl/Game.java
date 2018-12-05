package com.itlbv.settl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.map.Map;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.mobs.utils.MobFactory;
import com.itlbv.settl.util.OrthoCamController;
import com.itlbv.settl.util.Player;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {
    public static SpriteBatch batch;
    public static Map map;
    private static OrthographicCamera camera;
    private static OrthoCamController cameraController;
    private static OrthogonalTiledMapRenderer mapRenderer;
    public static ArrayList<Mob> mobs;
    public static ArrayList<Mob> humans;
    public static ArrayList<Mob> orcs;
    private static ArrayList<Mob> deadMobs;
    public static Array<GameObject> testObjects = new Array<>(); //TODO test objects
    public static BitmapFont font; //TODO font

    private static final int VIEWPORT = 40;
    public static float DELTA_TIME = 0;
    public static long RENDER_ITERATION = 0;

    public static Player player;
    public static Player player2;


    @Override
    public void create() {
        initializeClassFields();
        createCamera();
        createMap();
        createMapRenderer();
        createMobs();

        //player = new Player(3,3);
        //player2 = new Player(6,6);
    }

    private void initializeClassFields() {
        batch = new SpriteBatch();
        mobs = new ArrayList<Mob>();
        humans = new ArrayList<Mob>();
        orcs = new ArrayList<Mob>();
        deadMobs = new ArrayList<Mob>();
        font = new BitmapFont(Gdx.files.internal("fontjava4.fnt"), false);
        font.getData().setScale(.1f);
    }

    private void createCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT * (w/h), VIEWPORT);
        camera.position.set(VIEWPORT/2 * (w/h) - 6, VIEWPORT/2 - 5, 0); //todo MAGIC NUMBERS
        cameraController = new OrthoCamController(camera);
        Gdx.input.setInputProcessor(cameraController);
    }

    private void createMap() {
        map = new Map("maps/map.tmx");
        map.init();
    }

    private void createMapRenderer() {
        float unitScale = (float) 1/GameConstants.TILE_SIZE_PXL;
        mapRenderer = new OrthogonalTiledMapRenderer(map.getMap(), unitScale);
        mapRenderer.setView(camera);
    }

    private void createMobs() {
        for (int i = 0; i < 5; i++) {
            Mob mob = MobFactory.createMobAtRandomPosition(false, MobObjectType.HUMAN_KNIGHT);
            mobs.add(mob);
        }
        for (int i = 0; i < 5; i++) {
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

        mapRenderer.setView(camera);
        mapRenderer.render();

        //player.update();
        //player2.update();

        batch.begin();

        drawDeadMobs();
        drawMobs();
        drawTestObjects(); //TODO test objects
        //player.draw();
        //player2.draw();

        batch.end();

        GameWorld.tick(camera);
    }

    private void updateCamera() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        int directionX = 0;
        int directionY = 0;
        int cameraSpeed = 1;

        if(cameraController.down) directionY = -1 ;
        if(cameraController.up) directionY = 1 ;
        if(cameraController.left) directionX = -1;
        if(cameraController.right) directionX = 1;

        camera.position.x += directionX * cameraSpeed;
        camera.position.y += directionY * cameraSpeed;
        camera.update();
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
            font.draw(batch, mob.getStringId(), mob.getRenderPosition().x, mob.getRenderPosition().y);
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = VIEWPORT;
        camera.viewportHeight = VIEWPORT * height/width;
        camera.update();
    }


    /*
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
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {

        }

        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100/camera.viewportWidth);

        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
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
