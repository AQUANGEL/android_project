package com.vladlozkin.libgdk_protector.BalloonImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.vladlozkin.libgdk_protector.IEnemyUpdate;

import java.util.Random;

public class BalloonLeftToRight extends Balloon implements IEnemyUpdate {

    private final float FIRST_LEVEL_MAX_DELTA = 0.1f;
    private float weight = 10;
    Random rand;

    public BalloonLeftToRight()
    {
        super( Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        rand = new Random(100);
    }

    @Override
    public void Move() {
        float delta = rand.nextFloat() * FIRST_LEVEL_MAX_DELTA + 0.2f;
        super.getBound().x -= weight*(delta*2);
        super.getBound().y -= weight*(delta/2);

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
