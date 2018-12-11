package com.itlbv.settl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
    public static World world;
    public static Map map;

    private static OrthogonalTiledMapRenderer mapRenderer;
    private static Box2DDebugRenderer debugRenderer;
    private static InputController inputController;
    private static OrthographicCamera camera;
    private static OrthographicCamera debugCamera;
    static SpriteBatch batch;
    private static Stage stage;
    private static BitmapFont font;

    public static ArrayList<Mob> mobs;
    private static ArrayList<Mob> deadMobs;

    private static final int VIEWPORT = 40;
    public static float DELTA_TIME = 0;
    private static long RENDER_ITERATION = 0;

    // Test stuff
    private static Player player;
    private static Player player2;
    public static Array<GameObject> testObjects = new Array<>(); //TODO test objects


    private static void createPlayers() {
        player = new Player(3,3);
        player2 = new Player(6,6);
    }

    @Override
    public void create() {
        initializeClassFields();
        setCamera();
        setDebugCamera();
        setUi();
        setInputController();
        setMap();
        setMapRenderer();
        createMobs();

        //createPlayers();
    }

    private void initializeClassFields() {
        world = new World(new Vector2(.0f, .0f), true);
        world.setContactListener(new CollisionHandler());
        debugRenderer = new Box2DDebugRenderer();
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

    private void setDebugCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        debugCamera = new OrthographicCamera();
        debugCamera.setToOrtho(false, w, h);
    }

    private void setUi() {
        stage = new Stage();
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        Label label = new Label("test label", labelStyle);
        label.setPosition(0, 0);
        label.setSize(100,100);
        stage.addActor(label);
    }

    private void setInputController() {
        inputController = new InputController(camera);
        Gdx.input.setInputProcessor(inputController);
    }

    private void setMap() {
        map = new Map("maps/map.tmx");
        map.init();
    }

    private void setMapRenderer() {
        float unitScale = (float) 1/GameConstants.TILE_SIZE_PXL;
        mapRenderer = new OrthogonalTiledMapRenderer(map.getMap(), unitScale);
        mapRenderer.setView(camera);
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clears the screen

        RENDER_ITERATION++;
        updateDeltaTime();
        updateCamera();
        updateMobs();

        drawGameObjects();
        drawDebugInfo();

        world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2);
        world.clearForces(); //TODO should it be here?
    }

    private void drawGameObjects() {
        mapRenderer.setView(camera);
        mapRenderer.render();
        batch.begin();
        drawDeadMobs();
        drawMobs();
        drawTestObjects(); //TODO test objects drawing
        //drawPlayer();
        batch.end();
        //stage.act(DELTA_TIME);
        stage.draw();
    }

    private void drawDebugInfo() {
        if (!inputController.debugMode) return;
        debugCamera.update();
        batch.begin();
        drawMobId();
        batch.end();
        debugRenderer.render(world, camera.combined);
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

    private void updateCamera() {
        batch.setProjectionMatrix(camera.combined);

        int directionX = 0;
        int directionY = 0;
        int cameraSpeed = 1;

        if(inputController.down) directionY = -1 ;
        if(inputController.up) directionY = 1 ;
        if(inputController.left) directionX = -1;
        if(inputController.right) directionX = 1;

        if (inputController.zoomIn) camera.zoom -= .02;
        if (inputController.zoomOut) camera.zoom += .02;

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

    private void drawPlayer() {
        player.draw();
        player2.draw();
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
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = VIEWPORT;
        camera.viewportHeight = VIEWPORT * height/width;
        camera.update();
    }

    /*
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
        stage.dispose();
        batch.dispose();
    }
}
