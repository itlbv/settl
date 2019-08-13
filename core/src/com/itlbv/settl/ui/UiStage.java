package com.itlbv.settl.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.itlbv.settl.Game;
import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.mob.action.util.ActionUtil;
import com.itlbv.settl.ui.util.UiShapeRenderer;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisWindow;

public class UiStage extends Stage {
    private static OrthographicCamera debugCamera;
    private static Box2DDebugRenderer box2dBodyRenderer;
    private UiShapeRenderer uiShapeRenderer;

    Mob selectedMob;
    private Rectangle selectionIndicator;

    VisLabel labelSelectedMob;
    private VisLabel labelGameSpeed;
    private BitmapFont font;

    boolean debugMode = true;
    boolean routeDrawing = true;

    public UiStage() {
        uiShapeRenderer = new UiShapeRenderer();
        box2dBodyRenderer = new Box2DDebugRenderer();
        selectionIndicator = new Rectangle();
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
        font = new BitmapFont(Gdx.files.internal("font.fnt"));

        VisUI.load(VisUI.SkinScale.X2);

        VisWindow windowMobInfo = new VisWindow("mobInfo");
        labelGameSpeed = new VisLabel();
        windowMobInfo.add(labelGameSpeed);
        labelSelectedMob = new VisLabel();
        windowMobInfo.add(labelSelectedMob);

        addActor(windowMobInfo);
        windowMobInfo.pack();

        /*
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
        */
    }

    void leftMouseClick(Vector2 clickPosition) {
        for (Mob mob : Game.mobs) {
            selectionIndicator.set(mob.getPosition().x - 0.5f,
                    mob.getPosition().y,
                    1f, 1.5f);
            if (selectionIndicator.contains(clickPosition.x, clickPosition.y)) {
                selectedMob = mob;
                labelSelectedMob.setText(mob.toString());
                break;
            }
        }
    }

    void rightMouseClick(Vector2 clickPosition) {
        if (selectedMob == null) return;
        Mob clickedMob = null;
        for (Mob mob : Game.mobs) {
            selectionIndicator.set(mob.getPosition().x - 0.5f,
                    mob.getPosition().y,
                    1f, 1.5f);
            if (selectionIndicator.contains(clickPosition.x, clickPosition.y)) {
                clickedMob = mob;
                break;
            }
        }
        if (clickedMob == null) {
            ActionUtil.setMove(selectedMob, clickPosition);
        } else if (clickedMob == selectedMob) {
            //TODO move selected mob to click position instead
        } else {
            ActionUtil.setApproachAndFight(selectedMob, clickedMob);
        }
    }

    public void drawAdditionalInfo() {
        /*
        drawSelectionIndicator();
        drawGameSpeed();
        */
        drawDebugInfo();
    }

    private void drawDebugInfo() {
        if (!debugMode) return;
        //drawMobsRoutes();
        box2dBodyRenderer.render(Game.world, Game.camera.combined);
        debugCamera.update();
        Game.batch.begin();
        //drawMobId();
        Game.batch.end();
    }
/*
    private void drawSelectionIndicator() {
        if (selectedMob == null) return;
        uiShapeRenderer.drawSelectionIndicator(selectionIndicator);
    }

    private void drawGameSpeed() {
        if (GameUtil.gameSpeed == 0) {
            labelGameSpeed.setText("PAUSE");
        } else if (GameUtil.gameSpeed == 1) {
            labelGameSpeed.setText("NORMAL");
        } else if (GameUtil.gameSpeed == 2) {
            labelGameSpeed.setText("SLOW");
        } else if (GameUtil.gameSpeed == 3) {
            labelGameSpeed.setText("VERY SLOW");
        }
    }



    private void drawMobId() {
        Game.batch.setProjectionMatrix(debugCamera.combined);
        Vector3 fontPos = new Vector3();
        for (Mob mob : Game.mobs) {
            fontPos.set(mob.getPosition(), 0);
            //TODO move to UIUtil
            Game.camera.project(fontPos,0,0, debugCamera.viewportWidth, debugCamera.viewportHeight);
            font.draw(Game.batch, Integer.toString(mob.getId()), fontPos.x, fontPos.y);
        }
    }

    private void drawMobsRoutes() {
        if (!routeDrawing) return;
        Game.mobs.forEach(m -> uiShapeRenderer.drawRoute(m));
    }
    */
}
