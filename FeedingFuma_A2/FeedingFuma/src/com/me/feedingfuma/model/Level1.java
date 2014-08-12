package com.me.feedingfuma.model;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.me.feedingfuma.model.OtherFish.Genre;

public class Level1 {

	ArrayList<OtherFish> fishes;
	Random ran;
	int score;
	int live;
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
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
	
	private void loadLevel1() {
		ran = new Random();
		fishes = new ArrayList<OtherFish>();

		//small fishes up and down
		for (int i = 0; i < 10; i++) {
			fishes.add(new OtherFish(new Vector2((ran.nextInt(10 - 5)), (ran
					.nextInt(10 - 2)))));
			fishes.get(i).setGenre(Genre.BlueFish);
		}

		for(int i = 0 ; i < 5 ; i++) {
			fishes.add(new OtherFish(new Vector2((ran.nextInt(4)), (ran
					.nextInt(3)))));
			fishes.get(i).setGenre(Genre.NormalFish);
		}

		for(int i = 0 ; i < 3 ; i++) {
			fishes.add(new OtherFish(new Vector2((ran.nextInt(10 - 5)), (ran
					.nextInt(10 - 2)))));

			fishes.get(i).setGenre(Genre.BigFish);
		}
		
		for (int i = 0; i < fishes.size(); i++) {
			/*if (fishes.get(i).getGenre() == Genre.YellowFish) {
				fishes.get(i).setSize(0.5f);
				fishes.get(i).setSpeed(2f);
				fishes.get(i).getBounds().width = fishes.get(i).getSize();
				fishes.get(i).getBounds().height = fishes.get(i).getSize();
			}*/
			if (fishes.get(i).getGenre() == Genre.LaHan) {
				fishes.get(i).setSize(0.6f);
				fishes.get(i).setSpeed(3f);
				fishes.get(i).getBounds().width = fishes.get(i).getSize();
				fishes.get(i).getBounds().height = fishes.get(i).getSize();
			}
			if (fishes.get(i).getGenre() == Genre.BlueFish) {
				fishes.get(i).setSize(0.3f);
				fishes.get(i).setSpeed(1.0f);
				fishes.get(i).getBounds().width = fishes.get(i).getSize();
				fishes.get(i).getBounds().height = fishes.get(i).getSize();
			}
			if (fishes.get(i).getGenre() == Genre.NormalFish) {
				fishes.get(i).setSize(0.7f);
				fishes.get(i).setSpeed(2f);
				fishes.get(i).getBounds().width = fishes.get(i).getSize();
				fishes.get(i).getBounds().height = fishes.get(i).getSize();
			}
			if (fishes.get(i).getGenre() == Genre.BigFish) {
				fishes.get(i).setSize(2.0f);
				fishes.get(i).setSpeed(2f);
				fishes.get(i).getBounds().width = fishes.get(i).getSize();
				fishes.get(i).getBounds().height = fishes.get(i).getSize();
			}
			if (fishes.get(i).getGenre() == Genre.Pirahna) {
				fishes.get(i).setSize(1f);
				fishes.get(i).setSpeed(4f);
				fishes.get(i).getBounds().width = fishes.get(i).getSize();
				fishes.get(i).getBounds().height = fishes.get(i).getSize();
			}
		}
	}
}
