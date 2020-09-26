package com.vladlozkin.libgdk_protector.BalloonImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public abstract class Balloon {
    protected Random rand = new Random(100);
    protected Rectangle bound;
    protected Texture texture;

    protected boolean shouldDraw = true;
    protected Rectangle screenRectangel;


    public Balloon(float x, float y){
        texture = new Texture(Gdx.files.internal("ballon.png"));
        bound = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        screenRectangel =  new Rectangle(-bound.getWidth() * 1.5f, -bound.getHeight()* 1.5f,Gdx.graphics.getWidth() + bound.getWidth()* 2.5f,Gdx.graphics.getHeight() + bound.getHeight() * 2.5f);
    }

    public Rectangle getBound(){
        return bound;
    }

    public Rectangle getScreenRectangel()
    {
        return screenRectangel;
    }

    public boolean Visible(){
        return shouldDraw == true;
    }

    public void HideEnemy()
    {
        shouldDraw = false;
    }

    public void ShowEnemy()
    {
        shouldDraw = true;
    }

    public Texture getTexture(){
        return texture;
    }

    public void draw(SpriteBatch spriteBatch){
        spriteBatch.draw(texture, bound.x, bound.y);
    }

    public void SetNewPosition(int x, int y) {
        bound.setCenter(x, y);
    }
}
