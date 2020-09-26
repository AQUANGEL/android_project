package com.vladlozkin.libgdk_protector;

import swipe.SwipeHandler;
import swipe.mesh.SwipeTriStrip;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;
import java.util.Vector;

import com.vladlozkin.libgdk_protector.BalloonImpl.*;


public class SwiperImproved implements ApplicationListener {
	OrthographicCamera cam;
	SpriteBatch batch;
	SwipeHandler swipe;
	Texture swipeTexture;
	SwipeTriStrip tris;

	public static Texture backgroundTexture;
	public static Sprite backgroundSprite;
	private SpriteBatch spriteBatch;

	Random rand = new Random();
	Vector balloons = new Vector<IEnemyUpdate>();
	IEnemyUpdate enemy;
	private final int MAX_NUM_OF_RIGHT_TO_LEFT_BALLOONS = 1;
	private final int MAX_NUM_OF_LEFT_TO_RIGHT_BALLOONS = 1;
	BitmapFont scoreText;



	@Override
	public void create() {
		loadTextures();
		swiperInit();
		batch = new SpriteBatch();

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//attach event Listener class
		Gdx.input.setInputProcessor(swipe);

		scoreText = new BitmapFont(); //or use alex answer to use custom font
		scoreText.setColor(255.0f, 255.0f, 255.0f, 255.0f);
		scoreText.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		scoreText.getData().setScale(8);

		Thread enemyDispatchThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true)
				{
					try {
						for (Object balloonIter : balloons) {
							Thread.sleep(700);
							enemy = (IEnemyUpdate) balloonIter;
							if (enemy.Visible() == false) {
								int newX = rand.nextInt(Gdx.graphics.getWidth());
								int newY = rand.nextInt(Gdx.graphics.getHeight());
								enemy.SetNewPosition(newX, newY);
								enemy.Show();
							}
						}
					}
					catch (Exception e)
					{
					}
				}
			}
		});
		enemyDispatchThread.start();
	}

	private void loadTextures() {
		spriteBatch = new SpriteBatch();
		backgroundTexture = new Texture("farmResized.png");
		backgroundSprite = new Sprite(backgroundTexture);
		for(int i = 0; i < MAX_NUM_OF_RIGHT_TO_LEFT_BALLOONS; i++)
		{
			balloons.add(new BalloonRightToLeft());
		}

		for(int i = 0; i < MAX_NUM_OF_LEFT_TO_RIGHT_BALLOONS; i++)
		{
			balloons.add(new BalloonLeftToRight());
		}
	}

	private void swiperInit()
	{
		//the triangle strip renderer
		tris = new SwipeTriStrip();
		//a swipe handler with max # of input points to be kept alive
		swipe = new SwipeHandler(10);
		//minimum distance between two points
		swipe.minDistance = 10;
		//minimum distance between first and second point
		swipe.initialDistance = 10;

		//we will use a texture for the smooth edge, and also for stroke effects
		swipeTexture = new Texture("gradient.png");
		swipeTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		//the thickness of the line
		tris.thickness = 25f;

		//the vertex color for tinting, i.e. for opacity
		tris.color = Color.WHITE;

	}

	public void renderBackground() {
		backgroundSprite.draw(spriteBatch);
	}


	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		spriteBatch.begin();
		renderBackground();
		renderEnemys();
		scoreText.draw(spriteBatch, "Score: 25 ", Gdx.graphics.getWidth() - 620,  Gdx.graphics.getHeight() - 200, 500, 0, true);
		spriteBatch.end();

		renderSwipe();
		handleSwipeOutcome();
	}


	private void renderEnemys()
	{
		for(Object enemyIter : balloons)
		{
			enemy = (IEnemyUpdate) enemyIter;
			if (enemy.Visible())
			{
				enemy.Move();
				enemy.Draw(spriteBatch);
			}
		}
	}

	private void renderSwipe()
	{
		cam.update();
		batch.setProjectionMatrix(cam.combined);

		swipeTexture.bind();

		//generate the triangle strip from our path
		tris.update(swipe.path());

		//render the triangles to the screen
		tris.draw(cam);
	}

	private void handleSwipeOutcome()
	{
		for(Vector2 point : swipe.path())
		{
			for(Object enemyIter : balloons)
			{
				enemy = (IEnemyUpdate) enemyIter;
				if(enemy.GetBound().contains(point.x,point.y)){
					enemy.Hide();
				}
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}


	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		swipeTexture.dispose();
		backgroundTexture.dispose();
		spriteBatch.dispose();
	}

}