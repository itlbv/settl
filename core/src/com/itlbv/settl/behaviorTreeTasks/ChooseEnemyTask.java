package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.Game;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.mobs.managers.TaskManager;
import sun.plugin2.message.OverlayWindowMoveMessage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ChooseEnemyTask extends LeafTask<Mob>{
    @Override
    public Status execute() {
        getObject().chooseEnemy();
        return Status.SUCCEEDED;
    }

    @Override
    protected Task copyTo(Task task) {
        return task;
    }
}
