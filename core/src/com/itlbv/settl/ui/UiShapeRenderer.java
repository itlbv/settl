package com.itlbv.settl.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.mobs.util.MobAnimationState;

class UiShapeRenderer extends ShapeRenderer {
    private Matrix4 projectionMatrix;

    UiShapeRenderer(Matrix4 projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    void drawSelectingRect(Mob mob) {
        setProjectionMatrix(projectionMatrix);
        setColor(Color.WHITE);
        begin(ShapeType.Line);
        rect(mob.getSelectingRectangle().x,
                mob.getSelectingRectangle().y,
                mob.getSelectingRectangle().width,
                mob.getSelectingRectangle().height);
        end();
    }

    void drawRoute(Mob mob) {
        if (mob.getState() != MobAnimationState.WALK) return; //TODO is it safe enough?
        setProjectionMatrix(projectionMatrix);
        begin(ShapeType.Filled);
        if (mob.getPath().size() == 0) {
            drawSteering(mob);
        } else {
            drawPath(mob);
        }
        end();
    }

    private void drawSteering(Mob mob) {
        setColor(Color.YELLOW);
        Mob target = (Mob) mob.getTarget();
        if (target == null) return; // TODO deal with target system
        rectLine(mob.getPosition().x, mob.getPosition().y, target.getPosition().x, target.getPosition().y, .1f);
    }

    private void drawPath(Mob mob) {
        setColor(Color.WHITE);
        Array<Vector2> waypoints = new Array<>(mob.getPath().nodes.size + 1);
        waypoints.add(mob.getPosition());
        mob.getPath().nodes.forEach(n -> waypoints.add(n.getPosition()));
        for (int i = 0; i < waypoints.size - 1; i++) {
            Vector2 currWaypoint = waypoints.get(i);
            Vector2 nextWaypoint = waypoints.get(i + 1);
            rectLine(currWaypoint.x, currWaypoint.y, nextWaypoint.x, nextWaypoint.y, .1f);
        }
    }
}
