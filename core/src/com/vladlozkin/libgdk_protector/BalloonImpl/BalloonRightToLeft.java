package com.vladlozkin.libgdk_protector.BalloonImpl;

import com.badlogic.gdx.Gdx;
import com.vladlozkin.libgdk_protector.Enemy;

import java.util.Random;

public class BalloonRightToLeft extends Enemy {

    private float weight = 10;
    Random rand = new Random();

    public BalloonRightToLeft() {
        super( Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight(), "ballon.png");
        SetNewPosition();
    }

    public void Move(float delta)
    {
        if (CheckIfShouldMove())
        {
//            float delta = rand.nextFloat() * FIRST_LEVEL_MAX_DELTA + 0.2f;
            super.GetBound().x += weight*(delta*2 + 0.3f);
            super.GetBound().y -= weight*(delta/2 + 0.1f);
        }
    }

    @Override
    public void SetNewPosition() {
        float newX =  0 + rand.nextInt(Gdx.graphics.getWidth()/4);
        float newY = Gdx.graphics.getHeight() - bound.y + rand.nextInt(Gdx.graphics.getHeight()/10);
        bound.setCenter(newX, newY);
    }

    public void SetPositionOnTheGround(float groundY)
    {
        float newX = rand.nextInt(Gdx.graphics.getWidth());
        bound.setCenter(newX, groundY);
    }
}
