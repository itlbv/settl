package com.itlbv.settl.ui.elements;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.itlbv.settl.ui.elements.windows.GameInfo;
import com.itlbv.settl.ui.elements.windows.MobInfo;
import com.itlbv.settl.ui.elements.windows.Window;

public class RootTable extends Table {

    private Window mobInfo;
    private Window gameInfo;

    public RootTable() {
        super();
        setup();
    }

    private void setup() {
        setFillParent(true);
        mobInfo = new MobInfo();
        gameInfo = new GameInfo();
        this.add(mobInfo).width(400).expand().bottom().left();
        this.add(gameInfo).width(370).expand().bottom().right();
    }

    public void update() {
        mobInfo.update();
        gameInfo.update();
    }
}