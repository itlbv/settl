package com.itlbv.settl.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.itlbv.settl.Game;
import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.ui.util.DebugRenderer;
import com.itlbv.settl.ui.util.UiUtil;
import com.itlbv.settl.util.GameUtil;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisWindow;

import static com.itlbv.settl.mob.action.util.ActionUtil.approachMobAndFight;
import static com.itlbv.settl.mob.action.util.ActionUtil.moveToPosition;

public class UiStage extends Stage {

    private Table rootTable;

    private Mob selectedMob;
    private Rectangle selectionIndicator;

    boolean debugMode = true;
    public boolean routeDrawing = true;
    private static DebugRenderer debugRenderer;

    public UiStage() {
        debugRenderer = new DebugRenderer(this);
        selectionIndicator = new Rectangle();
        setStage();
    }

    private void setStage() {
        VisUI.load(VisUI.SkinScale.X2); // scaling ui for hi-res displays

        rootTable = new Table();
        rootTable.setFillParent(true);

        VisWindow winMobInfo = setupWinMobInfo();
        VisWindow winGameInfo = setupWinGameInfo();

        rootTable.add(winMobInfo).width(400).expand().bottom().left();
        rootTable.add(winGameInfo).width(350).expand().bottom().right();
        addActor(rootTable);
    }

    private VisWindow setupWinMobInfo() {
        VisWindow window = new VisWindow("mobInfo");
        createLabelsAndAddToWindow("selectedMob", "Mob:", window);
        createLabelsAndAddToWindow("target", "target:", window);
        createLabelsAndAddToWindow("action", "action:", window);
        createLabelsAndAddToWindow("bodyPos", "body pos:", window);
        createLabelsAndAddToWindow("sensorPos", "sensor pos:", window);
        return window;
    }

    private VisWindow setupWinGameInfo() {
        VisWindow window = new VisWindow("gameInfo");
        createLabelsAndAddToWindow("gameSpeed", "Game speed:", window);
        return window;
    }

    private void createLabelsAndAddToWindow(String name, String text, VisWindow window) {
        VisLabel txt = new VisLabel(text);
        VisLabel label = new VisLabel();
        label.setName(name);
        window.add(txt).right();
        window.add(label).expandX();
        window.row();
    }

    public void update() {
        updateSelectedMobInfo();
        updateGameInfo();
    }

    public void drawDebug() {
        drawSelectionIndicator();
        if (debugMode) {
            debugRenderer.draw();
        }
    }

    private void updateSelectedMobInfo() {
        if (selectedMob == null) {
            setTextToLabel("selectedMob", "-");
            setTextToLabel("target", "-");
            setTextToLabel("action", "-");
            setTextToLabel("bodyPos", "-");
            setTextToLabel("sensorPos", "-");
        } else {
            setTextToLabel("selectedMob", selectedMob.toString());
            setTextToLabel("target", selectedMob.getTarget() == null ? "" : selectedMob.getTarget().toString());
            setTextToLabel("action", selectedMob.hasNoActions() ? "" : selectedMob.getAction().toString());
            setTextToLabel("bodyPos", UiUtil.vectorToString(selectedMob.getPosition()));
            setTextToLabel("sensorPos", UiUtil.vectorToString(selectedMob.getSensorPosition()));
        }
    }

    private void setTextToLabel(String labelName, String text) {
        Actor actor = rootTable.findActor(labelName);
        if (actor instanceof VisLabel) {
            ((VisLabel) actor).setText(text);
        }
    }

    private void updateGameInfo() {
        String gameSpeed = "";
        if (GameUtil.gameSpeed == 0) {
            gameSpeed = "PAUSE";
        } else if (GameUtil.gameSpeed == 1) {
            gameSpeed = "NORMAL";
        } else if (GameUtil.gameSpeed == 2) {
            gameSpeed = "SLOW";
        } else if (GameUtil.gameSpeed == 3) {
            gameSpeed = "VERY SLOW";
        }
        setTextToLabel("gameSpeed", gameSpeed);
    }

    void leftMouseClick(Vector2 clickPosition) {
        selectedMob = getMobFromPosition(clickPosition);
    }

    void rightMouseClick(Vector2 clickPosition) {
        if (selectedMob == null)
            return;

        Mob clickedMob = getMobFromPosition(clickPosition);
        if (clickedMob == null || clickedMob == selectedMob) {
            moveToPosition(selectedMob, clickPosition);
        } else {
            approachMobAndFight(selectedMob, clickedMob);
        }
    }

    private Mob getMobFromPosition(Vector2 position) {
        for (Mob mob : Game.mobs) {
            selectionIndicator.set(mob.getPosition().x - 0.5f,
                    mob.getPosition().y,
                    1f, 1.5f);
            if (selectionIndicator.contains(position.x, position.y)) {
                return mob;
            }
        }
        return null;
    }

    private void drawSelectionIndicator() {
        if (selectedMob == null) return;
        debugRenderer.drawSelectionIndicator(selectionIndicator);
    }
}
