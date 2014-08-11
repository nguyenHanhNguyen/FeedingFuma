package com.me.feedingfuma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.GL10;
import com.me.feedingfuma.controller.OceanController;
import com.me.feedingfuma.model.Ocean;
import com.me.feedingfuma.view.OceanRenderer;

public class GameScreen implements Screen,InputProcessor{

	private Ocean ocean;
	private OceanController oceanController;
	private OceanRenderer oceanRenderer;
	
	private int width, height;
	
	public void show() {
		ocean = new Ocean();
		oceanRenderer = new OceanRenderer(ocean);
		oceanController = new OceanController(ocean);
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		oceanController.update(delta);
		oceanRenderer.render();
	}
	
	@Override
	public void resize(int width, int height) {
		oceanRenderer.setSize(width,height);
		this.width = width;
		this.height = height;
		
	}

	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		// left
		if (x < width / 2 && y > height / 2) {
			oceanController.leftPressed();
		}
		//right
		if (x > width / 2 && y > height / 2) {
			oceanController.rightPressed();
		}
		//up
		if(x < width / 2 && y < height / 2) {
			oceanController.upPressed();
		}
		//down
		if(x > width / 2 && y < height / 2) {
			oceanController.downPressed();
		}
		return true;
	}

	public boolean touchUp(int x, int y, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		if (x < width / 2 && y > height / 2) {
			oceanController.leftReleased();
		}
		if (x > width / 2 && y > height / 2) {
			oceanController.rightReleased();
		}
		if(x < width / 2 && y < height / 2) {
			oceanController.upReleased();
		}
		//down
		if(x > width / 2 && y < height / 2) {
			oceanController.downReleased();
		}
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
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
	}

}
