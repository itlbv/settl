package com.itlbv.settl.ui.elements.windows;

import com.itlbv.settl.ui.util.UiUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.itlbv.settl.ui.UiStage.selectedMob;

public class MobInfo extends Window {
    public MobInfo() {
        super("mobInfo");
        setup();
    }

    @Override
    void setup() {
        Map<String, String> labels = new LinkedHashMap<>();
        labels.put("Mob:", "selectedMob");
        labels.put("Target:", "target");
        labels.put("Action:", "action");
        labels.put("Body pos:", "bodyPos");
        labels.put("Sensor pos:", "sensorPos");
        createLabels(labels);
    }

    @Override
    public void update() {
        if (selectedMob == null) {
            updateLabel("selectedMob", "-");
            updateLabel("target", "-");
            updateLabel("action", "-");
            updateLabel("bodyPos", "-");
            updateLabel("sensorPos", "-");
        } else {
            updateLabel("selectedMob", selectedMob.toString());
            updateLabel("target", selectedMob.getTarget() == null ? "" : selectedMob.getTarget().toString());
            updateLabel("action", selectedMob.hasNoActions() ? "" : selectedMob.getAction().toString());
            updateLabel("bodyPos", UiUtil.vectorToString(selectedMob.getPosition()));
            updateLabel("sensorPos", UiUtil.vectorToString(selectedMob.getSensorPosition()));
        }
    }
}

