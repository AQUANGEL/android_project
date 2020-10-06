package com.vladlozkin.libgdk_protector.MissileImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.vladlozkin.libgdk_protector.Enemy;
import com.vladlozkin.libgdk_protector.IEnemy;

public class MissileUpTrajectory extends Enemy implements IEnemy
{
    float rotationDegree = 10;
    private float weight = 10;
    protected TextureRegion textureRegion;
    float velocityX = 10f;
    float velocityY = 16f;
    Sound sound = Gdx.audio.newSound(Gdx.files.internal("grad.wav"));

    public MissileUpTrajectory() {
        super( 0, 0, "missile.png");
        textureRegion = new TextureRegion(texture);
    }

    @Override
    public void Move(float delta) {
//        float delta = rand.nextFloat() * 0.2f + 0.2f;
        super.GetBound().x += velocityX * delta*2;
        super.GetBound().y += velocityY * delta*2;
        velocityY = velocityY * delta + 10;
        rotationDegree -= 0.04;

        if (!super.getScreenRectangel().contains( super.GetBound()))
        {
            super.Hide();
        }
    }

    @Override
    public Rectangle GetBound() {
        return super.GetBound();
    }

    @Override
    public void SetNewPosition() {
        float newX = rand.nextInt(Gdx.graphics.getWidth());
        float newY = 0;
        rotationDegree = 0;
        velocityX = 10f;
        velocityY = 16f;
        bound.setCenter(newX, newY);
//        sound.play(0.3f);
    }
}


