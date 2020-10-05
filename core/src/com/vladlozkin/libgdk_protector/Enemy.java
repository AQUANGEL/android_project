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
    private Texture textureOnImpactWithGround;


    protected boolean shouldDraw = true;
    protected Rectangle screenRectangel;
    public boolean touchedGround = false;

    public Enemy(float x, float y, String internalPathToEnemyImage){
        texture = new Texture(Gdx.files.internal(internalPathToEnemyImage));
        textureRegion = new TextureRegion(texture);
        textureOnImpactWithGround = new Texture(Gdx.files.internal("fire1.png"));
        bound = new Rectangle(rand.nextInt((int)x), y - texture.getHeight(), texture.getWidth()/1.5f, texture.getHeight()/1.5f);
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

    public void setTouchedGround(boolean touchedGround)
    {
        this.touchedGround = touchedGround;
    }

    public boolean CheckIfShouldMove()
    {
        return !touchedGround;
    }

    public void ShowImpact()
    {
        textureRegion.setRegion(textureOnImpactWithGround);
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
