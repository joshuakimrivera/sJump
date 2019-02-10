package com.sillygames.sJump.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sillygames.sJump.gameCore;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Killing Spree";
		config.height = 720;
		config.width = 1280;
		config.vSyncEnabled = true;
//		config.fullscreen = true;
		new LwjglApplication(new gameCore(new DesktopServices()), config);
	}
}
