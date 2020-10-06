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
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonExtinguisher;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonStationary;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonLeftToRight;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonRightToLeft;


public class Main implements ApplicationListener {
	OrthographicCamera cam;
	SpriteBatch batch;
	SwipeHandler swipe;
	Texture swipeTexture;
	SwipeTriStrip tris;
	SpriteBatch spriteBatch;
	IActionResolver actionResolver;

	public static boolean showLoginScreen = true;

	OtefProtectorGame game;

	public Main(IActionResolver actionResolver)
	{
		this.actionResolver = actionResolver;
	}


	@Override
	public void create() {
		spriteBatch = new SpriteBatch();
		game = new OtefProtectorGame(actionResolver, spriteBatch);


		if (showLoginScreen)
		{
			actionResolver.ShowMainMenu();
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//
//				}
//			}).start();;
			showLoginScreen = false;
		}
		game.InitGame();

		swiperInit();
		batch = new SpriteBatch();

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//attach event Listener class
		Gdx.input.setInputProcessor(swipe);
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		spriteBatch.begin();
		game.RenderGame();
		spriteBatch.end();

		renderSwipe();
		game.HandleSwipeOutcome(swipe.path());

		if(game.LevelFinished())
		{
			game.HandleLevelFinished();
//			dispose();
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
		spriteBatch.dispose();
	}

}