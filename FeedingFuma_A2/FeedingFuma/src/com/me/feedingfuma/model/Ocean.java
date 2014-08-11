package com.me.feedingfuma.model;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.math.Vector2;
import com.me.feedingfuma.model.OtherFish.Genre;

public class Ocean {
	Fuma fuma;
	ArrayList<OtherFish> fishes;
	Random ran;

	public Fuma getFuma() {
		return fuma;
	}

	public ArrayList<OtherFish> getFish() {
		return fishes;
	}

	public void setFish(ArrayList<OtherFish> fishes) {
		this.fishes = fishes;
	}
	public Ocean() {
		createEnvironment();
	}

	private void createEnvironment() {
		fuma = new Fuma(new Vector2(7, 2));

		ran = new Random();
		fishes = new ArrayList<OtherFish>();

		for (int i = 0; i < 20; i++) {
			fishes.add(new OtherFish(new Vector2((ran.nextInt(10 - 5)), (ran
					.nextInt(10 - 2)))));
			fishes.get(i).setGenre(Genre.BlueFish);
		}

		/*for (int i = 0; i < fishes.size(); i++) {
			if (i == 0) {
				fishes.get(i).setGenre(Genre.YellowFish);
			}
			if (i == 1) {
				fishes.get(i).setGenre(Genre.BlueFish);
			}
			if (i == 2) {
				fishes.get(i).setGenre(Genre.NormalFish);
			}
			if (i == 3) {
				fishes.get(i).setGenre(Genre.LaHan);
			}
			if (i == 4) {
				fishes.get(i).setGenre(Genre.BigFish);
			}
			if (i == 5) {
				fishes.get(i).setGenre(Genre.Pirahna);
			}
		}*/

		for (int i = 0; i < fishes.size(); i++) {
			if (fishes.get(i).getGenre() == Genre.YellowFish) {
				fishes.get(i).setSize(0.5f);
				fishes.get(i).setSpeed(2f);
			}
			if (fishes.get(i).getGenre() == Genre.LaHan) {
				fishes.get(i).setSize(0.7f);
				fishes.get(i).setSpeed(3f);
			}
			if (fishes.get(i).getGenre() == Genre.BlueFish) {
				fishes.get(i).setSize(0.5f);
				fishes.get(i).setSpeed(1.0f);
			}
			if (fishes.get(i).getGenre() == Genre.NormalFish) {
				fishes.get(i).setSize(0.7f);
				fishes.get(i).setSpeed(2f);
			}
			if (fishes.get(i).getGenre() == Genre.BigFish) {
				fishes.get(i).setSize(2.0f);
				fishes.get(i).setSpeed(2f);
			}
			if (fishes.get(i).getGenre() == Genre.Pirahna) {
				fishes.get(i).setSize(1f);
				fishes.get(i).setSpeed(4f);
			}
		}

	}
}
