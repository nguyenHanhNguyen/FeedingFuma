package com.me.feedingfuma.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class OtherFish {
	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private float ppuX = WIDTH / CAMERA_WIDTH; // pixels per unit on the X axis
	private float ppuY = HEIGHT / CAMERA_HEIGHT; // pixels per unit on the Y axis
													

	public enum State {
		DIE, SWIMMING;
	}

	public enum Genre {
		YellowFish, BlueFish, LaHan, NormalFish, BigFish, Pirahna;
	}

	Genre genre = Genre.YellowFish;

	Vector2 position = new Vector2();
	Vector2 acceleration = new Vector2();
	Vector2 vel = new Vector2();
	Rectangle bounds = new Rectangle();
	String dir = new String();
	boolean beEaten = false;
	boolean eatFuma = false;
	float size;
	float speed;

	public OtherFish(Vector2 position) {
		this.position = position;
		//this.bounds.setX(position.x);
		//this.bounds.setY(position.y);
	}

	public boolean BeEaten() {
		return beEaten;
	}

	public void setBeEaten(boolean beEaten) {
		this.beEaten = beEaten;
	}

	public boolean isEatFuma() {
		return eatFuma;
	}

	public void setEatFuma(boolean eatFuma) {
		this.eatFuma = eatFuma;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
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

	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	public Vector2 getVel() {
		return vel;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public void setVel(Vector2 vel) {
		this.vel = vel;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(float width, float height) {
		bounds.setWidth(width);
		bounds.setHeight(height);

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
		position.add(vel.cpy().mul(delta));
	}

}
