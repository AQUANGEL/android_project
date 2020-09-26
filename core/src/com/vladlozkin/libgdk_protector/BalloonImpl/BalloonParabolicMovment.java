package com.vladlozkin.libgdk_protector.BalloonImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.vladlozkin.libgdk_protector.IEnemyUpdate;

import java.util.Random;

public class BalloonParabolicMovment extends Balloon implements IEnemyUpdate {

    private final float FIRST_LEVEL_MAX_DELTA = 0.1f;
    private float weight = 10;
    float velocityX = 0.5f, velocityY = 0.5f;
    Random rand;

    public BalloonParabolicMovment()
    {
        super(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 1.5f);
        rand = new Random(100);
    }

    @Override
    public void Move() {
        float delta = rand.nextFloat() * FIRST_LEVEL_MAX_DELTA + 0.2f;
        velocityY += weight * delta;        // Apply gravity to vertical velocity
        super.bound.setX(super.bound.x += velocityX * delta);      // Apply horizontal velocity to X position
        super.bound.setY(super.bound.y += velocityY * delta);     // Apply vertical velocity to X position

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
    public void Draw(SpriteBatch spriteBatch) {
        super.draw(spriteBatch);
    }
}
