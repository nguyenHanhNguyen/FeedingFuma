package com.me.feedingfuma.view;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.me.feedingfuma.model.Fuma;
import com.me.feedingfuma.model.Fuma.State;
import com.me.feedingfuma.model.Level1;
import com.me.feedingfuma.model.Ocean;
import com.me.feedingfuma.model.OtherFish;
import com.me.feedingfuma.model.OtherFish.Genre;

public class OceanRenderer {
	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;

	private Ocean ocean;
	private OrthographicCamera cam;

	private Texture fumaTexture;
	private Texture leftFuma;
	private Texture yellowFishTexture;
	private Texture blueFishTexture;
	private Texture normalFish;
	private Texture lahanTexture;
	private Texture bigFishTexture;
	private Texture pirahnaTexture;
	private Texture rightBlueFishTexture;

	private SpriteBatch spriteBatch;
	private int width;
	private int height;
	private float ppuX; // pixels per unit on the X axis
	private float ppuY; // pixels per unit on the Y axis
	BitmapFont score = new BitmapFont();
	Level1 level = new Level1();
	ShapeRenderer debugRenderer = new ShapeRenderer();
		
	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float) width / CAMERA_WIDTH;
		ppuY = (float) height / CAMERA_HEIGHT;
		Gdx.app.log("width", String.valueOf(this.width));
		Gdx.app.log("height", String.valueOf(this.height));
	}

	public OceanRenderer(Ocean ocean) {
		this.ocean = ocean;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		spriteBatch = new SpriteBatch();
		loadTextures();
	}

	private void loadTextures() {
		leftFuma = new Texture(Gdx.files.internal("data/golden_fish_left.png"));
		fumaTexture = new Texture(Gdx.files.internal("data/golden_fish.png"));
		yellowFishTexture = new Texture(
				Gdx.files.internal("data/yellowFish.png"));
		blueFishTexture = new Texture(Gdx.files.internal("data/blueFish.png"));
		rightBlueFishTexture = new Texture(
				Gdx.files.internal("data/right_blueFish.png"));
		lahanTexture = new Texture(Gdx.files.internal("data/lahan.png"));
		normalFish = new Texture(Gdx.files.internal("data/fish2.png"));
		bigFishTexture = new Texture(Gdx.files.internal("data/big_fish.png"));
		pirahnaTexture = new Texture(Gdx.files.internal("data/pirahna.png"));

	}

	public void render() {
		spriteBatch.begin();
		drawScore();
		if(ocean.getFuma().state != State.DIE) {
			drawFuma();
		}
		drawOtherFish();
		spriteBatch.end();
		
		drawDebug();
	}

	private void drawFuma() {
		Fuma fuma = ocean.getFuma();
		if (ocean.getLevel().getScore() < 100) {
			fuma.setSize(1f);
			fuma.setBounds(1f,1f);
		} if(ocean.getLevel().getScore() > 100) {
			fuma.setSize(1.2f);
			fuma.setBounds(1.2f,1.2f);
		}

		if (fuma.isFacingRight()) {
			spriteBatch.draw(fumaTexture, fuma.getPosition().x * ppuX,
					fuma.getPosition().y * ppuY, fuma.getSize() * ppuX, fuma.getSize()
							* ppuY);
		}
		if (!fuma.isFacingRight()) {
			spriteBatch.draw(leftFuma, fuma.getPosition().x * ppuX,
					fuma.getPosition().y * ppuY, fuma.getSize() * ppuX, fuma.getSize()
							* ppuY);
		}

	}

	private void drawOtherFish() {
		ArrayList<OtherFish> fish = ocean.getFish();
		for (int i = 0; i < fish.size(); i++) {
			if (fish.get(i).getGenre() == Genre.YellowFish) {
				spriteBatch.draw(yellowFishTexture, fish.get(i).getPosition().x
						* ppuX, fish.get(i).getPosition().y * ppuY, fish.get(i)
						.getSize() * ppuX, fish.get(i).getSize() * ppuY);
			}
			if (fish.get(i).getGenre() == Genre.BlueFish
					&& fish.get(i).getDir().equalsIgnoreCase("left")) {
				spriteBatch.draw(blueFishTexture, fish.get(i).getPosition().x
						* ppuX, fish.get(i).getPosition().y * ppuY, fish.get(i)
						.getSize() * ppuX, fish.get(i).getSize() * ppuY);
			}
			if (fish.get(i).getGenre() == Genre.BlueFish
					&& fish.get(i).getDir().equalsIgnoreCase("right")) {
				spriteBatch.draw(rightBlueFishTexture, fish.get(i)
						.getPosition().x * ppuX, fish.get(i).getPosition().y
						* ppuY, fish.get(i).getSize() * ppuX, fish.get(i)
						.getSize() * ppuY);
			}
			if (fish.get(i).getGenre() == Genre.LaHan) {
				spriteBatch.draw(lahanTexture, fish.get(i).getPosition().x
						* ppuX, fish.get(i).getPosition().y * ppuY, fish.get(i)
						.getSize() * ppuX, fish.get(i).getSize() * ppuY);
			}
			if (fish.get(i).getGenre() == Genre.NormalFish) {
				spriteBatch.draw(normalFish,
						fish.get(i).getPosition().x * ppuX, fish.get(i)
								.getPosition().y * ppuY, fish.get(i).getSize()
								* ppuX, fish.get(i).getSize() * ppuY);
			}
			if (fish.get(i).getGenre() == Genre.BigFish) {
				spriteBatch.draw(bigFishTexture, fish.get(i).getPosition().x
						* ppuX, fish.get(i).getPosition().y * ppuY, fish.get(i)
						.getSize() * ppuX, fish.get(i).getSize() * ppuY);
			}
			if (fish.get(i).getGenre() == Genre.Pirahna) {
				spriteBatch.draw(pirahnaTexture, fish.get(i).getPosition().x
						* ppuX, fish.get(i).getPosition().y * ppuY, fish.get(i)
						.getSize() * ppuX, fish.get(i).getSize() * ppuY);
			}

		}

	}

	private void drawScore() {
		score.setColor(0.0f, 0.0f, 1.0f, 1.0f);
		score.draw(spriteBatch, "Score " + ocean.getLevel().getScore(), 50, 700);
	}
	
	private void drawDebug() {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Rectangle);
		
		ArrayList<OtherFish> fishes = ocean.getFish(); 
		for (int i = 0 ; i < fishes.size() ; i++) {
			Rectangle rect = fishes.get(i).getBounds();
			debugRenderer.setColor(new Color(1, 0, 0, 1));
			float x1 = fishes.get(i).getPosition().x + rect.x;
			float y1 = fishes.get(i).getPosition().y + rect.y;
			debugRenderer.rect(x1, y1, rect.width, rect.height);
		}
		
		// render Bob
		Fuma fuma = ocean.getFuma();
		Rectangle rect = fuma.getBounds();
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		float x1 = fuma.getPosition().x + rect.x;
		float y1 = fuma.getPosition().y + rect.y;
		debugRenderer.rect(x1, y1, rect.width, rect.height);
		debugRenderer.end();
	}

	public void dispose() {
		fumaTexture.dispose();
		leftFuma.dispose();
		yellowFishTexture.dispose();
		blueFishTexture.dispose();
		normalFish.dispose();
		lahanTexture.dispose();
		bigFishTexture.dispose();
		pirahnaTexture.dispose();

	}
}
