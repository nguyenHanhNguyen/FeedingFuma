package com.me.feedingfuma.view;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.me.feedingfuma.assetManager.SoundsManager;
import com.me.feedingfuma.model.Fuma;
import com.me.feedingfuma.model.Fuma.State;
import com.me.feedingfuma.model.Level1;
import com.me.feedingfuma.model.Ocean;
import com.me.feedingfuma.model.OtherFish;
import com.me.feedingfuma.model.OtherFish.Genre;

public class OceanRenderer extends Game {
	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;

	private Ocean ocean;
	private OrthographicCamera cam;

	private SpriteBatch spriteBatch;
	private Sprite fumaSprite;
	private Sprite leftFumaSprite;
	private Sprite blueFishSprite;
	private Sprite rightBlueFishSprite;
	private Sprite yellowFishSprite;
	private Sprite leftYellowFishSprite;
	private Sprite normalFishSprite;
	private Sprite leftNormalFishSprite;
	private Sprite lahanSprite;
	private Sprite leftLahanSprite;
	private Sprite bigFishSprite;
	private Sprite rightbigFishSprite;
	private Sprite backgroundSprite;
	private Sprite leftArrow;
	private Sprite rightArrow;
	private Sprite upArrow;
	private Sprite downArrow;

	private int width;
	private int height;
	private float ppuX; // pixels per unit on the X axis
	private float ppuY; // pixels per unit on the Y axis
	private AssetManager assetManager;
	private TextureAtlas atlas;
	private TextButton pause;
	private Stage stage;
	private BitmapFont font;
	private TextButtonStyle buttonStyle;
	BitmapFont score = new BitmapFont();
	BitmapFont live = new BitmapFont();
	Level1 level = new Level1();
	ShapeRenderer debugRenderer = new ShapeRenderer();
	Sound bite;
	SoundsManager soundsManager;
	Music bgMusic = Gdx.audio.newMusic(Gdx.files
			.internal("music/video_game.ogg"));

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float) width / CAMERA_WIDTH;
		ppuY = (float) height / CAMERA_HEIGHT;
		Gdx.app.log("width", String.valueOf(this.width));
		Gdx.app.log("height", String.valueOf(this.height));
	}

	public OceanRenderer(final Ocean ocean) {
		this.ocean = ocean;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		spriteBatch = new SpriteBatch();
		soundsManager = new SoundsManager();
		bgMusic.play();
		font = new BitmapFont();
		buttonStyle = new TextButtonStyle();
		buttonStyle.font = font;
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				true);
		pause = new TextButton("Pause", buttonStyle);
		stage.addActor(pause);
		loadTextures();
		loadSound();
	}

	private void loadTextures() {
		// use asset manager for better memory management
		assetManager = new AssetManager();
		assetManager.load("data/FeedingFuma.pack", TextureAtlas.class);
		assetManager.finishLoading();
		atlas = new TextureAtlas();
		atlas = assetManager.get("data/FeedingFuma.pack", TextureAtlas.class);

		// fuma
		fumaSprite = new Sprite(atlas.findRegion("golden_fish"));
		leftFumaSprite = new Sprite(atlas.findRegion("golden_fish_left"));
		// blue fish
		blueFishSprite = new Sprite(atlas.findRegion("blueFish"));
		rightBlueFishSprite = new Sprite(atlas.findRegion("right_blueFish"));
		// yellow fish
		yellowFishSprite = new Sprite(atlas.findRegion("yellowFish"));
		leftYellowFishSprite = new Sprite(atlas.findRegion("left_yellowFish"));
		// normal fish
		normalFishSprite = new Sprite(atlas.findRegion("fish2"));
		leftNormalFishSprite = new Sprite(atlas.findRegion("left_fish2"));
		// lahan
		lahanSprite = new Sprite(atlas.findRegion("lahan"));
		leftLahanSprite = new Sprite(atlas.findRegion("left_lahan"));
		// big fish
		bigFishSprite = new Sprite(atlas.findRegion("big_fish"));
		rightbigFishSprite = new Sprite(atlas.findRegion("right_big_fish"));

		// controller
		leftArrow = new Sprite(atlas.findRegion("left_arrow"));
		rightArrow = new Sprite(atlas.findRegion("right_arrow"));
		upArrow = new Sprite(atlas.findRegion("up_arrow"));
		downArrow = new Sprite(atlas.findRegion("down_arrow"));
	}

	private void loadSound() {
		soundsManager.load("sound/normal_bite.ogg", "bite");
	}

	public void render() {
		spriteBatch.begin();
		drawBackground();
		drawOnScreenController();
		drawScore();
		drawLive();
		drawFuma();
		drawOtherFish();
		spriteBatch.end();
		stage.draw();
		// drawDebug();
	}

	private void drawFuma() {
		if (ocean.getLevel().getLive() == 0) {
			// drawGameOver();
			// dispose();
		} else {
			Fuma fuma = ocean.getFuma(ocean.getLevel().getLive() - 1);
			//Gdx.app.log("number of fuma",String.valueOf(ocean.getLevel().getAllFuma().size()));
			if (fuma.state == State.SWIMMING) {
				if (ocean.getLevel().getScore() < 100) {
					fuma.setSize(0.5f);
					fuma.setBounds(0.5f, 0.5f);
				}
				if (ocean.getLevel().getScore() > 100) {
					fuma.setSize(0.8f);
					fuma.setBounds(0.8f, 0.8f);
				}
				if (ocean.getLevel().getScore() > 200) {
					fuma.setSize(1f);
					fuma.setBounds(1f, 1f);
				}
				if (ocean.getLevel().getScore() > 250) {
					fuma.setSize(1.2f);
					fuma.setBounds(1.2f, 1.2f);
				}

				if (fuma.isFacingRight()) {
					fumaSprite.setSize(fuma.getSize() * ppuX, fuma.getSize()
							* ppuY);
					fumaSprite.setPosition(fuma.getPosition().x * ppuX,
							fuma.getPosition().y * ppuY);
					fumaSprite.draw(spriteBatch);

				}

				if (!fuma.isFacingRight()) {
					leftFumaSprite.setSize(fuma.getSize() * ppuX,
							fuma.getSize() * ppuY);
					leftFumaSprite.setPosition(fuma.getPosition().x * ppuX,
							fuma.getPosition().y * ppuY);
					leftFumaSprite.draw(spriteBatch);

				}
			}
		}

	}

	private void drawOtherFish() {
		ArrayList<OtherFish> fish = ocean.getFish();
		for (int i = 0; i < fish.size(); i++) {
			// yellow fish
			if (fish.get(i).getGenre() == Genre.YellowFish
					&& fish.get(i).getFacingRight() == true) {
				yellowFishSprite.setPosition(
						fish.get(i).getPosition().x * ppuX, fish.get(i)
								.getPosition().y * ppuY);
				yellowFishSprite.setSize(fish.get(i).getSize() * ppuX, fish
						.get(i).getSize() * ppuY);
				yellowFishSprite.draw(spriteBatch);

			}
			if (fish.get(i).getGenre() == Genre.YellowFish
					&& fish.get(i).getFacingRight() == false) {
				leftYellowFishSprite.setPosition(fish.get(i).getPosition().x
						* ppuX, fish.get(i).getPosition().y * ppuY);
				leftYellowFishSprite.setSize(fish.get(i).getSize() * ppuX, fish
						.get(i).getSize() * ppuY);
				leftYellowFishSprite.draw(spriteBatch);

			}
			// blue fish
			if (fish.get(i).getGenre() == Genre.BlueFish
					&& fish.get(i).getFacingRight() == false) {
				blueFishSprite.setSize(fish.get(i).getSize() * ppuX, fish
						.get(i).getSize() * ppuY);
				blueFishSprite.setPosition(fish.get(i).getPosition().x * ppuX,
						fish.get(i).getPosition().y * ppuY);
				blueFishSprite.draw(spriteBatch);

			}
			if (fish.get(i).getGenre() == Genre.BlueFish
					&& fish.get(i).getFacingRight() == true) {
				rightBlueFishSprite.setSize(fish.get(i).getSize() * ppuX, fish
						.get(i).getSize() * ppuY);
				rightBlueFishSprite.setPosition(fish.get(i).getPosition().x
						* ppuX, fish.get(i).getPosition().y * ppuY);
				rightBlueFishSprite.draw(spriteBatch);

			}
			// Lahan
			if (fish.get(i).getGenre() == Genre.LaHan
					&& fish.get(i).getFacingRight() == true) {
				lahanSprite.setPosition(fish.get(i).getPosition().x * ppuX,
						fish.get(i).getPosition().y * ppuY);
				lahanSprite.setSize(fish.get(i).getSize() * ppuX, fish.get(i)
						.getSize() * ppuY);
				lahanSprite.draw(spriteBatch);

			}
			if (fish.get(i).getGenre() == Genre.LaHan
					&& fish.get(i).getFacingRight() == false) {
				leftLahanSprite.setPosition(fish.get(i).getPosition().x * ppuX,
						fish.get(i).getPosition().y * ppuY);
				leftLahanSprite.setSize(fish.get(i).getSize() * ppuX,
						fish.get(i).getSize() * ppuY);
				leftLahanSprite.draw(spriteBatch);

			}
			// Normal fish
			if (fish.get(i).getGenre() == Genre.NormalFish
					&& fish.get(i).getFacingRight() == true) {
				normalFishSprite.setPosition(
						fish.get(i).getPosition().x * ppuX, fish.get(i)
								.getPosition().y * ppuY);
				normalFishSprite.setSize(fish.get(i).getSize() * ppuX, fish
						.get(i).getSize() * ppuY);
				normalFishSprite.draw(spriteBatch);

			}
			if (fish.get(i).getGenre() == Genre.NormalFish
					&& fish.get(i).getFacingRight() == false) {
				leftNormalFishSprite.setPosition(fish.get(i).getPosition().x
						* ppuX, fish.get(i).getPosition().y * ppuY);
				leftNormalFishSprite.setSize(fish.get(i).getSize() * ppuX, fish
						.get(i).getSize() * ppuY);
				leftNormalFishSprite.draw(spriteBatch);

			}
			// Big fish
			if (fish.get(i).getGenre() == Genre.BigFish
					&& fish.get(i).getFacingRight() == true) {
				rightbigFishSprite.setPosition(fish.get(i).getPosition().x
						* ppuX, fish.get(i).getPosition().y * ppuY);
				rightbigFishSprite.setSize(fish.get(i).getSize() * ppuX, fish
						.get(i).getSize() * ppuY);
				rightbigFishSprite.draw(spriteBatch);

			}
			if (fish.get(i).getGenre() == Genre.BigFish
					&& fish.get(i).getFacingRight() == false) {
				bigFishSprite.setPosition(fish.get(i).getPosition().x * ppuX,
						fish.get(i).getPosition().y * ppuY);
				bigFishSprite.setSize(fish.get(i).getSize() * ppuX, fish.get(i)
						.getSize() * ppuY);
				bigFishSprite.draw(spriteBatch);

			}

		}

	}

	private void drawScore() {
		score.draw(spriteBatch, "Score " + ocean.getLevel().getScore(), 50, 700);
	}

	private void drawLive() {
		live.draw(spriteBatch, "Live " + ocean.getLevel().getLive(), 200, 700);
	}

	private void drawPause() {
		
	}
	
	
	private void drawBackground() {
		if (ocean.getLevel().getLevelName().equalsIgnoreCase("level1")) {
			backgroundSprite = new Sprite(atlas.findRegion("level1"));
			backgroundSprite.setSize(Gdx.graphics.getWidth(),
					Gdx.graphics.getHeight());
			backgroundSprite.setPosition(0, 0);
			backgroundSprite.draw(spriteBatch);
		}

	}

	private void drawOnScreenController() {
		leftArrow.setSize(50, 50);
		rightArrow.setSize(50, 50);
		upArrow.setSize(50, 50);
		downArrow.setSize(50, 50);

		
		leftArrow.setPosition(0, 0);
		rightArrow.setPosition(Gdx.graphics.getWidth() - 50, 0);
		upArrow.setPosition(0, Gdx.graphics.getHeight() - 50);
		downArrow.setPosition(Gdx.graphics.getWidth() - 50,
				Gdx.graphics.getHeight() - 50);

		leftArrow.draw(spriteBatch);
		rightArrow.draw(spriteBatch);
		upArrow.draw(spriteBatch);
		downArrow.draw(spriteBatch);
	}

	private void drawDebug() {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Rectangle);

		ArrayList<OtherFish> fishes = ocean.getFish();
		for (int i = 0; i < fishes.size(); i++) {
			Rectangle rect = fishes.get(i).getBounds();
			debugRenderer.setColor(new Color(1, 0, 0, 1));
			float x1 = fishes.get(i).getPosition().x + rect.x;
			float y1 = fishes.get(i).getPosition().y + rect.y;
			debugRenderer.rect(x1, y1, rect.width, rect.height);
		}

		// render Fuma
		if (ocean.getLevel().getLive() > 0) {
			Fuma fuma = ocean.getFuma(ocean.getLevel().getLive() - 1);
			Rectangle rect = fuma.getBounds();
			debugRenderer.setColor(new Color(0, 1, 0, 1));
			float x1 = fuma.getPosition().x + rect.x;
			float y1 = fuma.getPosition().y + rect.y;
			debugRenderer.rect(x1, y1, rect.width, rect.height);
			debugRenderer.end();
		}
	}


	public void dispose() {
		assetManager.dispose();
		score.dispose();
		live.dispose();
		bgMusic.dispose();
		soundsManager.dispose("bite");
	}

	@Override
	public void create() {
		stage.addActor(pause);
		pause.setClickListener(new ClickListener() {
			
			@Override
			public void click(Actor actor, float x, float y) {
				pause.setClickListener(new ClickListener() {
					@Override
					public void click(Actor actor, float x, float y) {
						ocean.getLevel().setPause(true);
						Gdx.app.log("pause","pause");
					}
				});
				
			}
		});
	}
}
