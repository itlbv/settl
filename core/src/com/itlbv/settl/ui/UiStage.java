package com.itlbv.settl.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.itlbv.settl.Game;
import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.ui.elements.RootTable;
import com.itlbv.settl.ui.elements.Selection;
import com.itlbv.settl.ui.util.DebugRenderer;
import com.kotcrab.vis.ui.VisUI;

import static com.itlbv.settl.mob.action.ActionUtil.approachMobAndFight;
import static com.itlbv.settl.mob.action.ActionUtil.moveToPosition;

public class UiStage extends Stage {

    public static Mob selectedMob;
    public static Selection selection;
    public static boolean debugMode = true;
    public static boolean routeDrawing = true;
    private RootTable rootTable;
    private static DebugRenderer debugRenderer;

    public UiStage() {
        debugRenderer = new DebugRenderer();
        selection = new Selection();
        setStage();
    }

    private void setStage() {
        VisUI.load(VisUI.SkinScale.X2); // scaling ui for hi-res displays
        rootTable = new RootTable();
        addActor(rootTable);
    }

    public void update() {
        rootTable.update();
    }

    @Override
    public void draw() {
        super.draw();
        debugRenderer.draw();
    }

    void leftMouseClick(Vector2 clickPosition) {
        selectedMob = getMobAtPosition(clickPosition);
    }

    void rightMouseClick(Vector2 clickPosition) {
        if (selectedMob == null)
            return;

        Mob clickedMob = getMobAtPosition(clickPosition);
        if (clickedMob == null || clickedMob == selectedMob) {
            moveToPosition(selectedMob, clickPosition);
        } else {
            approachMobAndFight(selectedMob, clickedMob);
        }
    }

    private Mob getMobAtPosition(Vector2 position) {
        for (Mob mob : Game.mobs) {
            selection.setToMob(mob);
            if (selection.contains(position.x, position.y))
                return mob;
        }
        return null;
    }
}
