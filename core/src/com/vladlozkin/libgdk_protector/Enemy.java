package com.vladlozkin.libgdk_protector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Enemy {
    protected Random rand = new Random(100);
    protected Rectangle bound;
    protected Texture texture;
    protected TextureRegion textureRegion;

    protected boolean shouldDraw = true;
    protected Rectangle screenRectangel;

    public Enemy(float x, float y, String internalPathToEnemyImage){
        texture = new Texture(Gdx.files.internal(internalPathToEnemyImage));
        textureRegion = new TextureRegion(texture);
        bound = new Rectangle(x, y - texture.getHeight(), texture.getWidth()/1.5f, texture.getHeight()/1.5f);
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

    public int score() { return 10; }

    public boolean isShouldDraw() { return this.shouldDraw; }

    public void draw(SpriteBatch spriteBatch){
        spriteBatch.draw(textureRegion, bound.x, bound.y, bound.getWidth()/2, bound.getHeight()/2, texture.getWidth(), texture.getHeight(), 0.5f,0.5f, 0);
//        spriteBatch.draw(texture, bound.x, bound.y, texture.getWidth()/1.5f, texture.getHeight()/1.5f);
    }
}
