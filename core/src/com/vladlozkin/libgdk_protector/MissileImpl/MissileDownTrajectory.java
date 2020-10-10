package com.vladlozkin.libgdk_protector.MissileImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vladlozkin.libgdk_protector.Enemy;

public class MissileDownTrajectory extends Enemy
{
    float rotationDegree = 260;
    private float weight = 10;
    float velocityX = 4f;
    float velocityY = 7f;

    int m_damageCounter = 0;

    public MissileDownTrajectory() {
        super( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), "missile.png");
        SetNewPosition();
        SetSpeed(2f, 2f);
    }

    @Override
    public void Move(float delta) {

        if (CheckIfShouldMove())
        {
            float delta2 = rand.nextFloat() * 0.2f + 0.2f;
            super.GetBound().x += velocityX * delta2 * xSpeed;
            super.GetBound().y += velocityY * delta2 * ySpeed;
            velocityY = velocityY * delta2 + -2;
            rotationDegree -= 0.01;
        }
    }

    @Override
    public void Show()
    {
        rotationDegree = 260;
        super.shouldDraw = true;
    }

    @Override
    public void Draw(SpriteBatch spriteBatch)
    {
        if (!TouchedGround())
        {
            spriteBatch.draw(textureRegion, bound.x, bound.y, bound.getWidth(), bound.getHeight(), texture.getWidth(), texture.getHeight(), 0.5f,0.5f, rotationDegree);
        }
        else {
            spriteBatch.draw(textureRegion, bound.x, bound.y, bound.getWidth(), bound.getHeight(), texture.getWidth(), texture.getHeight(), 1f,1f, 0);
        }
    }

    @Override
    public void SetNewPosition() {
        float newX = rand.nextInt(Gdx.graphics.getWidth());
        float newY = Gdx.graphics.getHeight();
        rotationDegree = 260;
        velocityX = 4f;
        velocityY = 7f;
        bound.setCenter(newX, newY);
    }

    public int Score() { return 40; }

    public boolean Destroyed()
    {
        return m_damageCounter == 60;
    }

    public void AddDamage()
    {
        m_damageCounter++;
    }
}


