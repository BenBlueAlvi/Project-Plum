package com.ue.projectplum.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ue.projectplum.projectPlum;


public class DesktopLauncher {
	
	public static boolean directToGame = true;
	public static void main (String[] arg) {
		projectPlum theGame = new projectPlum();
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Planetary Strategery";
		cfg.height = projectPlum.viewHeight;
		cfg.width =  projectPlum.viewWidth;
	
		//cfg.addIcon("assets/icon.png", FileType.Internal);

		
		@SuppressWarnings("unused")
		LwjglApplication launcher = new LwjglApplication(theGame, cfg);
	}
}
