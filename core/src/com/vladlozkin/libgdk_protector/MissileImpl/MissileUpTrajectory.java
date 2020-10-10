package com.vladlozkin.libgdk_protector.MissileImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.vladlozkin.libgdk_protector.Enemy;

public class MissileUpTrajectory extends Enemy
{
    float rotationDegree = 10;
    private float weight = 10;
    protected TextureRegion textureRegion;
    float velocityX = 70f;
    float velocityY = 100f;
    Sound sound = Gdx.audio.newSound(Gdx.files.internal("grad.wav"));

    public MissileUpTrajectory() {
        super( 0, 0, "missile.png");
        textureRegion = new TextureRegion(texture);
        SetSpeed(20f, 20f);
        SetNewPosition();
    }

    @Override
    public void Move(float delta) {
        System.out.println(CheckIfShouldMove());
        if (CheckIfShouldMove())
        {
            super.GetBound().x += velocityX *(delta * xSpeed);
            super.GetBound().y += velocityY * (delta * ySpeed);
            velocityY = velocityY * delta + 10 ;
            rotationDegree -= 30;
        }
    }

    @Override
    public Rectangle GetBound() {
        return super.GetBound();
    }

    @Override
    public void SetNewPosition() {
        float newX = rand.nextInt(Gdx.graphics.getWidth());
        float newY = 400;
        rotationDegree = 0;
        velocityX = 10f;
        velocityY = 16f;
        bound.setCenter(newX, newY);
//        sound.play(0.3f);
    }
}


