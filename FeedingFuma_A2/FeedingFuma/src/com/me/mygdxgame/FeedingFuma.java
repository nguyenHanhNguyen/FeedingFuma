package com.me.mygdxgame;

import com.badlogic.gdx.Game;
import com.me.feedingfuma.screens.GameScreen;

public class FeedingFuma extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen());
	}
	
}
