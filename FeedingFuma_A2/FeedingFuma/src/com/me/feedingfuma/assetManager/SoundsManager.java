package com.me.feedingfuma.assetManager;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundsManager {

	private static HashMap<String,Sound> sounds;
	
	static{
		sounds = new HashMap<String,Sound>();
	}
	
	public void load(String path, String name) {
		Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
		sounds.put(name, sound);
	}
	
	public void play(String name) {
		sounds.get(name).play();
	}
	
	public void stop(String name) {
		sounds.get(name).stop();
	}
	
	public void dispose(String name) {
		sounds.get(name).dispose();
	}
}
