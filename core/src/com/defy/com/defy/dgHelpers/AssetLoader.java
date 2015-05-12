package com.defy.com.defy.dgHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Ena on 15-03-25.
 */
public class AssetLoader {
    public static Texture texture;

    public static Animation birdAnimation;
    public static TextureRegion birdDown, birdUp;


    public static void load() {

        texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        birdDown = new TextureRegion(new Texture(Gdx.files.internal("data/bee.png")));
        birdDown.flip(true, true);

        birdUp = new TextureRegion(new Texture(Gdx.files.internal("data/bee_fly.png")));
        birdUp.flip(true, true);

        TextureRegion[] birds = { birdDown, birdUp};
        birdAnimation = new Animation(0.12f, birds);
        birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();
    }

}

