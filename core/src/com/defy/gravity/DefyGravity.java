package com.defy.gravity;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.defy.com.defy.dgHelpers.AssetLoader;
import com.defy.screens.GameScreen;
import com.defy.screens.MainMenu;

public class DefyGravity extends Game {
    @Override
    public void create() {
        Gdx.app.log("DefyGravity", "created");
        AssetLoader.load();
        setScreen(new MainMenu(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
