package com.itlbv.settl.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.itlbv.settl.Game;
import com.itlbv.settl.mobs.Mob;

public class InputStage extends Stage {
    private OrthographicCamera camera;
    private static OrthographicCamera debugCamera;
    private static Box2DDebugRenderer box2dBodyRenderer;
    private UiShapeRenderer uiShapeRenderer;

    private Mob selectedMob;

    private Label labelSelectedMob;
    private Label labelGameSpeed;
    private BitmapFont font;

    private boolean debugMode = true;
    private boolean routeDrawing = true;
    public InputStage(OrthographicCamera camera) {
        this.camera = camera;
        uiShapeRenderer = new UiShapeRenderer(camera.combined);
        box2dBodyRenderer = new Box2DDebugRenderer();
        setDebugCamera();
        setStage();
    }

    private void setDebugCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        debugCamera = new OrthographicCamera();
        debugCamera.setToOrtho(false, w, h);
    }

    private void setStage() {
        font = new BitmapFont(Gdx.files.internal("font26.fnt"));

        Table table = new Table();
        table.setFillParent(true);
        addActor(table);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        labelSelectedMob = new Label("no selected mob", labelStyle);
        labelSelectedMob.setPosition(0, 0);
        labelSelectedMob.setSize(100,100);
        addActor(labelSelectedMob);

        labelGameSpeed = new Label("game speed", labelStyle);
        labelGameSpeed.setPosition(0, 50);
        labelGameSpeed.setSize(100,100);
        addActor(labelGameSpeed);
    }

    public void drawAdditionalInfo() {
        drawMobSelection();
        drawGameSpeed();
        drawDebugInfo();
    }

    private void drawMobSelection() {
        if (selectedMob == null) return;
        uiShapeRenderer.drawSelectingRect(selectedMob);
    }

    private void drawGameSpeed() {
        if (gameSpeed == 0) {
            labelGameSpeed.setText("PAUSE");
        } else if (gameSpeed == 1) {
            labelGameSpeed.setText("NORMAL");
        } else if (gameSpeed == 2) {
            labelGameSpeed.setText("SLOW");
        } else if (gameSpeed == 3) {
            labelGameSpeed.setText("VERY SLOW");
        }
    }

    private void drawDebugInfo() {
        if (!debugMode) return;
        drawMobsRoutes();
        box2dBodyRenderer.render(Game.world, camera.combined);
        debugCamera.update();
        Game.batch.begin();
        drawMobId();
        Game.batch.end();
    }

    private void drawMobId() {
        Game.batch.setProjectionMatrix(debugCamera.combined);
        Vector3 fontPos = new Vector3();
        for (Mob mob : Game.mobs) {
            fontPos.set(mob.getPosition(), 0);
            camera.project(fontPos,0,0, debugCamera.viewportWidth, debugCamera.viewportHeight);
            font.draw(Game.batch, Integer.toString(mob.getId()), fontPos.x, fontPos.y);
        }
    }

    private void drawMobsRoutes() {
        if (!routeDrawing) return;
        Game.mobs.forEach(m -> uiShapeRenderer.drawRoute(m));
    }

    @Override
    public boolean keyTyped(char character) {
        switch (character) {
            case '\b': debugMode = !debugMode; break;// BACKSPACE
            case 't': routeDrawing =! routeDrawing; break;
            case 'x': increaseGameSpeed(); break;
            case 'z': decreaseGameSpeed(); break;
            case ' ': changePauseState(); break;// SPACEBAR
        }
        return true;
    }

    private void increaseGameSpeed() {
        switch (gameSpeed) {
            case 0: gameSpeed = 3; break;
            case 1: gameSpeed = 1; break;
            case 2: gameSpeed = 1; break;
            case 3: gameSpeed = 2; break;
        }
    }

    private void decreaseGameSpeed() {
        switch (gameSpeed) {
            case 0: gameSpeed = 0; break;
            case 1: gameSpeed = 2; break;
            case 2: gameSpeed = 3; break;
            case 3: gameSpeed = 3; break;
        }
    }

    public int gameSpeed = 1;
    private int previousGameSpeed = 1;
    private void changePauseState() {
        if (gameSpeed == 0) {
            gameSpeed = previousGameSpeed;
        } else {
            previousGameSpeed = gameSpeed;
            gameSpeed = 0;
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 clickCoord = new Vector3(screenX, screenY, 0);
        camera.unproject(clickCoord);
        for (Mob mob : Game.mobs) {
            Rectangle selectingRect = mob.getSelectingRectangle();
            if (selectingRect.contains(clickCoord.x, clickCoord.y)) {
                selectedMob = mob;
                labelSelectedMob.setText(mob.toString());
                break;
            }
        }
        return true;
    }

    public void updateCameraPosition() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-1, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0, -1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(1, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom += .02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoom -= .02;
        }
    }
}
