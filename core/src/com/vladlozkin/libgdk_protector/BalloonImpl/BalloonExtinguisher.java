package com.vladlozkin.libgdk_protector.BalloonImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vladlozkin.libgdk_protector.Enemy;

import java.util.Random;

public class BalloonExtinguisher extends Enemy {

    Texture waterTexture;
    private final float FIRST_LEVEL_MAX_DELTA = 0.1f;
    private float weight = 10;

    public BalloonExtinguisher()
    {
        super( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), "BallonWithGift.png");
        waterTexture = new Texture(Gdx.files.internal("waterSplash3.png"));
        SetNewPosition();
    }

    @Override
    public void ShowImpact() {
        textureRegion.setRegion(waterTexture);
    }

    @Override
    public void Move(float delta) {
        if (CheckIfShouldMove())
        {
//            float delta = rand.nextFloat() * FIRST_LEVEL_MAX_DELTA + 0.2f;
            super.GetBound().x -= weight*(delta*2 + 0.2f);
            if (textureRegion.getTexture() == waterTexture)
            {
                weight = 20;
                super.GetBound().y -= weight*(delta*1.5 + 0.2f);
            }
            else
            {
                super.GetBound().y -= 2*(delta/2 + 0.2f);
            }
        }
    }

    @Override
    public void SetNewPosition() {
        float newX =  Gdx.graphics.getWidth() - rand.nextInt(Gdx.graphics.getWidth()/4);
        float newY = Gdx.graphics.getHeight() - bound.y + rand.nextInt(Gdx.graphics.getHeight()/6);
        bound.setCenter(newX, newY);
    }

    @Override
    public int Score()
    {
        return 0;
    }
}