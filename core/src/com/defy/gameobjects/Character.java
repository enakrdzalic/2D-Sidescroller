package com.defy.gameobjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.defy.screens.GameScreen;
import com.defy.screens.GameWorld;

/**
 * Created by Ena on 15-02-17.
 */
public class Character {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float rotation;
    private int width;
    private int height;

    private Boolean hasStarted = false;
    private Boolean isOver = false;
    private Game g;

    public Character (float x, float y, int width, int height, Game g) {
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 460);
        this.g = g;
    }

    public void update(float delta){
        if (!hasStarted || isOver) return;

        velocity.add(acceleration.cpy().scl(delta));
        if (velocity.y > 8* 200)
            velocity.y = 8* 200;

        position.add(velocity.cpy().scl(3*delta));
    }

    public void onClick() {
        if (!hasStarted) hasStarted = true;
        if (isOver) g.setScreen(new GameScreen(g));
        velocity.y = -140;
    }

    public float getX() {
        return position.x;
    }

    public void updateX(int x) {
        position.x += x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isFalling() {
        return velocity.y > 110;
    }

    public boolean hasStarted() {
        return hasStarted;
    }

    public void setHasStarted(boolean b){
        hasStarted = b;
    }

    public void setIsOver(boolean b){
        isOver = b;
    }
    public boolean shouldntFlap() {
        return velocity.y > 70;
    }


}
