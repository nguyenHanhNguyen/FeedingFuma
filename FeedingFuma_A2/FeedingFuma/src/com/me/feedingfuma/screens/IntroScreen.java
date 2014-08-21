package com.me.feedingfuma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.me.mygdxgame.FeedingFuma;

public class IntroScreen  implements Screen {

	FeedingFuma myGame;
	
	public IntroScreen(FeedingFuma mg) {
		this.myGame = mg;
	}

	Texture intro;
	SpriteBatch batch;
	float time = 0;
	long startTime;
	AssetManager manager;
	
	@Override
	public void show() {
		manager = new AssetManager();
		manager.load("data/intro_screen.jpg", Texture.class);
		manager.finishLoading();
		intro = manager.get("data/intro_screen.jpg", Texture.class);
		batch = new SpriteBatch();
		startTime = TimeUtils.millis();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(intro, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		batch.end();
		time += delta;

		if(TimeUtils.millis() > (startTime + 5000)) {
			myGame.setScreen(myGame.game_screen);
		}
		
	}

	@Override
	public void hide() {
		Gdx.app.log("dispose", "dispose intro");
		batch.dispose();
		manager.dispose();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
