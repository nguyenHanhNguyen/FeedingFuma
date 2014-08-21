package com.me.feedingfuma.model;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.me.feedingfuma.model.OtherFish.Genre;

public class Level1 {

	int live = 5;
	ArrayList<OtherFish> fishes;
	ArrayList<Fuma> fuma;
	Random ran;
	int score;

	Sprite background;
	AssetManager assetManager;
	TextureAtlas atlas;
	String levelName = "Level1";
	boolean pause = false;
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLive() {
		return live;
	}

	public void setLive(int live) {
		this.live = live;
	}

	public Fuma getFuma(int order) {
		return fuma.get(order);
	}

	public ArrayList<Fuma> getAllFuma() {
		return fuma;
	}

	public void setFuma(ArrayList<Fuma> fuma) {
		this.fuma = fuma;
	}

	public ArrayList<OtherFish> getFish() {
		return fishes;
	}

	public void setFish(ArrayList<OtherFish> fishes) {
		this.fishes = fishes;
	}

	public Level1() {
		loadLevel1();
	}

	public String getLevelName() {
		return levelName;
	}
	
	public boolean getPause() {
		return pause;
	}
	
	public void setPause(boolean pause) {
		this.pause = pause;
	}

	private void loadLevel1() {
		ran = new Random();
		fishes = new ArrayList<OtherFish>();
		fuma = new ArrayList<Fuma>();

		// put Fuma randomly on screen
		for (int i = 0; i < 5; i++) {
			fuma.add(new Fuma(new Vector2((ran.nextInt(10 - 5)), (ran
					.nextInt(10 - 2)))));
		}
		// small fishes
		for (int i = 0; i < 30; i++) {
			fishes.add(new OtherFish(new Vector2((ran.nextInt(10 - 5)), (ran
					.nextInt(10 - 2)))));
			fishes.get(i).setGenre(Genre.BlueFish);
		}

		for (int i = 0; i < 5; i++) {
			fishes.add(new OtherFish(new Vector2((ran.nextInt(4)), (ran
					.nextInt(3)))));
			fishes.get(i).setGenre(Genre.NormalFish);
		}

		for (int i = 0; i < 3; i++) {
			fishes.add(new OtherFish(new Vector2((ran.nextInt(8)), (ran
					.nextInt(7)))));
			fishes.get(i).setGenre(Genre.LaHan);
		}
		
		for(int i = 0 ; i < 1 ; i++) {
			fishes.add(new OtherFish(new Vector2((ran.nextInt(5)), (ran
					.nextInt(7)))));
			fishes.get(i).setGenre(Genre.BigFish);
		}

		for (int i = 0; i < fishes.size(); i++) {
			if (fishes.get(i).getGenre() == Genre.LaHan) {
				fishes.get(i).setSize(0.6f);
				fishes.get(i).setSpeed(3.5f);
			}
			if (fishes.get(i).getGenre() == Genre.BlueFish) {
				fishes.get(i).setSize(0.3f);
				fishes.get(i).setSpeed(1.0f);
			}
			if (fishes.get(i).getGenre() == Genre.NormalFish) {
				fishes.get(i).setSize(0.7f);
				fishes.get(i).setSpeed(2f);
			}
			if (fishes.get(i).getGenre() == Genre.BigFish) {
				fishes.get(i).setSize(2f);
				fishes.get(i).setSpeed(2f);
			}
			
			fishes.get(i).getBounds().width = fishes.get(i).getSize();
			fishes.get(i).getBounds().height = fishes.get(i).getSize();
		}
	}

	public void randomDir() {	
		for (int i = 0; i < fishes.size() / 2; i++) {
			if (i % 2 == 0) {
				fishes.get(i).setDir("LEFT");
				fishes.get(i).setFacingRight(false);
			}
			if (i % 2 != 0) {
				fishes.get(i).setDir("RIGHT");
				fishes.get(i).setFacingRight(true);
			}
		}

		for (int i = fishes.size() / 2; i < fishes.size(); i++) {
			if (i % 2 == 0) {
				fishes.get(i).setDir("UP");
				if (fishes.get(i).getFacingRight() == true) {
					fishes.get(i).setFacingRight(true);
				} else {
					fishes.get(i).setFacingRight(false);
				}
			}
			if (i % 2 != 0) {
				fishes.get(i).setDir("DOWN");
				fishes.get(i).setFacingRight(true);
				if (fishes.get(i).getFacingRight() == true) {
					fishes.get(i).setFacingRight(true);
				} else {
					fishes.get(i).setFacingRight(false);
				}
			}
		}
	}

	public void updateFishRandom() {
		float delta = Gdx.graphics.getDeltaTime();
		randomDir();
		// Gdx.app.log("number of fish", String.valueOf(fishes.size()));
		for (int i = 0; i < fishes.size(); i++) {
			if (fishes.get(i).getDir().equalsIgnoreCase("UP")) {
				fishes.get(i).getVel().y = fishes.get(i).getSpeed();
				// fishes.get(i).setBounds(fishes.get(i).getPosition().x,fishes.get(i).getPosition().y);
			}
			if (fishes.get(i).getDir().equalsIgnoreCase("DOWN")) {
				fishes.get(i).getVel().y = -fishes.get(i).getSpeed();
				// fishes.get(i).setBounds(fishes.get(i).getPosition().x,fishes.get(i).getPosition().y);
			}
			if (fishes.get(i).getDir().equalsIgnoreCase("RIGHT")) {
				fishes.get(i).getVel().x = fishes.get(i).getSpeed();
				// fishes.get(i).setBounds(fishes.get(i).getPosition().x,fishes.get(i).getPosition().y);
			}
			if (fishes.get(i).getDir().equalsIgnoreCase("LEFT")) {
				fishes.get(i).getVel().x = -fishes.get(i).getSpeed();
				// fishes.get(i).setBounds(fishes.get(i).getPosition().x,fishes.get(i).getPosition().y);
			}
			fishes.get(i).update(delta);
		}
	}
}
