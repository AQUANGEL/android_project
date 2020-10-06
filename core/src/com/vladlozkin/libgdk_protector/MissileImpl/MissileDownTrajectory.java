package com.vladlozkin.libgdk_protector.MissileImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vladlozkin.libgdk_protector.Enemy;
import com.vladlozkin.libgdk_protector.IEnemy;

public class MissileDownTrajectory extends Enemy implements IEnemy
{
    float rotationDegree = 260;
    private float weight = 10;
    protected TextureRegion textureRegion;
    float velocityX = 4f;
    float velocityY = 7f;

    public MissileDownTrajectory() {
        super( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), "missile.png");
        textureRegion = new TextureRegion(texture);
    }

    @Override
    public void Move(float delta) {
//        float delta = rand.nextFloat() * 0.2f + 0.2f;
        super.GetBound().x += velocityX * delta*2;
        super.GetBound().y += velocityY * delta*2;
        velocityY = velocityY * delta + -2;
        rotationDegree -= 0.05;

        if (!super.getScreenRectangel().contains( super.GetBound()))
        {
            super.Hide();
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
        spriteBatch.draw(textureRegion, bound.x, bound.y, bound.getWidth()/2, bound.getHeight()/2, texture.getWidth(), texture.getHeight(), 0.5f,0.5f, rotationDegree);
    }

    @Override
    public void SetNewPosition() {
        float newX = rand.nextInt(Gdx.graphics.getWidth());
        float newY = Gdx.graphics.getHeight() - bound.y;
        rotationDegree = 260;
        velocityX = 4f;
        velocityY = 7f;
        bound.setCenter(newX, newY);
    }
}


