package com.vladlozkin.libgdk_protector.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

    Label extinguisherText;
    Label.LabelStyle textStyle;
    BitmapFont font;

    public ExtinguisherTeacher(SpriteBatch spriteBatch) {
        m_SpriteBatch = spriteBatch;
        backgroundTexture = new Texture("farmResized.png");
        backgroundSprite = new Sprite(backgroundTexture);

        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textStyle = new Label.LabelStyle();
        textStyle.font = font;
        extinguisherText = new Label("Swipe the balloon until it pops\nto extinguish fires\n ", textStyle);
        extinguisherText.setColor(Color.ROYAL);
        extinguisherText.setFontScale(4f,4f);
        extinguisherText.setBounds(150, Gdx.graphics.getHeight()/2,200,100);
    }

    @Override
    public CopyOnWriteArrayList<IEnemy> InitLevel() {
        CopyOnWriteArrayList enemies = new CopyOnWriteArrayList<IEnemy>();

        for(int i = 0; i < 5; i++)
        {
            BalloonRightToLeft balloon = new BalloonRightToLeft();
            balloon.SetPositionOnTheGround(Gdx.graphics.getHeight()/6);
            enemies.add(balloon);
        }

        for(int i = 0; i < 5; i++)
        {
            BalloonLeftToRight balloon = new BalloonLeftToRight();
            balloon.SetPositionOnTheGround(Gdx.graphics.getHeight()/6);
            enemies.add(balloon);
        }

        Collections.shuffle(enemies);

        BalloonExtinguisher extinguisher = new BalloonExtinguisher();
        extinguisher.SetNewPosition();
        enemies.add(extinguisher);

        return enemies;
    }

    @Override
    public void RenderBackground() {
        backgroundSprite.draw(m_SpriteBatch);
    }

    @Override
    public void AdditionalRenderingIfNeeded() {
        extinguisherText.draw(m_SpriteBatch, (float) 1.0);
    }
}
