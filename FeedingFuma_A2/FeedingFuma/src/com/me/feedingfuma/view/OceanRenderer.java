package com.me.feedingfuma.view;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.feedingfuma.model.Fuma;
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
	private float ppuX;	// pixels per unit on the X axis
	private float ppuY;	// pixels per unit on the Y axis
	public void setSize (int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
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
		yellowFishTexture = new Texture(Gdx.files.internal("data/yellowFish.png"));
		blueFishTexture = new Texture(Gdx.files.internal("data/blueFish.png"));
		rightBlueFishTexture = new Texture(Gdx.files.internal("data/right_blueFish.png"));
		lahanTexture = new Texture(Gdx.files.internal("data/lahan.png"));
		normalFish = new Texture (Gdx.files.internal("data/fish2.png"));
		bigFishTexture = new Texture(Gdx.files.internal("data/big_fish.png"));
		pirahnaTexture = new Texture(Gdx.files.internal("data/pirahna.png"));
		
	}
	
	public void render() {
		spriteBatch.begin();
		drawFuma();
		drawOtherFish();
		spriteBatch.end();
	}

	private void drawFuma() {
		Fuma fuma = ocean.getFuma();
		if(fuma.isFacingRight()) {
		  spriteBatch.draw(fumaTexture, fuma.getPosition().x * ppuX, fuma.getPosition().y * ppuY, Fuma.SIZE * ppuX, Fuma.SIZE * ppuY);
		}
		if(!fuma.isFacingRight()) {
		  spriteBatch.draw(leftFuma, fuma.getPosition().x * ppuX, fuma.getPosition().y * ppuY, Fuma.SIZE * ppuX, Fuma.SIZE * ppuY);
		}
		
	}
	
	private void drawOtherFish() {
		ArrayList<OtherFish> fish = ocean.getFish();
		//Gdx.app.log("number of fish ocean renderer", String.valueOf(fish.size()));
		for(int i = 0 ; i < fish.size() ; i ++) {
				if(fish.get(i).getGenre() == Genre.YellowFish) {
					spriteBatch.draw(yellowFishTexture,fish.get(i).getPosition().x * ppuX, fish.get(i).getPosition().y * ppuY, fish.get(i).getSize() * ppuX , fish.get(i).getSize() * ppuY);
				}
				if(fish.get(i).getGenre() == Genre.BlueFish && fish.get(i).getDir().equalsIgnoreCase("left")) {
					spriteBatch.draw(blueFishTexture,fish.get(i).getPosition().x * ppuX, fish.get(i).getPosition().y * ppuY, fish.get(i).getSize() * ppuX , fish.get(i).getSize() * ppuY);
				}
				if(fish.get(i).getGenre() == Genre.BlueFish && fish.get(i).getDir().equalsIgnoreCase("right")) {
					spriteBatch.draw(rightBlueFishTexture,fish.get(i).getPosition().x * ppuX, fish.get(i).getPosition().y * ppuY, fish.get(i).getSize() * ppuX , fish.get(i).getSize() * ppuY);
				}
				if(fish.get(i).getGenre() == Genre.LaHan) {
					spriteBatch.draw(lahanTexture,fish.get(i).getPosition().x * ppuX, fish.get(i).getPosition().y * ppuY, fish.get(i).getSize() * ppuX , fish.get(i).getSize() * ppuY);
				}
				if(fish.get(i).getGenre() == Genre.NormalFish) {
					spriteBatch.draw(normalFish,fish.get(i).getPosition().x * ppuX, fish.get(i).getPosition().y * ppuY, fish.get(i).getSize() * ppuX , fish.get(i).getSize() * ppuY);
				}
				if(fish.get(i).getGenre() == Genre.BigFish) {
					spriteBatch.draw(bigFishTexture,fish.get(i).getPosition().x * ppuX, fish.get(i).getPosition().y * ppuY, fish.get(i).getSize() * ppuX , fish.get(i).getSize() * ppuY);
				}
				if(fish.get(i).getGenre() == Genre.Pirahna) {
					spriteBatch.draw(pirahnaTexture,fish.get(i).getPosition().x * ppuX, fish.get(i).getPosition().y * ppuY, fish.get(i).getSize() * ppuX , fish.get(i).getSize() * ppuY);
				}
				
			}
		
	}
	
	private void drawScore() {
		
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
