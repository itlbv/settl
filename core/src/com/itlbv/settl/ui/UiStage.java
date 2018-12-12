package com.itlbv.settl.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class UiStage extends Stage {
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
}
