package com.me.mygdxgame;

import com.badlogic.gdx.Game;
import com.me.feedingfuma.screens.GameOverScreen;
import com.me.feedingfuma.screens.GameScreen;
import com.me.feedingfuma.screens.IntroScreen;

public class FeedingFuma extends Game {
	
	//ntn
	public IntroScreen intro_screen;
	public GameOverScreen game_over_screen;
	public GameScreen game_screen;
	//ntn-done
	
	@Override
	public void create() {
		//setScreen(new IntroScreen());
		
		//ntn
		intro_screen = new IntroScreen(this);
		game_screen = new GameScreen(this);
		game_over_screen = new GameOverScreen(this);
		setScreen(intro_screen);
		//ntn-done
	}
	
}
