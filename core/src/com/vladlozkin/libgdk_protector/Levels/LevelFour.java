package com.vladlozkin.libgdk_protector.Levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonExtinguisher;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonLeftToRight;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonRightToLeft;
import com.vladlozkin.libgdk_protector.IEnemy;
import com.vladlozkin.libgdk_protector.MissileImpl.MissileDownTrajectory;

import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class LevelFour implements ILevel {

    Texture backgroundTexture;
    Sprite backgroundSprite;
    SpriteBatch m_SpriteBatch;

    public LevelFour(SpriteBatch spriteBatch) {
        m_SpriteBatch = spriteBatch;
        backgroundTexture = new Texture("farmResized.png");
        backgroundSprite = new Sprite(backgroundTexture);
    }

    @Override
    public CopyOnWriteArrayList<IEnemy> InitLevel() {
        CopyOnWriteArrayList enemise = new CopyOnWriteArrayList<IEnemy>();
        IEnemy balloon;

        for(int i = 0; i < 1; i++)
        {
            balloon = new BalloonRightToLeft();
            balloon.SetSpeed(0.6f, 0.3f);
            enemise.add(balloon);
        }

        for(int i = 0; i < 3; i++)
        {
            balloon = new BalloonLeftToRight();
            balloon.SetSpeed(0.6f, 0.3f);
            enemise.add(balloon);
        }

        for(int i = 0; i < 1; i++)
        {
            enemise.add(new BalloonExtinguisher());
        }

        for(int i = 0; i < 3; i++)
        {
            enemise.add(new MissileDownTrajectory());
        }

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


