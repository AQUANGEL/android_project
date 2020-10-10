package com.vladlozkin.libgdk_protector.Levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vladlozkin.libgdk_protector.IEnemy;
import com.vladlozkin.libgdk_protector.MissileImpl.MissileDownTrajectory;
import com.vladlozkin.libgdk_protector.MissileImpl.MissileDownTrajectoryLeftToRight;
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

        for(int i = 0; i < 2; i++)
        {
            enemise.add(new MissileDownTrajectory());
        }

        for(int i = 0; i < 2; i++)
        {
            enemise.add(new MissileDownTrajectoryLeftToRight());
        }

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


