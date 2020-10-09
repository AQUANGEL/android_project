package com.vladlozkin.libgdk_protector.BalloonImpl;

import com.badlogic.gdx.Gdx;
import com.vladlozkin.libgdk_protector.Enemy;

import java.util.Random;

public class BalloonLeftToRight extends Enemy {

    private final float FIRST_LEVEL_MAX_DELTA = 0.1f;
    private float weight = 10;
    Random rand = new Random();

    public BalloonLeftToRight()
    {
        super( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), "ballon.png");
        SetNewPosition();
    }

    public void Move(float delta) {
        if (CheckIfShouldMove())
        {
//            float delta = rand.nextFloat() * delta + 0.2f;
            super.GetBound().x -= weight*(delta*2  + 0.2f);
            super.GetBound().y -= weight*(delta/2 + 0.1f);
        }
    }

    @Override
    public void SetNewPosition() {
        float newX =  rand.nextInt(Gdx.graphics.getWidth());
        float newY = Gdx.graphics.getHeight() - bound.y + rand.nextInt(Gdx.graphics.getHeight()/10);
        bound.setCenter(newX, newY);
    }

    public void SetPositionOnTheGround(float groundY)
    {
        float newX = rand.nextInt(Gdx.graphics.getWidth());
        bound.setCenter(newX, groundY);
    }

}
