package com.itlbv.settl.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.itlbv.settl.Game;

public class DesktopLauncher {
	private static final int INITIAL_SCREEN_WIDTH_PXL = 1200;
	private static final int INITIAL_SCREEN_HEIGHT_PXL = 1200;

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = INITIAL_SCREEN_WIDTH_PXL;
		config.height = INITIAL_SCREEN_HEIGHT_PXL;
		new LwjglApplication(new Game(), config);
	}
}
