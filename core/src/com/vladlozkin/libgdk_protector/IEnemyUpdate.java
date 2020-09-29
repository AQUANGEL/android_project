package com.vladlozkin.libgdk_protector;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface IEnemyUpdate {
    boolean Visible();
    void Hide();
    void Show();
    void Move();
    void Draw(SpriteBatch spriteBatch);
    void SetNewPosition();
    Rectangle GetBound();
}
