package com.vladlozkin.libgdk_protector.Levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonExtinguisher;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonLeftToRight;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonRightToLeft;
import com.vladlozkin.libgdk_protector.IEnemy;
import com.vladlozkin.libgdk_protector.Levels.ILevel;

import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class LevelTwo implements ILevel {

    Texture backgroundTexture;
    Sprite backgroundSprite;
    SpriteBatch m_SpriteBatch;

    public LevelTwo(SpriteBatch spriteBatch) {
        m_SpriteBatch = spriteBatch;
        backgroundTexture = new Texture("farmResized.png");
        backgroundSprite = new Sprite(backgroundTexture);

    }

    @Override
    public CopyOnWriteArrayList<IEnemy> InitLevel() {
        CopyOnWriteArrayList enemise = new CopyOnWriteArrayList<IEnemy>();
        IEnemy balloon;

        for(int i = 0; i < 5; i++)
        {
            balloon = new BalloonRightToLeft();
            balloon.SetSpeed(0.4f, 0.2f);
            enemise.add(balloon);
        }

        for(int i = 0; i < 5; i++)
        {
            balloon = new BalloonLeftToRight();
            balloon.SetSpeed(0.4f, 0.2f);
            enemise.add(balloon);
        }

        for(int i = 0; i < 2; i++)
        {
            enemise.add(new BalloonExtinguisher());
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
