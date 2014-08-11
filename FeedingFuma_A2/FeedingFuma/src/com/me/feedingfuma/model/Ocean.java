package com.me.feedingfuma.model;

import java.util.ArrayList;
import com.badlogic.gdx.math.Vector2;

public class Ocean {
	Fuma fuma;
	ArrayList<OtherFish> fishes;
	Level1 level;

	public Fuma getFuma() {
		return fuma;
	}
	
	public Level1 getLevel() {
		return level;
	}

	public ArrayList<OtherFish> getFish() {
		return level.getFish();
	}

	public void setFish(ArrayList<OtherFish> fishes) {
		this.fishes = fishes;
	}
	
	public Ocean() {
		createEnvironment();
	}

	private void createEnvironment() {
		fuma = new Fuma(new Vector2(7, 2));
		level = new Level1();

	}
}
