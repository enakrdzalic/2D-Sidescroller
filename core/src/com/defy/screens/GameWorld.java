package com.defy.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.defy.gameobjects.*;
import com.defy.gameobjects.Character;

/**
 * Created by Ena on 15-02-17.
 */
public class GameWorld {
    private Character character;
    public static int width = Gdx.graphics.getWidth() /4;
    public static int height = Gdx.graphics.getHeight() /6;

    public GameWorld(int midPointY, Game g) {
        character = new Character(70, midPointY, width, height, g);

    }

    public void update(float delta) {
        character.update(delta);
    }

    public Character getCharacter() {
        return character;
    }

}
