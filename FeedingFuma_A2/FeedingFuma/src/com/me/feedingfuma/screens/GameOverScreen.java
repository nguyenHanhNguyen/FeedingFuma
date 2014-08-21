package com.me.feedingfuma.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameOverScreen extends FumaScreen{

	public GameOverScreen(Game game) {
		super(game);
	}

	TextureRegion gameover;
	SpriteBatch batch;
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(gameover, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		Gdx.app.log("draw game over","game over");
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		gameover = new TextureRegion(new Texture(Gdx.files.internal("data/game_over.jpg")));
		batch = new SpriteBatch();
		
	}

	@Override
	public void hide() {
		batch.dispose();
		gameover.getTexture().dispose();
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
