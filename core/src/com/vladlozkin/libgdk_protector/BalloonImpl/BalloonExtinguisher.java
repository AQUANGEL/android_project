package com.vladlozkin.libgdk_protector.BalloonImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vladlozkin.libgdk_protector.Enemy;

import java.util.Random;

public class BalloonExtinguisher extends Enemy {

    Texture waterTexture;
    private float weight = 10;
    int m_damageCounter = 0;


    public BalloonExtinguisher()
    {
        super( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), "BallonWithGift.png");
        waterTexture = new Texture(Gdx.files.internal("waterSplash3.png"));
        SetNewPosition();
        SetSpeed(0.5f, 0.2f);
    }

    @Override
    public void ShowImpact() {
        textureRegion.setRegion(waterTexture);
    }

    @Override
    public void Move(float delta) {
        if (CheckIfShouldMove())
        {
            super.GetBound().x -= weight*(delta*2 + xSpeed);
            if (textureRegion.getTexture() == waterTexture)
            {
                weight = 20;
                super.GetBound().y -= weight*(delta*1.5 + ySpeed+0.2);
            }
            else
            {
                super.GetBound().y -= 2*(delta/2 + ySpeed);
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

    public boolean Destroyed()
    {
        return m_damageCounter == 30;
    }

    public void AddDamage()
    {
        m_damageCounter++;
    }

}
