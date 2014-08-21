package com.me.feedingfuma.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.me.feedingfuma.assetManager.SoundsManager;
import com.me.feedingfuma.model.Fuma;
import com.me.feedingfuma.model.Fuma.State;
import com.me.feedingfuma.model.Level1;
import com.me.feedingfuma.model.Ocean;
import com.me.feedingfuma.model.OtherFish;
import com.me.feedingfuma.model.OtherFish.Genre;

public class OceanController {
	Random rand = new Random();
	int score = 0;
	int live = 5;
	Level1 level;

	enum Keys {
		LEFT, RIGHT, UP, DOWN;
	}

	boolean pause = false;
	private Ocean ocean;
	private Fuma fuma;
	private ArrayList<Fuma> array_fuma;
	private ArrayList<OtherFish> fishes;
	private SoundsManager soundsManager;
	// use to detect collision
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {

		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}

	};

	static Map<Keys, Boolean> keys = new HashMap<OceanController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
	}

	ArrayList<Rectangle> otherFishRect = new ArrayList<Rectangle>();

	public OceanController(Ocean ocean) {
		fishes = new ArrayList<OtherFish>();
		level = new Level1();
		this.ocean = ocean;
		// get the last fish in the array list
		if (ocean.getLevel().getLive() > 0) {
			this.fuma = ocean.getLevel()
					.getFuma(ocean.getLevel().getLive() - 1);
		}
		this.array_fuma = ocean.getLevel().getAllFuma();
		this.fishes = ocean.getLevel().getFish();
		soundsManager = new SoundsManager();
	}

	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}

	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}

	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}

	public void upPressed() {
		keys.get(keys.put(Keys.UP, true));
	}

	public void downPressed() {
		keys.get(keys.put(Keys.DOWN, true));
	}

	public void upReleased() {
		keys.get(keys.put(Keys.UP, false));
	}

	public void downReleased() {
		keys.get(keys.put(Keys.DOWN, false));
	}

	public void update(float delta) {

		if (ocean.getLevel().getPause() == false) {
			ocean.getLevel().updateFishRandom();
			if (ocean.getLevel().getLive() > 0) {
				updateFuma();
			}
			fuma.update(delta);
			detectCollision(delta);
		}
	}

	// detect collision
	private void detectCollision(float delta) {

		fuma.getVelocity().mul(delta);
		Rectangle fumaRect = rectPool.obtain();
		fumaRect.set(fuma.getPosition().x, fuma.getPosition().y,
				fuma.getBounds().width, fuma.getBounds().height);

		for (int i = 0; i < fishes.size(); i++) {
			Rectangle fishRect = rectPool.obtain();
			fishRect.set(fishes.get(i).getPosition().x, fishes.get(i)
					.getPosition().y, fishes.get(i).getBounds().width, fishes
					.get(i).getBounds().height);
			// detect collision with small fish
			//Gdx.app.log("live", String.valueOf(live));
			if (fumaRect.overlaps(fishRect)) {
				if (fumaRect.width > fishRect.width) {
					// if (fumaRect.width > fishRect.width) {
					if (fishes.get(i).getGenre().equals(Genre.BlueFish)) {
						score += 10;
					}
					if (fishes.get(i).getGenre().equals(Genre.NormalFish)) {
						score += 20;
					}
					if (fishes.get(i).getGenre().equals(Genre.LaHan)) {
						score += 30;
					}
					if (fishes.get(i).getGenre().equals(Genre.BigFish)) {
						score += 50;
					}
					ocean.getLevel().setScore(score);
					fuma.setEatFish(true);
					fishes.remove(fishes.get(i));
					ocean.setFish(fishes);
					soundsManager.play("bite");

				} else {
					fuma.setState(State.DIE);
					pause = true;
					if (live > 0) {
						live--;
						ocean.getLevel().setLive(live);
						array_fuma.remove(fuma);
						ocean.setFuma(array_fuma);
						if(live > 1) {
							fuma = ocean.getFuma(live - 1);
						} 
						fuma.setState(State.SWIMMING);
						pause = false;
					}

				}
			} else {
				fuma.setEatFish(false);
			}
		}

	}

	private void processInput() {

		if (keys.get(Keys.LEFT)) {
			// left is pressed
			fuma.setFacingRight(false);
			fuma.setState(State.SWIMMING);
			fuma.setDir("LEFT");
		}
		if (keys.get(Keys.RIGHT)) {
			// right is pressed
			fuma.setFacingRight(true);
			fuma.setState(State.SWIMMING);
			fuma.setDir("RIGHT");
		}
		if (keys.get(Keys.UP)) {
			if (fuma.isFacingRight()) {
				fuma.setFacingRight(true);
			} else {
				fuma.setFacingRight(false);
			}
			fuma.setState(State.SWIMMING);
			fuma.setDir("UP");
		}
		if (keys.get(Keys.DOWN)) {
			if (fuma.isFacingRight()) {
				fuma.setFacingRight(true);
			} else {
				fuma.setFacingRight(false);
			}
			fuma.setFacingRight(true);
			fuma.setState(State.SWIMMING);
			fuma.setDir("DOWN");
		}

		if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT))
				|| (!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
			// fuma.setState(State.IDLE);
			// acceleration is 0 on the x
			fuma.getAcceleration().x = 0;
			// horizontal speed is 0
			fuma.getVelocity().x = 0;
		}

		if ((keys.get(Keys.UP) && keys.get(Keys.DOWN))
				|| (!keys.get(Keys.UP) && !(keys.get(Keys.DOWN)))) {
			// fuma.setState(State.IDLE);
			// acceleration is 0 on the y
			fuma.getAcceleration().y = 0;
			// vertical speed is 0
			fuma.getVelocity().y = 0;
		}

	}

	private void updateFuma() {
		processInput();

		if (fuma.getDir().equalsIgnoreCase("LEFT")) {
			fuma.getVelocity().x = -fuma.getSpeed();
		}
		if (fuma.getDir().equalsIgnoreCase("RIGHT")) {
			fuma.getVelocity().x = fuma.getSpeed();
		}
		if (fuma.getDir().equalsIgnoreCase("UP")) {
			fuma.getVelocity().y = fuma.getSpeed();
		}
		if (fuma.getDir().equalsIgnoreCase("DOWN")) {
			fuma.getVelocity().y = -fuma.getSpeed();
		}

		if (fuma.getDir().equalsIgnoreCase("DIE")) {
			fuma.getVelocity().x = 0;
			fuma.getVelocity().y = 0;
		}

	}

	public void pause() {
		pause = true;
	}

	public void resume() {
		pause = false;
	}
}
