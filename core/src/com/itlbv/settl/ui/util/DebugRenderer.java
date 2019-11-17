package com.itlbv.settl.ui.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.itlbv.settl.Game;
import com.itlbv.settl.Target;
import com.itlbv.settl.mob.Mob;

import static com.itlbv.settl.mob.action.Action.ActionType.MOVE;
import static com.itlbv.settl.ui.UiStage.*;

public class DebugRenderer extends ShapeRenderer {

    private static OrthographicCamera debugCamera;
    private static Box2DDebugRenderer bodyRenderer;
    private BitmapFont font;

    public DebugRenderer() {
        bodyRenderer = new Box2DDebugRenderer();
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        setDebugCamera();
    }

    private void setDebugCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        debugCamera = new OrthographicCamera();
        debugCamera.setToOrtho(false, w, h);
    }

    public void draw() {
        drawSelection();
        if (debugMode) {
            bodyRenderer.render(Game.world, Game.camera.combined);
            drawMobsRoutes();
            debugCamera.update();
            Game.batch.begin();
            drawMobId();
            Game.batch.end();
        }
    }

    private void drawSelection() {
        if (selectedMob == null)
            return;
        selection.setToMob(selectedMob);
        setProjectionMatrix(Game.camera.combined);
        setColor(Color.RED);
        begin(ShapeType.Line);
        rect(selection.x,
                selection.y,
                selection.width,
                selection.height);
        end();
    }

    private void drawMobId() {
        Game.batch.setProjectionMatrix(debugCamera.combined);
        for (Mob mob : Game.mobs) {
            Vector2 screenCoord = UiUtil.worldToScreenCoordWithDebugCamera(mob.getPosition(), debugCamera);
            font.draw(Game.batch, Integer.toString(mob.getId()), screenCoord.x, screenCoord.y);
        }
    }

    private void drawMobsRoutes() {
        if (routeDrawing) {
            setProjectionMatrix(Game.camera.combined);
            begin(ShapeType.Filled);
            Game.mobs.forEach(this::drawRoute);
            end();
        }
    }

    private void drawRoute(Mob mob) {
        if (mob.getActionType() != MOVE)
            return;

        if (mob.movement.isUnderSteering()) {
            drawSteering(mob);
        } else {
            drawPath(mob);
        }
    }

    private void drawSteering(Mob mob) {
        setColor(Color.YELLOW);
        Target target = mob.getTarget();
        rectLine(mob.getPosition().x, mob.getPosition().y, target.getPosition().x, target.getPosition().y, .1f);
    }

    private void drawPath(Mob mob) {
        setColor(Color.WHITE);
        Array<Vector2> waypoints = new Array<>(mob.movement.getPathMovement().getPath().nodes.size + 1);
        waypoints.add(mob.getPosition());
        mob.movement.getPathMovement().getPath().nodes.forEach(n -> waypoints.add(n.getPosition()));
        for (int i = 0; i < waypoints.size - 1; i++) {
            Vector2 currWaypoint = waypoints.get(i);
            Vector2 nextWaypoint = waypoints.get(i + 1);
            rectLine(currWaypoint.x, currWaypoint.y, nextWaypoint.x, nextWaypoint.y, .1f);
        }
    }
}
