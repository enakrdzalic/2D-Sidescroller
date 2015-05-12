package com.defy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.defy.com.defy.dgHelpers.AssetLoader;
import com.defy.gameobjects.*;
import com.defy.gameobjects.Character;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ena on 15-02-17.
 */
public class GameRenderer {

    private Character myCharacter;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;
    private BitmapFont font;

    private int midPointY;
    private int gameHeight;

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    private MapLayer wallLayer;
    private MapObjects wallObjects;

    private MapLayer coinsLayer;
    private MapObjects coinsObjects;

    private List<MapLayer> allCoins;
    private List<MapObjects> allCoinsObjects;

    private int xMovedSoFar;
    private int score;
    private Boolean flipped = false;
    private Boolean hasStarted = false;
    private Boolean isOver = false;

    //private Sound coinSound = Gdx.audio.newSound(Gdx.files.internal("data/coin1.wav"));

    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        this.gameHeight = gameHeight;
        this.midPointY = midPointY;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 136, gameHeight); // TODO: set to true to flip upside down
        cam.zoom = 2.7f;
        cam.translate(120f, 180f);
        cam.update();

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        font = new BitmapFont();
        font.setScale(2, -2);
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        tiledMap = new TmxMapLoader().load("data/DefyGravity.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        wallLayer = tiledMap.getLayers().get("Walls");
        wallObjects = wallLayer.getObjects();
        coinsLayer = tiledMap.getLayers().get("Coins Obj");
        coinsObjects = coinsLayer.getObjects();

        String tL = "Tile Layer ";
        allCoins = new ArrayList<MapLayer>();
        for (int i = 4; i <= 91; i++) {
            allCoins.add(tiledMap.getLayers().get(tL + i));
        }
        allCoinsObjects = new ArrayList<MapObjects>();
        for (int i = 0; i <= 87; i++) {
            allCoinsObjects.add(allCoins.get(i).getObjects());
        }

        xMovedSoFar = 0;
        score = 0;

        myCharacter = world.getCharacter();
    }

    public void render(float runTime) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!myCharacter.hasStarted()) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(70 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
                shapeRenderer.rect(-150, 0, 4 * 136, 4 * (166));
                shapeRenderer.end();

                tiledMapRenderer.setView(cam);
                tiledMapRenderer.render();

                // Begin SpriteBatch
                batcher.begin();
                batcher.enableBlending();
                batcher.draw(AssetLoader.birdAnimation.getKeyFrame(runTime),
                        myCharacter.getX(), myCharacter.getY(), myCharacter.getWidth(), myCharacter.getHeight());
                font.draw(batcher, "Score:" + score, 10, 10);
                font.draw(batcher, "Tap to play!", 100, midPointY * 4);


                // End SpriteBatch
                batcher.end();
                return;
        }

        if (isOver) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(70 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
            shapeRenderer.rect(-150, 0, 4 * 136, 4 * (166));
            shapeRenderer.end();

            tiledMapRenderer.setView(cam);
            tiledMapRenderer.render();

            // Begin SpriteBatch
            batcher.begin();
            batcher.enableBlending();
            batcher.draw(AssetLoader.birdAnimation.getKeyFrame(runTime),
                    myCharacter.getX(), myCharacter.getY(), myCharacter.getWidth(), myCharacter.getHeight());
            font.draw(batcher, "Score:" + score, 10, 10);
            font.draw(batcher, "Game Over!", 100, midPointY * 3);
            font.draw(batcher, "Tap to play again", 70, midPointY * 3.5f);



            // End SpriteBatch
            batcher.end();
            return;
        }

        hasStarted = true;
        cam.translate(3, 0);
        xMovedSoFar += 3;
        cam.update();
        checkCollisions();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(70 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(-150, 0, 4 * 136, 4 * (166));
        shapeRenderer.end();

        tiledMapRenderer.setView(cam);
        tiledMapRenderer.render();

        // Begin SpriteBatch
        batcher.begin();
        batcher.enableBlending();
        batcher.draw(AssetLoader.birdAnimation.getKeyFrame(runTime),
                myCharacter.getX(), myCharacter.getY(), myCharacter.getWidth(), myCharacter.getHeight());
        font.draw(batcher, "Score:" + score, 10, 10);


        // End SpriteBatch
        batcher.end();

        if (myCharacter.getX() + xMovedSoFar > 3330 && !flipped){
            flipped = true;
        }

    }

    private void checkCollisions() {
        Rectangle charRect = new Rectangle(
                myCharacter.getX() + xMovedSoFar - myCharacter.getWidth()/2,
                myCharacter.getY(),
                myCharacter.getWidth(),
                myCharacter.getHeight());

        for (EllipseMapObject coin : coinsObjects.getByType(EllipseMapObject.class)) {
            Ellipse ellipse = coin.getEllipse();
            Circle circle = new Circle(ellipse.x, ellipse.y, ellipse.height / 2);

            if (Intersector.overlaps(circle, charRect)) {
                Gdx.app.log("DefyGravity", "CIRCLE!!");
                coinsObjects.remove(coin);
                Gdx.app.log("DefyGravity", Integer.toString((int) myCharacter.getX() + xMovedSoFar));
                removeCoin((int) myCharacter.getX() + xMovedSoFar);
                score++;
                //coinSound.play(1, .3f, 1);
            }
        }

        for (MapObject object : wallObjects) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                if (Intersector.overlaps(rect, charRect)) {
                    Gdx.app.log("DefyGravity", "HIT A RECT");
                    if (!isOver) {
                        isOver = true;
                        myCharacter.setIsOver(true);
                    }
                    //wallObjects.remove(object);
                }
            } else if (object instanceof PolygonMapObject) {
                Polygon polygon = ((PolygonMapObject) object).getPolygon();
                Polygon charPoly = new Polygon();
                charPoly.setOrigin(myCharacter.getX() + xMovedSoFar, myCharacter.getY());
                float[] vertices = {
                        0, 0,
                        0, myCharacter.getHeight(),
                        myCharacter.getWidth(), 0,
                        myCharacter.getWidth(), myCharacter.getHeight()};
                charPoly.setVertices(vertices);
                if (Intersector.overlapConvexPolygons(polygon, charPoly)) {
                    Gdx.app.log("DefyGravity", "HIT A POLY");

                }
            } else if (object instanceof PolylineMapObject) {
                Polyline chain = ((PolylineMapObject) object).getPolyline();
                //Gdx.app.log("DefyGravity", "HIT A LINE");
            }
        }
    }

    void removeCoin(int x) {
        x = (x - 500) / 70;
        Gdx.app.log("DefyGravity", Integer.toString(x));
        allCoins.get(x).setVisible(false);
    }

    public void dispose() {
        font.dispose();
        //coinSound.dispose();
    }

}
