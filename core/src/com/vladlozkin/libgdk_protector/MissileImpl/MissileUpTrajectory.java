package com.vladlozkin.libgdk_protector.MissileImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.vladlozkin.libgdk_protector.Enemy;
import com.vladlozkin.libgdk_protector.IEnemyUpdate;

import java.util.Random;

public class MissileUpTrajectory extends Enemy implements IEnemyUpdate
{
    float rotationDegree = 10;
    private float weight = 10;
    protected TextureRegion textureRegion;
    float velocityX = 10f;
    float velocityY = 16f;
    Sound sound = Gdx.audio.newSound(Gdx.files.internal("grad.wav"));



    public MissileUpTrajectory() {
        super( 0, 0, "missile.png");
        textureRegion = new TextureRegion(texture);
    }

    @Override
    public void Move() {
        float delta = rand.nextFloat() * 0.2f + 0.2f;
        super.getBound().x += velocityX * delta*2;
        super.getBound().y += velocityY * delta*2;
        velocityY = velocityY * delta + 10;
        rotationDegree -= 0.04;

        if (!super.getScreenRectangel().contains( super.getBound()))
        {
            super.HideEnemy();
        }
    }

    public boolean Visible(){
        return super.shouldDraw == true;
    }

    public void Hide()
    {
        super.shouldDraw = false;
    }

    public void Show()
    {
        super.shouldDraw = true;
    }

    @Override
    public Rectangle GetBound() {
        return super.getBound();
    }

    @Override
    public void Draw(SpriteBatch spriteBatch)
    {
        spriteBatch.draw(textureRegion, bound.x, bound.y, bound.getWidth()/2, bound.getHeight()/2, texture.getWidth(), texture.getHeight(), 0.5f,0.5f, rotationDegree);
    }

    @Override
    public void SetNewPosition() {
        float newX = rand.nextInt(Gdx.graphics.getWidth());
        float newY = 0;
        rotationDegree = 0;
        velocityX = 10f;
        velocityY = 16f;
        bound.setCenter(newX, newY);
//        sound.play(0.3f);
    }

    @Override
    public boolean TouchedGround() {
        return super.touchedGround;
    }


}


