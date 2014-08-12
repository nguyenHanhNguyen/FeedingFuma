package com.me.feedingfuma.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Fuma {

	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private float ppuX = 1280 / CAMERA_WIDTH; // pixels per unit on the X axis
	private float ppuY = 720 / CAMERA_HEIGHT; // pixels per unit on the Y axis

	public enum State {
		IDLE, SWIMMING, DIE;
	}

	//public static float SIZE = 1f;
	public static float SPEED = 2.5f;

	Vector2 position = new Vector2();
	Vector2 acceleration = new Vector2();
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	public State state = State.IDLE;
	String dir = new String();
	boolean facingRight = true;
	boolean eatFish = false;
	float size = 1f;
	
	public Fuma(Vector2 position) {
		this.position = position;
		this.bounds.height = size;
		this.bounds.width = size;
	}

    public void setSize(float size) {
    	this.size = size;
    }
    
    public float getSize() {
    	return size;
    }
	
	public boolean getEatFish() {
		return eatFish;
	}
	
	public void setEatFish(boolean eatFish) {
		this.eatFish = eatFish;
	}
	
	public String getDir() {
		return dir;
	}
	
	public void setDir(String dir) {
		this.dir = dir;
	}
	
	public boolean isFacingRight() {
		return facingRight;
	}

	public void setFacingRight(boolean facingRight) {
		this.facingRight = facingRight;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(int x, int y) {
		position.x = x;
		position.y = y;
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public Rectangle getBounds() {
		return bounds;
	}
	
	public void setBounds(float width, float height) {
		bounds.setWidth(width);
		bounds.setHeight(height);

	}

	public State getState() {
		return state;
	}

	public void setState(State newState) {
		this.state = newState;
	}

	public void update(float delta) {
		if (position.x * ppuX >= WIDTH && position.y * ppuY < HEIGHT) {
			position.x = 0;
		}
		if (position.x * ppuX < 0 && position.y * ppuY < HEIGHT) {
			position.x = WIDTH / ppuX;
		}
		if (position.y * ppuY >= HEIGHT && position.x * ppuX < WIDTH) {
			position.y = 0;
		}
		if (position.y * ppuY < 0 && position.x * ppuX < WIDTH) {
			position.y = HEIGHT / ppuY;
		}
		if (position.y * ppuY > HEIGHT && position.x * ppuX > WIDTH) {
			position.y = 0;
			position.x = 0;
		}
		if (position.y * ppuY < 0 && position.x * ppuX < 0) {
			position.x = WIDTH / ppuX;
			position.y = HEIGHT / ppuY;
		}

		// Gdx.app.log("position",String.valueOf(position.x));
		position.add(velocity.cpy().mul(delta));

	}
}
