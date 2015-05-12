package com.defy.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.defy.com.defy.dgHelpers.AssetLoader;

/**
 * Created by Ena on 15-05-02.
 */
public class MainMenu implements Screen {
    private Stage stage = new Stage();
    private Skin skin = new Skin();
    private OrthographicCamera cam = new OrthographicCamera();
    private SpriteBatch batcher = new SpriteBatch();
    private Game g;
    private BitmapFont font;
    public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|/?-+=()*&.;,{}<>";
    private TextButton textButton;

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    private MapLayer floor;
    private MapObjects floorObjs;

    private MapLayer clouds1;
    private MapObjects clouds1Objs;

    private MapLayer clouds2;
    private MapObjects clouds2Objs;

    Texture backgroundImg;

    public MainMenu(Game g) {
        this.g = g;
        create();
    }

    @Override
    public void show() {

    }

    public void create() {
        Gdx.input.setInputProcessor(stage);
        Pixmap pixmap = new Pixmap(130,100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.YELLOW);
        pixmap.fill();
        cam.setToOrtho(true);
        cam.zoom = 1.5f;
        cam.translate(130, 135);
        cam.update();
        batcher.setProjectionMatrix(cam.combined);

//        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameter.size = 12;
//        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
//        generator.dispose();

        skin.add("white", new Texture(pixmap));

        BitmapFont font = new BitmapFont();
        font.scale(1);
        skin.add("default",font);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.ORANGE);
        textButtonStyle.down = skin.newDrawable("white", Color.ORANGE);
        textButtonStyle.checked = skin.newDrawable("white", Color.ORANGE);
        textButtonStyle.over = skin.newDrawable("white", Color.BLACK);

        textButtonStyle.font = font;


        skin.add("default", textButtonStyle);

        textButton = new TextButton("P L A Y",textButtonStyle);
        textButton.setPosition(70, 100);
        stage.addActor(textButton);
        stage.addActor(textButton);
        stage.addActor(textButton);

        backgroundImg = new Texture(Gdx.files.internal("data/bgrd.png"));

//        tiledMap = new TmxMapLoader().load("data/mainmenu.tmx");
//        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 0.5f); // TODO: change this to depend on height
//
//        floor = tiledMap.getLayers().get("Ground");
//        floorObjs = floor.getObjects();
//        clouds1 = tiledMap.getLayers().get("Cloud 1");
//        clouds1Objs = clouds1.getObjects();
//        clouds2 = tiledMap.getLayers().get("Cloud 2");
//        clouds2Objs = clouds2.getObjects();

        //tiledMapRenderer.render();

        textButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                //System.out.println("Clicked! Is checked: " + button.isChecked());
                textButton.setText("Starting new game");
                g.setScreen(new GameScreen(g));

            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batcher.begin();
        batcher.draw(backgroundImg,0,0);
        batcher.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        cam.update();

        //tiledMapRenderer.render();

        // Begin SpriteBatch
        batcher.begin();
        batcher.enableBlending();
        batcher.draw(AssetLoader.birdAnimation.getKeyFrame(delta),
                200, 160, GameWorld.width*1.3f, GameWorld.height*1.3f);

        // End SpriteBatch
        batcher.end();
    }

    @Override
    public void resize(int width, int height) {
        //stage.setViewport(width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
