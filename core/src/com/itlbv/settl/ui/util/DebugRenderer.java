package com.itlbv.settl.ui.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.itlbv.settl.Game;
import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.mob.action.Action;
import com.itlbv.settl.mob.action.util.ActionUtil;
import com.itlbv.settl.ui.UiStage;

import static com.itlbv.settl.mob.util.MobUtil.getTargetMob;

public class DebugRenderer extends ShapeRenderer {

    private UiStage stage;
    private static OrthographicCamera debugCamera;
    private static Box2DDebugRenderer bodyRenderer;
    private BitmapFont font;

    public DebugRenderer(UiStage stage) {
        this.stage = stage;
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
        bodyRenderer.render(Game.world, Game.camera.combined);
        drawMobsRoutes();
        debugCamera.update();
        Game.batch.begin();
        drawMobId();
        Game.batch.end();
    }

    public void drawSelectionIndicator(Rectangle selectionIndicator) {
        setProjectionMatrix(Game.camera.combined);
        setColor(Color.RED);
        begin(ShapeType.Line);
        rect(selectionIndicator.x,
                selectionIndicator.y,
                selectionIndicator.width,
                selectionIndicator.height);
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
        if (!stage.routeDrawing) return;
        Game.mobs.forEach(this::drawRoute);
    }

    private void drawRoute(Mob mob) {
        if (ActionUtil.getTypeOfCurrentAction(mob) != Action.ActionType.MOVE) return;

        setProjectionMatrix(Game.camera.combined);
        begin(ShapeType.Filled);
        if (mob.movement.getPathMovement().getPath().size() == 0) {
            drawSteering(mob);
        } else {
            drawPath(mob);
        }
        end();
    }

    private void drawSteering(Mob mob) {
        setColor(Color.YELLOW);
        Mob target = getTargetMob(mob); //TODO throws exception when target is Destination
        if (target == null) return; // TODO deal with target system
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
