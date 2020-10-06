package com.vladlozkin.libgdk_protector.BalloonImpl;

import com.vladlozkin.libgdk_protector.Enemy;

import java.util.Random;

public class BalloonStationary extends Enemy {

    private final float FIRST_LEVEL_MAX_DELTA = 0.1f;
    private float weight = 10;
    Random rand = new Random();

    public BalloonStationary(float x, float y) {
        super( x, y, "ballon.png");
    }

    public void Move(float delta)
    {
    }

    @Override
    public void SetNewPosition() {
    }
}