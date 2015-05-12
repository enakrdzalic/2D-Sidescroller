package com.defy.gravity.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.defy.gravity.DefyGravity;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Defy Gravity";
        config.width = 272;
        config.height = 408;
		new LwjglApplication(new DefyGravity(), config);
	}
}
