package com.vladlozkin.libgdk_protector.BalloonImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.vladlozkin.libgdk_protector.Enemy;
import com.vladlozkin.libgdk_protector.IEnemyUpdate;

import java.util.Random;

public class BalloonRightToLeft extends Enemy implements IEnemyUpdate {

    private final float FIRST_LEVEL_MAX_DELTA = 0.1f;
    private float weight = 10;
    Random rand = new Random(100);

    public BalloonRightToLeft() {
        super( Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight(), "ballon.png");


    }

    public void Move(){
        if(shouldMove == false)
        {
            return;
        }
        if (!super.getScreenRectangel().contains( super.getBound()))
        {
            super.ShowImpact();
            shouldMove = false;
        }

        float delta = rand.nextFloat() * FIRST_LEVEL_MAX_DELTA + 0.2f;
        super.getBound().x += weight*(delta*2);
        super.getBound().y -= weight*(delta/2);


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

    @Override
    public void SetNewPosition() {
        float newX =  0 + rand.nextInt(Gdx.graphics.getWidth()/4);
        float newY = Gdx.graphics.getHeight() - bound.y + rand.nextInt(Gdx.graphics.getHeight()/10);
        bound.setCenter(newX, newY);
    }
}
