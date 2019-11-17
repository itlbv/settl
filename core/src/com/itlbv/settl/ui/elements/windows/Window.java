package com.itlbv.settl.ui.elements.windows;

import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisWindow;

import java.util.HashMap;
import java.util.Map;

public abstract class Window extends VisWindow {

    private Map<String, VisLabel> labels = new HashMap<>();

    Window(String title) {
        super(title);
    }

    void setup() {
    }

    public void update() {
    }

    void createLabels(Map<String, String> labNames) {
        for (Map.Entry<String, String> entry : labNames.entrySet()) {
            String displayedText = entry.getKey();
            String nameOfInfoLabel = entry.getValue();

            VisLabel labelWithText = new VisLabel(displayedText);
            VisLabel labelWithInfo = new VisLabel();
            labelWithInfo.setName(nameOfInfoLabel);

            this.add(labelWithText).right();
            this.add(labelWithInfo).expandX();
            this.row();

            labels.put(nameOfInfoLabel, labelWithInfo);
        }
    }

    void updateLabel(String labelName, String newText) {
        labels.get(labelName).setText(newText);
    }
}
