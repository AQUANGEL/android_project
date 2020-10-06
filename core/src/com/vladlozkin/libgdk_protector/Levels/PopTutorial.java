package com.vladlozkin.libgdk_protector.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.vladlozkin.libgdk_protector.BalloonImpl.BalloonStationary;
import com.vladlozkin.libgdk_protector.IEnemy;

import java.util.concurrent.CopyOnWriteArrayList;

public class PopTutorial implements ILevel{

    Texture backgroundTexture;
    Sprite backgroundSprite;
    SpriteBatch m_SpriteBatch;

    Label swipeTeacherText;
    Label tapTeacherText;
    Label.LabelStyle textStyle;
    BitmapFont font;

    public PopTutorial(SpriteBatch spriteBatch)
    {
        backgroundTexture = new Texture("farmResized.png");
        backgroundSprite = new Sprite(backgroundTexture);
        m_SpriteBatch = spriteBatch;

        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textStyle = new Label.LabelStyle();
        textStyle.font = font;
        swipeTeacherText = new Label("Swipe to POP the balloon", textStyle);
        tapTeacherText = new Label("Tap to POP the balloon", textStyle);
        swipeTeacherText.setColor(Color.ROYAL);
        tapTeacherText.setColor(Color.ROYAL);
        swipeTeacherText.setFontScale(4f,4f);
        tapTeacherText.setFontScale(4f,4f);
    }

    @Override
    public CopyOnWriteArrayList<IEnemy> InitLevel() {

        CopyOnWriteArrayList enemies = new CopyOnWriteArrayList<IEnemy>();

        BalloonStationary swipeBalloon = new BalloonStationary(Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getWidth()/2);
        BalloonStationary tapBalloon = new BalloonStationary(Gdx.graphics.getWidth()*0.8f, Gdx.graphics.getWidth()/2 + 200);
        enemies.add(swipeBalloon);
        enemies.add(tapBalloon);

        swipeTeacherText.setBounds(swipeBalloon.GetBound().x, swipeBalloon.GetBound().y,200,100);
        tapTeacherText.setBounds(tapBalloon.GetBound().x, tapBalloon.GetBound().y,200,100);

        return enemies;
    }

    @Override
    public void RenderBackground() {
        backgroundSprite.draw(m_SpriteBatch);
    }


    @Override
    public void AdditionalRenderingIfNeeded() {
        swipeTeacherText.draw(m_SpriteBatch, (float) 1.0);
        tapTeacherText.draw(m_SpriteBatch, (float) 1.0);
    }
}
