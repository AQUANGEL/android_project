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

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.vladlozkin.libgdk_protector.MissileImpl.MissileUpTrajectory;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonRightToLeft;


public class Main implements ApplicationListener {
	OrthographicCamera cam;
	SpriteBatch batch;
	SwipeHandler swipe;
	Texture swipeTexture;
	SwipeTriStrip tris;

	public static Texture backgroundTexture;
	public static Sprite backgroundSprite;
	private SpriteBatch spriteBatch;

	Random rand = new Random();
	CopyOnWriteArrayList enemysToDraw = new CopyOnWriteArrayList<IEnemyUpdate>();
	CopyOnWriteArrayList enemysWaitingList = new CopyOnWriteArrayList<IEnemyUpdate>();
	IEnemyUpdate enemyInWaitingList;
	IEnemyUpdate enemyInToDrawList;
	IEnemyUpdate enemy;
	private final int MAX_NUM_OF_RIGHT_TO_LEFT_BALLOONS = 3;
	private final int MAX_NUM_OF_LEFT_TO_RIGHT_BALLOONS = 1;
	IActionResolver actionResolver;

	private int score = 0;
	Label scoreText;
	Label.LabelStyle scoreTextStyle;
	BitmapFont scoreFont;

	public static boolean showLoginScreen = true;
	public static boolean showLeaderBoard = false;

	public Main(IActionResolver actionResolver)
	{
		this.actionResolver = actionResolver;
	}



	@Override
	public void create() {
		loadTextures();
		swiperInit();
		batch = new SpriteBatch();

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//attach event Listener class
		Gdx.input.setInputProcessor(swipe);

		scoreFont = new BitmapFont();
		scoreFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		scoreTextStyle = new Label.LabelStyle();
		scoreTextStyle.font = scoreFont;

		scoreText = new Label("Score: 0",scoreTextStyle);
		scoreText.setBounds(32, 32,200,100);
		scoreText.setFontScale(4f,4f);

		Thread enemyDispatchThread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true)
				{
					try {
						Thread.sleep(3000);
						if(!enemysWaitingList.isEmpty())
						{
							enemyInWaitingList = (IEnemyUpdate) enemysWaitingList.remove(0);
							enemysToDraw.add(enemyInWaitingList);
						}
					}
					catch (Exception e)
					{
					}
				}
			}
		});
		enemyDispatchThread2.start();

		Thread enemyDispatchThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true)
				{
					try {
						LinkedList itemsToRemove = (LinkedList<IEnemyUpdate>) enemysToDraw.clone();
						for (Object iter : enemysToDraw) {
							Thread.sleep(1500);
							enemyInToDrawList = (IEnemyUpdate) iter;
							if (enemyInToDrawList.Visible() == false) {
								enemyInToDrawList.SetNewPosition();
								enemyInToDrawList.Show();
								enemysWaitingList.add(enemyInToDrawList);
								itemsToRemove.add(enemyInToDrawList);
							}
						}
						enemysToDraw.removeAll(itemsToRemove);
					}
					catch (Exception e)
					{
						e.printStackTrace();
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
			enemysWaitingList.add(new BalloonRightToLeft());
		}
//
//		for(int i = 0; i < MAX_NUM_OF_LEFT_TO_RIGHT_BALLOONS; i++)
//		{
//			enemys.add(new BalloonLeftToRight());
//		}

//		for(int i = 0; i < 1; i++)
//		{
//			enemys.add(new MissileDownTrajectory());
//		}

//		for(int i = 0; i < 2; i++)
//		{
//			enemys.add(new MissileUpTrajectory());
//		}
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
		if (showLoginScreen)
		{
			actionResolver.ShowMainMenu();
			showLoginScreen = false;
//			showLeaderBoard = true;  //TODO:: remove from here, just for testing
		}
		else
		{
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

			spriteBatch.begin();
			renderBackground();
			renderEnemys();
			scoreText.draw(spriteBatch, (float) 1.0);
			spriteBatch.end();

			renderSwipe();
			handleSwipeOutcome();
			// TODO:: add check of score or time elapsed or life etc.. ,
			// TODO:: set showLeaderBoard to true when needed
			if(showLeaderBoard)
			{
				actionResolver.ShowLeaderBoard();
				showLeaderBoard  = false;
			}
		}

	}

	private void updateScore(int toAdd) {
		this.score += toAdd;

		scoreText.setText("Score: " + this.score);
	}

	private void renderEnemys()
	{
		for(Object enemyIter : enemysToDraw)
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
			for(Object enemyIter : enemysToDraw)
			{
				enemy = (IEnemyUpdate) enemyIter;
				if(enemy.GetBound().contains(point.x,point.y)){
					if (enemy.isShouldDraw()) {
						updateScore(enemy.score());
						enemy.Hide();
						System.out.println("ENEMY GOT DESTRUCTED FROM SWIPE");
					}
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