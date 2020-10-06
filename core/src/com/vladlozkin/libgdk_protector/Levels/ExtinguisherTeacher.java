package com.vladlozkin.libgdk_protector.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonExtinguisher;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonLeftToRight;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonRightToLeft;
import com.vladlozkin.libgdk_protector.IEnemy;

import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class ExtinguisherTeacher implements ILevel {

    Texture backgroundTexture;
    Sprite backgroundSprite;
    SpriteBatch m_SpriteBatch;

    public ExtinguisherTeacher(SpriteBatch spriteBatch) {
        m_SpriteBatch = spriteBatch;
        backgroundTexture = new Texture("farmResized.png");
        backgroundSprite = new Sprite(backgroundTexture);
    }

    @Override
    public CopyOnWriteArrayList<IEnemy> InitLevel() {
        CopyOnWriteArrayList enemise = new CopyOnWriteArrayList<IEnemy>();

        for(int i = 0; i < 5; i++)
        {
            BalloonRightToLeft balloon = new BalloonRightToLeft();
            balloon.SetPositionOnTheGround(Gdx.graphics.getHeight()/6);
            enemise.add(balloon);
        }

        for(int i = 0; i < 5; i++)
        {
            BalloonLeftToRight balloon = new BalloonLeftToRight();
            balloon.SetPositionOnTheGround(Gdx.graphics.getHeight()/6);
            enemise.add(balloon);
        }

        Collections.shuffle(enemise);

        BalloonExtinguisher extinguisher = new BalloonExtinguisher();
        extinguisher.SetNewPosition();
        enemise.add(extinguisher);

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
