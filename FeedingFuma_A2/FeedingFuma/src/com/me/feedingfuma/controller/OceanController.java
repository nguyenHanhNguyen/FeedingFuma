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
import com.me.feedingfuma.model.Ocean;
import com.me.feedingfuma.model.OtherFish;

public class OceanController {
	Random rand = new Random();

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

	public OceanController(Ocean ocean) {
		fishes = new ArrayList<OtherFish>();
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
		detectCollision(delta);
		updateFishRandom();
		for(int i = 0 ; i < fishes.size() ; i++) {
			fishes.get(i).update(delta);
		}
		updateFuma();
		fuma.update(delta);
		
	}

	private void detectCollision(float delta) {
		fuma.getVelocity().mul(delta);
		
		Rectangle fumaRect = rectPool.obtain();
		fumaRect.set(fuma.getBounds().x, fuma.getBounds().y,
				fuma.getBounds().width, fuma.getBounds().height);

		fumaRect.x += fuma.getVelocity().x;
		//fumaRect.y += fuma.getVelocity().y;
		for (int i = 0 ; i < fishes.size() ; i ++) {
			if (fumaRect.overlaps(fishes.get(i).getBounds())) {
				fuma.setEatFish(true);
				//fishes.get(i).setBeEaten(true);
				fishes.remove(fishes.get(i));
				ocean.setFish(fishes);
				Gdx.app.log("Collide x", "fuma collide");
			}
		}
		fumaRect.x = fuma.getPosition().x;
		//fumaRect.y = fuma.getPosition().y;

		fuma.getPosition().add(fuma.getVelocity());
		fuma.getBounds().x = fuma.getPosition().x;
		fuma.getBounds().y = fuma.getPosition().y;
		fuma.getVelocity().mul(delta);
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
/*			// up
			if (rand.nextInt(4) + 1 == 1) {
				fishes.get(i).setDir("UP");

			}
			// down
			if (rand.nextInt(4) + 1 == 2) {
				fishes.get(i).setDir("DOWN");

			}
			// right
			if (rand.nextInt(4) + 1 == 3) {
				fishes.get(i).setDir("RIGHT");

			}
			// left
			if (rand.nextInt(4) + 1 == 4) {
				fishes.get(i).setDir("LEFT");

			}
*/		}
	}

	private void updateFishRandom() {
		//float delta = Gdx.graphics.getDeltaTime();
		//check if the fish be eaten or not
		/*ArrayList<OtherFish> liveFish = new ArrayList<OtherFish>();
		for(int i = 0 ; i < fishes.size() ; i++) {
			if(fishes.get(i).BeEaten() == false) {
				liveFish.add(fishes.get(i));
			}
		}*/
		
		randomDir();
		Gdx.app.log("number of fish", String.valueOf(fishes.size()));
		for (int i = 0; i < fishes.size(); i++) {
			if (fishes.get(i).getDir().equalsIgnoreCase("UP")) {
				fishes.get(i).getVel().y = fishes.get(i).getSpeed();
			}
			if (fishes.get(i).getDir().equalsIgnoreCase("DOWN")) {
				fishes.get(i).getVel().y = -fishes.get(i).getSpeed();
			}
			if (fishes.get(i).getDir().equalsIgnoreCase("RIGHT")) {
				fishes.get(i).getVel().x = fishes.get(i).getSpeed();
			}
			if (fishes.get(i).getDir().equalsIgnoreCase("LEFT")) {
				fishes.get(i).getVel().x = -fishes.get(i).getSpeed();
			}
			//fishes.get(i).update(delta);
		}
	}
}
