package com.itlbv.settl.ui.elements.windows;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.itlbv.settl.util.GameUtil.gameSpeed;

public class GameInfo extends Window {
    public GameInfo() {
        super("gameInfo");
        setup();
    }

    @Override
    void setup() {
        Map<String, String> labels = new LinkedHashMap<>();
        labels.put("Game speed:", "gameSpeed");
        createLabels(labels);
    }

    @Override
    public void update() {
        String gameSpeedText = "";
        switch (gameSpeed) {
            case 0:
                gameSpeedText = "PAUSE";
                break;
            case 1:
                gameSpeedText = "NORMAL";
                break;
            case 2:
                gameSpeedText = "SLOW";
                break;
            case 3:
                gameSpeedText = "VERY SLOW";
                break;
        }
        updateLabel("gameSpeed", gameSpeedText);
    }
}
