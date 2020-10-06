package com.vladlozkin.libgdk_protector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Enemy implements IEnemy {
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
        bound = new Rectangle(rand.nextInt((int)x+1), Math.max(y - texture.getHeight(),0), texture.getWidth()/1.5f, texture.getHeight()/1.5f);
    }

    public Rectangle getScreenRectangel()
    {
        return screenRectangel;
    }

    public boolean Visible(){
        return shouldDraw == true;
    }

    @Override
    public void Hide() {
        shouldDraw = false;
    }

    @Override
    public void Show() {
        shouldDraw = true;
    }

    @Override
    public void Move(float delta) throws Exception {
        throw new Exception("You must overide Move method");
    }

    @Override
    public void Draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(textureRegion, bound.x, bound.y, bound.getWidth()/2, bound.getHeight()/2, texture.getWidth(), texture.getHeight(), 0.5f,0.5f, 0);
    }

    @Override
    public void SetNewPosition() throws Exception {
        throw new Exception("You must overide setNewPosition method");
    }

    public void setTouchedGround(boolean touchedGround)
    {
        this.touchedGround = touchedGround;
    }

    @Override
    public Rectangle GetBound() {
        return bound;
    }

    public boolean CheckIfShouldMove()
    {
        return !touchedGround;
    }

    public void ShowImpact()
    {
        textureRegion.setRegion(textureOnImpactWithGround);
    }

    @Override
    public boolean TouchedGround() {
        return touchedGround;
    }

    public int Score() { return 10; }

    public boolean ShouldDraw() { return this.shouldDraw; }

}
