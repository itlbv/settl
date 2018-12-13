package com.itlbv.settl.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.itlbv.settl.Game;
import com.itlbv.settl.mobs.Mob;

public class UiStage extends Stage {
    private Mob selectedMob;

    private Table table;
    private BitmapFont font;
    public Label labelSelectedMob;

    public UiStage() {
        font = new BitmapFont(Gdx.files.internal("font26.fnt"));

        table = new Table();
        table.setFillParent(true);
        addActor(table);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        labelSelectedMob = new Label("selected mob", labelStyle);
        labelSelectedMob.setPosition(0, 0);
        labelSelectedMob.setSize(100,100);
        addActor(labelSelectedMob);
    }

    public void update() {
        checkSelectedMob();
        act(Game.DELTA_TIME);
    }

    private void checkSelectedMob() {
        if (selectedMob == null) {
            labelSelectedMob.setText("no mob selected");
            return;
        }
        labelSelectedMob.setText(selectedMob.toString());
    }

    public Mob getSelectedMob() {
        return selectedMob;
    }

    public void setSelectedMob(Mob selectedMob) {
        this.selectedMob = selectedMob;
    }
}
