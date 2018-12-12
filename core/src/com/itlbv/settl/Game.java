package com.itlbv.settl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.map.Map;
import com.itlbv.settl.map.Node;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.mobs.utils.MobFactory;
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
    private static Stage stage;
    static SpriteBatch batch;

    private static Box2DDebugRenderer box2dBodyRenderer;
    private static ShapeRenderer shapeDebugRenderer;
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
        shapeDebugRenderer = new ShapeRenderer();
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

    private void setUiStage() {
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

    private void setInputProcessor() {
        mouseKeyboardInput = new MouseKeyboardInput(camera);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(mouseKeyboardInput);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
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
        //drawPlayer();
        batch.end();
        //stage.act(DELTA_TIME);
        stage.draw();
    }

    private void drawDebugInfo() {
        if (!mouseKeyboardInput.debugMode) return;
        if (mouseKeyboardInput.drawPath) {
          drawMobPath();
        }
        debugCamera.update();
        batch.begin();
        drawMobId();
        batch.end();
        box2dBodyRenderer.render(world, camera.combined);
    }

    private void drawMobPath() {
        shapeDebugRenderer.setProjectionMatrix(camera.combined);
        shapeDebugRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Mob mob : mobs) {
            if (mob.getPath().size() == 0) {
                shapeDebugRenderer.setColor(Color.YELLOW);
                Mob target = (Mob) mob.getTarget();
                if (target == null) break;
                shapeDebugRenderer.rectLine(mob.getPosition().x, mob.getPosition().y, target.getPosition().x, target.getPosition().y, .1f);
            } else {
                shapeDebugRenderer.setColor(Color.WHITE);
                ArrayList<Vector2> nodesToDraw = new ArrayList<>();
                nodesToDraw.add(mob.getPosition());
                for (Node node : mob.getPath().nodes) {
                    nodesToDraw.add(node.getPosition());
                }
                for (int i = 0; i < nodesToDraw.size() - 1; i++) {
                    Vector2 currNode = nodesToDraw.get(i);
                    Vector2 nextNode = nodesToDraw.get(i + 1);
                    shapeDebugRenderer.rectLine(currNode.x, currNode.y, nextNode.x, nextNode.y, .1f);
                }
            }
        }
        shapeDebugRenderer.end();
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

        if(mouseKeyboardInput.down) directionY = -1 ;
        if(mouseKeyboardInput.up) directionY = 1 ;
        if(mouseKeyboardInput.left) directionX = -1;
        if(mouseKeyboardInput.right) directionX = 1;

        if (mouseKeyboardInput.zoomIn) camera.zoom -= .02;
        if (mouseKeyboardInput.zoomOut) camera.zoom += .02;

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
