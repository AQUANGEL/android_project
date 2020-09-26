package com.vladlozkin.libgdk_protector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Balloon {
    Random rand = new Random();
    private Rectangle bound;
    private Texture texture;
    private float weight = 10; //or gravity or however you want to think about it
    private boolean shouldDraw = true;
    private Rectangle screenRectangel;


    public Balloon(float x, float y){
        texture = new Texture(Gdx.files.internal("ballon.png"));
        bound = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        screenRectangel =  new Rectangle(-bound.getWidth(), -bound.getHeight(),Gdx.graphics.getWidth() + bound.getWidth(),Gdx.graphics.getHeight() + bound.getHeight());
    }

    public void update(float delta){
        bound.x += weight*(delta*2);
        bound.y -= weight*(delta/2);

        if (!screenRectangel.contains(bound))
        {
            bound.x = 0;
            bound.y = Gdx.graphics.getHeight()-bound.getHeight();
        }
    }

    public Rectangle getBound(){
        return bound;
    }

    public boolean DrawMe(){
        return shouldDraw == true;
    }
    public void HideTexture()
    {
//        shouldDraw = false;
        int newX = rand.nextInt(Gdx.graphics.getWidth());
        int newY = rand.nextInt(Gdx.graphics.getHeight());
        bound.setCenter(newX, newY);
    }

    public Texture getTexture(){
        return texture;
    }

    public void draw(SpriteBatch spriteBatch){
        spriteBatch.draw(texture, bound.x, bound.y);
    }
}
