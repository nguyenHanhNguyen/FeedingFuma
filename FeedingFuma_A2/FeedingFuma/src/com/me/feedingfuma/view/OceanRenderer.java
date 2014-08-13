package com.me.feedingfuma.view;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

	
	private SpriteBatch spriteBatch;
	private Sprite fumaSprite;
	private Sprite leftFumaSprite;
	private Sprite blueFishSprite;
	private Sprite rightBlueFishSprite;
	private Sprite yellowFishSprite;
	private Sprite normalFishSprite;
	private Sprite lahanSprite;
	private Sprite bigFishSprite;
	private Sprite pirahnaSprite;
	private Sprite backgroundSprite;
	
	
	
	private int width;
	private int height;
	private float ppuX; // pixels per unit on the X axis
	private float ppuY; // pixels per unit on the Y axis
	private AssetManager assetManager;
	private TextureAtlas atlas;
	
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
		// use asset manager for better memory management
		assetManager = new AssetManager();
		assetManager.load("data/FeedingFuma.pack",TextureAtlas.class);
		assetManager.finishLoading();
		atlas = new TextureAtlas();
		atlas = assetManager.get("data/FeedingFuma.pack",TextureAtlas.class);
		
		fumaSprite = new Sprite(atlas.findRegion("golden_fish"));
		leftFumaSprite = new Sprite(atlas.findRegion("golden_fish_left"));
		blueFishSprite = new Sprite(atlas.findRegion("blueFish"));
		rightBlueFishSprite = new Sprite(atlas.findRegion("right_blueFish"));
		yellowFishSprite = new Sprite(atlas.findRegion("yellowFish"));
		normalFishSprite = new Sprite(atlas.findRegion("fish2"));
		lahanSprite = new Sprite(atlas.findRegion("lahan"));
		bigFishSprite = new Sprite(atlas.findRegion("big_fish"));
		pirahnaSprite = new Sprite(atlas.findRegion("pirahna"));
		
	}

	public void render() {
		spriteBatch.begin();
		drawBackground();
		drawScore();
		if(ocean.getFuma().state != State.DIE) {
			drawFuma();
		}
		drawOtherFish();
		spriteBatch.end();
		
		//drawDebug();
	}

	private void drawFuma() {
		Fuma fuma = ocean.getFuma();
		if (ocean.getLevel().getScore() < 100) {
			fuma.setSize(0.5f);
			fuma.setBounds(0.5f,0.5f);
		} if(ocean.getLevel().getScore() > 100) {
			fuma.setSize(0.8f);
			fuma.setBounds(0.8f,0.8f);
		} if(ocean.getLevel().getScore() > 200) {
			fuma.setSize(1f);
			fuma.setBounds(1f,1f);
		} if(ocean.getLevel().getScore() > 250) {
			fuma.setSize(1.2f);
			fuma.setBounds(1.5f,1.5f);
		} 

		if (fuma.isFacingRight()) {
			fumaSprite.setSize(fuma.getSize() * ppuX, fuma.getSize() * ppuY);
			fumaSprite.setPosition(fuma.getPosition().x * ppuX,fuma.getPosition().y * ppuY);
			fumaSprite.draw(spriteBatch);
			
		}
		
		if (!fuma.isFacingRight()) {
			leftFumaSprite.setSize(fuma.getSize() * ppuX, fuma.getSize() * ppuY);
			leftFumaSprite.setPosition(fuma.getPosition().x * ppuX,fuma.getPosition().y * ppuY);
			leftFumaSprite.draw(spriteBatch);
			
		}

	}

	private void drawOtherFish() {
		ArrayList<OtherFish> fish = ocean.getFish();
		for (int i = 0; i < fish.size(); i++) {
			if (fish.get(i).getGenre() == Genre.YellowFish) {
				yellowFishSprite.setPosition(fish.get(i).getPosition().x* ppuX, fish.get(i).getPosition().y * ppuY);
				yellowFishSprite.setSize(fish.get(i).getSize() * ppuX, fish.get(i).getSize() * ppuY);
				yellowFishSprite.draw(spriteBatch);
					
			}
			if (fish.get(i).getGenre() == Genre.BlueFish
					&& fish.get(i).getFacingRight() == false) {
				blueFishSprite.setSize(fish.get(i).getSize() * ppuX, fish.get(i).getSize() * ppuY);
				blueFishSprite.setPosition(fish.get(i).getPosition().x* ppuX, fish.get(i).getPosition().y * ppuY);
				blueFishSprite.draw(spriteBatch);
				
			}
			if (fish.get(i).getGenre() == Genre.BlueFish
					&& fish.get(i).getFacingRight() == true) {
				rightBlueFishSprite.setSize(fish.get(i).getSize() * ppuX, fish.get(i).getSize() * ppuY);
				rightBlueFishSprite.setPosition(fish.get(i).getPosition().x* ppuX, fish.get(i).getPosition().y * ppuY);
				rightBlueFishSprite.draw(spriteBatch);
				
			}
			
			if (fish.get(i).getGenre() == Genre.LaHan) {
				lahanSprite.setPosition(fish.get(i).getPosition().x* ppuX, fish.get(i).getPosition().y * ppuY);
				lahanSprite.setSize(fish.get(i).getSize() * ppuX, fish.get(i).getSize() * ppuY);
				lahanSprite.draw(spriteBatch);				
				
			}
			if (fish.get(i).getGenre() == Genre.NormalFish) {
				normalFishSprite.setPosition(fish.get(i).getPosition().x * ppuX, fish.get(i).getPosition().y * ppuY);
				normalFishSprite.setSize(fish.get(i).getSize()* ppuX, fish.get(i).getSize() * ppuY);
				normalFishSprite.draw(spriteBatch);				
				
			}
			if (fish.get(i).getGenre() == Genre.BigFish) {
				bigFishSprite.setPosition(fish.get(i).getPosition().x* ppuX, fish.get(i).getPosition().y * ppuY);
				bigFishSprite.setSize(fish.get(i).getSize() * ppuX, fish.get(i).getSize() * ppuY);
				bigFishSprite.draw(spriteBatch);
		
			}
			if (fish.get(i).getGenre() == Genre.Pirahna) {
				pirahnaSprite.setPosition(fish.get(i).getPosition().x* ppuX, fish.get(i).getPosition().y * ppuY);
				pirahnaSprite.setSize(fish.get(i).getSize() * ppuX, fish.get(i).getSize() * ppuY);
				pirahnaSprite.draw(spriteBatch);
						
			}

		}

	}

	private void drawScore() {
		score.draw(spriteBatch, "Score " + ocean.getLevel().getScore(), 50, 700);
	}
	
	
	private void drawBackground() {
		if(ocean.getLevel().getLevelName().equalsIgnoreCase("level1")) {
			backgroundSprite = new Sprite (atlas.findRegion("level1"));
			backgroundSprite.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			backgroundSprite.setPosition(0, 0);
			backgroundSprite.draw(spriteBatch);
		}
	
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
		
		// render Fuma
		Fuma fuma = ocean.getFuma();
		Rectangle rect = fuma.getBounds();
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		float x1 = fuma.getPosition().x + rect.x;
		float y1 = fuma.getPosition().y + rect.y;
		debugRenderer.rect(x1, y1, rect.width, rect.height);
		debugRenderer.end();
	}
	

	public void dispose() {
		fumaSprite.getTexture().dispose();
		leftFumaSprite.getTexture().dispose();
		blueFishSprite.getTexture().dispose();
		rightBlueFishSprite.getTexture().dispose();
		
		/*fumaTexture.dispose();
		leftFuma.dispose();
		yellowFishTexture.dispose();
		blueFishTexture.dispose();
		normalFish.dispose();
		lahanTexture.dispose();
		bigFishTexture.dispose();
		pirahnaTexture.dispose();
		background.getTexture().dispose();*/
		
	}
}
