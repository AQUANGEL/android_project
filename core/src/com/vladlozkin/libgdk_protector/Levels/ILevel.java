package com.vladlozkin.libgdk_protector.Levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.vladlozkin.libgdk_protector.IEnemy;

import java.util.concurrent.CopyOnWriteArrayList;

public interface ILevel {
    CopyOnWriteArrayList<IEnemy> InitLevel();
    void RenderBackground();
    void AdditionalRenderingIfNeeded();
}
