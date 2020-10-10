package com.vladlozkin.libgdk_protector;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface IEnemy {
    boolean Visible();
    void Hide();
    void Show();
    void Move(float delta) throws Exception;
    void Draw(SpriteBatch spriteBatch);
    void SetNewPosition() throws Exception;
    void ShowImpact();
    boolean TouchedGround();
    void setTouchedGround(boolean touchedGround);
    Rectangle GetBound();
    int Score();
    void SetSpeed(float xSpeed, float ySpeed);
    void OnHitSound();
}
