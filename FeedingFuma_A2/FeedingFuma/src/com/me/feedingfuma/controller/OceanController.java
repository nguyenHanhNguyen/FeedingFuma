package com.me.feedingfuma.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.me.feedingfuma.model.Fuma;
import com.me.feedingfuma.model.Fuma.State;
import com.me.feedingfuma.model.Level1;
import com.me.feedingfuma.model.Ocean;
import com.me.feedingfuma.model.OtherFish;

public class OceanController {
	Random rand = new Random();
	int score = 0;
	Level1 level;
	enum Keys {
		LEFT, RIGHT, UP, DOWN;
	}

	private Ocean ocean;
	private Fuma fuma;
	private ArrayList<OtherFish> fishes;
	// use to detect collision
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {

		@Override
		protected Rectangle newObject() {
			// TODO Auto-generated method stub
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
		this.fuma = ocean.getFuma();
		this.fishes = ocean.getFish();
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
		updateFishRandom();
		if(fuma.state != State.SWIMMING) {
			updateFuma();
			fuma.update(delta);
			detectCollision(delta);
		}
		
	}

	private void detectCollision(float delta) {

		fuma.getVelocity().mul(delta);
		
		Rectangle fumaRect = rectPool.obtain();
		fumaRect.set(fuma.getPosition().x, fuma.getPosition().y,
				fuma.getBounds().width, fuma.getBounds().height);

		//fumaRect.x += fuma.getVelocity().x;
		//fumaRect.y += fuma.getVelocity().y;
		for (int i = 0 ; i < fishes.size() ; i ++) {
			Rectangle fishRect = new Rectangle();
			fishRect.set(fishes.get(i).getPosition().x, fishes.get(i).getPosition().y,fishes.get(i).getBounds().width, fishes.get(i).getBounds().height);
			if (fumaRect.overlaps(fishRect)) {
				if(fumaRect.width > fishRect.width) {
				fuma.setEatFish(true);
				score += 20;
				ocean.getLevel().setScore(score);
				fishes.remove(fishes.get(i));
				ocean.setFish(fishes);
				} else {
				fuma.setState(State.DIE);	
				}
			}
		}
		//fumaRect.x = fuma.getPosition().x;
		//fumaRect.y = fuma.getPosition().y;

		//fuma.getPosition().add(fuma.getVelocity());
		//fuma.getBounds().x = fuma.getPosition().x;
		//fuma.getBounds().y = fuma.getPosition().y;
		//fuma.getVelocity().mul(1/delta);
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
			fuma.setState(State.IDLE);
			// acceleration is 0 on the x
			fuma.getAcceleration().x = 0;
			// horizontal speed is 0
			fuma.getVelocity().x = 0;
		}

		if ((keys.get(Keys.UP) && keys.get(Keys.DOWN))
				|| (!keys.get(Keys.UP) && !(keys.get(Keys.DOWN)))) {
			fuma.setState(State.IDLE);
			// acceleration is 0 on the y
			fuma.getAcceleration().y = 0;
			// vertical speed is 0
			fuma.getVelocity().y = 0;
		}
		
	}

	private void updateFuma() {
		processInput();
		//if (fuma.getEatFish() == false) {
			if (fuma.getDir().equalsIgnoreCase("LEFT")) {
				fuma.getVelocity().x = -Fuma.SPEED;
			}
			if (fuma.getDir().equalsIgnoreCase("RIGHT")) {
				fuma.getVelocity().x = Fuma.SPEED;
			}
			if (fuma.getDir().equalsIgnoreCase("UP")) {
				fuma.getVelocity().y = Fuma.SPEED;
			}
			if (fuma.getDir().equalsIgnoreCase("DOWN")) {
				fuma.getVelocity().y = -Fuma.SPEED;
			}
		//}
	
	}

	private void randomDir() {
		
		for (int i = 0; i < fishes.size(); i++) {
			if(i % 2 == 0) {
				fishes.get(i).setDir("LEFT");
			} else {
				fishes.get(i).setDir("RIGHT");
			}
			/*if( i % 3 == 0) {
				fishes.get(i).setDir("UP");
			}else if (i % 3 == 0 && i % 2 == 0) {
				fishes.get(i).setDir("DOWN");
			}*/
		}
	}

	private void updateFishRandom() {
		float delta = Gdx.graphics.getDeltaTime();
		randomDir();
		//Gdx.app.log("number of fish", String.valueOf(fishes.size()));
		for (int i = 0; i < fishes.size(); i++) {
			if (fishes.get(i).getDir().equalsIgnoreCase("UP")) {
				fishes.get(i).getVel().y = fishes.get(i).getSpeed();
				//fishes.get(i).setBounds(fishes.get(i).getPosition().x,fishes.get(i).getPosition().y);
			}
			if (fishes.get(i).getDir().equalsIgnoreCase("DOWN")) {
				fishes.get(i).getVel().y = -fishes.get(i).getSpeed();
				//fishes.get(i).setBounds(fishes.get(i).getPosition().x,fishes.get(i).getPosition().y);
			}
			if (fishes.get(i).getDir().equalsIgnoreCase("RIGHT")) {
				fishes.get(i).getVel().x = fishes.get(i).getSpeed();
				//fishes.get(i).setBounds(fishes.get(i).getPosition().x,fishes.get(i).getPosition().y);
			}
			if (fishes.get(i).getDir().equalsIgnoreCase("LEFT")) {
				fishes.get(i).getVel().x = -fishes.get(i).getSpeed();
				//fishes.get(i).setBounds(fishes.get(i).getPosition().x,fishes.get(i).getPosition().y);
			}
			fishes.get(i).update(delta);
		}
	}
}
