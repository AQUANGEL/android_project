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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.vladlozkin.libgdk_protector.MissileImpl.MissileUpTrajectory;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonLeftToRight;
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
	Rectangle screenRectangel;
//	Rectangle screenRectangel =  new Rectangle(-bound.getWidth() * 1.5f, Gdx.graphics.getHeight()/12,Gdx.graphics.getWidth() + bound.getWidth()* 2.5f,Gdx.graphics.getHeight() + bound.getHeight() * 2.5f);

	IEnemyUpdate enemyInWaitingList;
	IEnemyUpdate enemyInToDrawList;
	IEnemyUpdate enemy;
	private final int MAX_NUM_OF_RIGHT_TO_LEFT_BALLOONS = 3;
	private final int MAX_NUM_OF_LEFT_TO_RIGHT_BALLOONS = 1;
	CopyOnWriteArrayList m_EnemysToDraw = new CopyOnWriteArrayList<IEnemyUpdate>();
	CopyOnWriteArrayList m_EnemysWaitingList = new CopyOnWriteArrayList<IEnemyUpdate>();
	CopyOnWriteArrayList itemsToRemove = new CopyOnWriteArrayList<IEnemyUpdate>();

	Label scoreText;

	IActionResolver actionResolver;

	private int score = 0;
	Label.LabelStyle scoreTextStyle;
	BitmapFont scoreFont;

	public static boolean showLoginScreen = true;
	public static boolean showLeaderBoard = false;

	private int m_CurrentLevel = 0;
	private int m_MsForEnemyDispatch = 1500;
	private int m_MsInWaitingList = 1500;
	private int nofLeftEnemies;


	public Main(IActionResolver actionResolver)
	{
		this.actionResolver = actionResolver;
	}


	@Override
	public void create() {
		if (showLoginScreen)
		{
			actionResolver.ShowMainMenu();
			showLoginScreen = false;
		}
		screenRectangel = new Rectangle(0, Gdx.graphics.getHeight()/12,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		spriteBatch = new SpriteBatch();
		createGameLevel(m_CurrentLevel);
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

		Thread enemyDispatchThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true)
				{
					try {
						Thread.sleep(m_MsForEnemyDispatch);
						if(!m_EnemysWaitingList.isEmpty())
						{
							enemyInWaitingList = (IEnemyUpdate) m_EnemysWaitingList.remove(0);
							m_EnemysToDraw.add(enemyInWaitingList);
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		enemyDispatchThread.start();

		Thread populateEnemyDispatchListThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true)
				{
					try {
						itemsToRemove.clear();
						for (Object iter : m_EnemysToDraw) {
							Thread.sleep(1000);
							enemyInToDrawList = (IEnemyUpdate) iter;
							if (enemyInToDrawList.Visible() == false) {
								enemyInToDrawList.SetNewPosition();
								enemyInToDrawList.Show();
								m_EnemysWaitingList.add(enemyInToDrawList);
								itemsToRemove.add(enemyInToDrawList);
							}
						}
						m_EnemysToDraw.removeAll(itemsToRemove);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		populateEnemyDispatchListThread.start();
	}

	private void createGameLevel(int level) {
		switch (level) {
			case 0:
				createIntro();
				break;
			case 1:
				createFirstLevel();
				break;
			case 2:
				createSecondLevel();
				break;
			case 3:
				new Exception("Level not created yet");
				break;
			default:
				new Exception("Level not created yet");
		}
	}



	private void createIntro() {
		backgroundTexture = new Texture("farmResized.png");
		backgroundSprite = new Sprite(backgroundTexture);

		for(int i = 0; i < MAX_NUM_OF_RIGHT_TO_LEFT_BALLOONS; i++)
		{
			m_EnemysWaitingList.add(new BalloonRightToLeft());
		}

		Collections.shuffle(m_EnemysWaitingList);
		nofLeftEnemies = m_EnemysWaitingList.size();
	}

	private void createFirstLevel()
	{
		backgroundTexture = new Texture("farmResized.png");
		backgroundSprite = new Sprite(backgroundTexture);

		for(int i = 0; i < MAX_NUM_OF_RIGHT_TO_LEFT_BALLOONS; i++)
		{
			m_EnemysWaitingList.add(new BalloonRightToLeft());
		}

		for(int i = 0; i < MAX_NUM_OF_LEFT_TO_RIGHT_BALLOONS; i++)
		{
			m_EnemysWaitingList.add(new BalloonLeftToRight());
		}

		Collections.shuffle(m_EnemysWaitingList);
		nofLeftEnemies = m_EnemysWaitingList.size();
//		for(int i = 0; i < 1; i++)
//		{
//			enemys.add(new MissileDownTrajectory());
//		}

//		for(int i = 0; i < 2; i++)
//		{
//			enemys.add(new MissileUpTrajectory());
//		}
	}

	private void createSecondLevel() {
		backgroundTexture = new Texture("black.png");
		backgroundSprite = new Sprite(backgroundTexture);

		nofLeftEnemies = 1; //this will never stop
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
			scoreText.draw(spriteBatch, (float) 1.0);
			spriteBatch.end();

			renderSwipe();
			handleSwipeOutcome();
			// TODO:: add check of score or time elapsed or life etc.. ,
			// TODO:: set showLeaderBoard to true when needed
			if(nofLeftEnemies <= 0)
			{
				actionResolver.ShowLeaderBoard(score);
				m_CurrentLevel++;
				dispose();

				create();
//				Gdx.app.exit();
//				System.exit(0);
			}
	}

	private void updateScore(int toAdd) {
		this.score += toAdd;
		scoreText.setText("Score: " + this.score);
	}

	private void renderEnemys()
	{
		for(Object enemyIter : m_EnemysToDraw)
		{
			enemy = (IEnemyUpdate) enemyIter;
			if (enemy.Visible())
			{
				if(!enemy.TouchedGround())
				{
					if(enemy.GetBound().y < screenRectangel.y) {
						enemy.ShowImpact();
						enemy.setTouchedGround(true);
						nofLeftEnemies--;
					}
					else
					{
						enemy.Move();
					}
				}
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
		for(Object enemyIter : m_EnemysToDraw)
		{
			enemy = (IEnemyUpdate) enemyIter;
			for(Vector2 point : swipe.path())
			{
				if(enemy.GetBound().contains(point.x,point.y) && !enemy.TouchedGround()){
					m_EnemysToDraw.remove(enemy);
					nofLeftEnemies--;
					updateScore(enemy.score());
					break;
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
		m_EnemysToDraw.clear();
		m_EnemysWaitingList.clear();
	}

}