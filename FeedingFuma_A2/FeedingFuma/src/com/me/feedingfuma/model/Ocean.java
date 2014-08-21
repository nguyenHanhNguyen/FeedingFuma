package com.me.feedingfuma.model;

import java.util.ArrayList;

public class Ocean {
	ArrayList<Fuma> fuma;
	ArrayList<OtherFish> fishes;
	Level1 level;

	public Fuma getFuma(int order) {
		return level.getFuma(order);
	}
	
	public void setFuma(ArrayList<Fuma> fuma) {
		this.fuma = fuma;
	}
	
	public Level1 getLevel() {
		return level;
	}

	public void setLevel(Level1 level) {
		this.level = level;
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
		level = new Level1();
	}
}
