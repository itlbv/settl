package com.itlbv.settl.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.itlbv.settl.Game;

public class DesktopLauncher {
	private static final int INITIAL_SCREEN_WIDTH_PXL = 2000;
	private static final int INITIAL_SCREEN_HEIGHT_PXL = 1500;

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(INITIAL_SCREEN_WIDTH_PXL, INITIAL_SCREEN_HEIGHT_PXL);
		new Lwjgl3Application(new Game(), config);
	}
}
