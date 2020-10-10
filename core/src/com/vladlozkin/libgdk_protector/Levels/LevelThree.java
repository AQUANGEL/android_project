package com.vladlozkin.libgdk_protector.Levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonExtinguisher;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonLeftToRight;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonRightToLeft;
import com.vladlozkin.libgdk_protector.IEnemy;
import com.vladlozkin.libgdk_protector.MissileImpl.MissileDownTrajectory;
import com.vladlozkin.libgdk_protector.MissileImpl.MissileUpTrajectory;

import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class LevelThree implements ILevel {

    Texture backgroundTexture;
    Sprite backgroundSprite;
    SpriteBatch m_SpriteBatch;

    public LevelThree(SpriteBatch spriteBatch) {
        m_SpriteBatch = spriteBatch;
        backgroundTexture = new Texture("farmResized.png");
        backgroundSprite = new Sprite(backgroundTexture);
    }

    @Override
    public CopyOnWriteArrayList<IEnemy> InitLevel() {
        CopyOnWriteArrayList enemise = new CopyOnWriteArrayList<IEnemy>();
        IEnemy balloon;

        for(int i = 0; i < 3; i++)
        {
            balloon = new BalloonRightToLeft();
            balloon.SetSpeed(0.4f, 0.2f);
            enemise.add(balloon);
        }

        for(int i = 0; i < 3; i++)
        {
            balloon = new BalloonLeftToRight();
            balloon.SetSpeed(0.4f, 0.2f);
            enemise.add(balloon);
        }

        for(int i = 0; i < 1; i++)
        {
            enemise.add(new BalloonExtinguisher());
        }

        enemise.add(new MissileDownTrajectory());
        enemise.add(new MissileDownTrajectory());
        enemise.add(new MissileDownTrajectory());


        Collections.shuffle(enemise);

        return enemise;
    }

    @Override
    public void RenderBackground() {
        backgroundSprite.draw(m_SpriteBatch);
    }

    @Override
    public void AdditionalRenderingIfNeeded() {

    }
}


