package com.defy.com.defy.dgHelpers;

import com.badlogic.gdx.InputProcessor;
import com.defy.gameobjects.Character;

/**
 * Created by Ena on 15-02-18.
 */
public class InputHandler implements InputProcessor {

    private Character character;

    public InputHandler(Character character){
        this.character = character;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        character.onClick();
        return true; // means we handled the touch
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
