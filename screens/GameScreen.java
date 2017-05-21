package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.richardsspot.game.Assets;
import com.richardsspot.game.IActivityRequestHandler;
import com.richardsspot.game.ScoreManager;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


import Ball.*;
import imageActors.Background;
import imageActors.Ground;
import imageActors.LargeCloud;
import imageActors.Score;
import imageActors.SmallCloud;

/**
 * Created by Richard Hall on 16/04/15.
 */
public class GameScreen implements Screen {

    private static final float ASPECT_RATIO = Assets.WORLD_WIDTH / Assets.WORLD_HEIGHT;

    private static Game game;

    private static Stage stage;
    private Viewport viewPort;
    private OrthographicCamera camera;

    private static LargeCloud cloud1, cloud2;
    private static SmallCloud sCloud1, sCloud2;

    private static Ground ground;
    private static Background background;

    private static int touchCount;
    private static int score;
    private static int ballCount;
    private static long repeatTime;

    private static float ballSpeed;

    private static List<Ball>ballsInPlay;

    private static List<Integer>xPlaces;

    private static Label scoreLabel;

    private static BitmapFont buttonFont;

    private static TextButton replayButton, menuButton;
    private static TextButtonStyle replayButtonStyle, menuButtonStyle;

    private static Skin skins;

    private static Score scoreImage;

    private static IActivityRequestHandler handler;

    public GameScreen(Game passedGame, IActivityRequestHandler handler) {
        game = passedGame;
        this.handler = handler;
        this.handler.showAds(true);


        ScoreManager.checkForScoreFile();

    }



    @Override
    public void show() {

        camera = new OrthographicCamera();

        //create the viewport
        viewPort = new StretchViewport(Assets.WORLD_WIDTH, Assets.WORLD_HEIGHT, camera);
        viewPort.apply();

        //create the stage and add the viewport
        stage = new Stage();
        stage.setViewport(viewPort);
        camera.update();



        //initialise the variables
        touchCount = 0;
        score = 0;
        ballCount = 5;
        ballSpeed = 2.8f;

        //initialise collections
        ballsInPlay = new ArrayList<Ball>();
        xPlaces = new ArrayList<Integer>();

        //create the ground
        ground = new Ground();
        //create the background
        background = new Background(false);
        //create the scoreImage
        scoreImage = new Score(Assets.WORLD_WIDTH, Assets.WORLD_HEIGHT);

        //this.loadAssets();
        this.populateXPlaces();

        //set the stage
        setTheStage();

        Assets.getInstance().playGameSound();

        //set the balls in play
        setTheBallsInPlay();
        //set the score label
        setTheScoreLabel();
        //start the game
        startGame();

        //set the input processor
        Gdx.input.setInputProcessor(stage);

        this.setupGameOver();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);
        stage.getViewport().update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

    }

    @Override
    public void pause() {
        for(Ball b : ballsInPlay){
            b.clearActions();
        }
    }

    @Override
    public void resume() {
        startGame();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.clear();
        stage.dispose();
        game.dispose();
        Gdx.input.setInputProcessor(null);
        camera = null;
        viewPort = null;
        for(Ball b : ballsInPlay){
            b.dispose();
        }
        ballsInPlay = null;
        cloud1.dispose();
        cloud1 = null;
        cloud2.dispose();
        cloud2 = null;

        sCloud1.dispose();
        sCloud1 = null;
        sCloud2.dispose();
        sCloud2 = null;
    }
    /**
     * Set the stage
     */
    public static void setTheStage(){
        stage.clear();
        //add the background
        stage.addActor(background);

        //add the clouds

        cloud1 = new LargeCloud(Assets.WORLD_HEIGHT * 0.85f);
        stage.addActor(cloud1);
        cloud1.moveCloud();

        cloud2 = new LargeCloud(Assets.WORLD_HEIGHT * 0.75f);
        stage.addActor(cloud2);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                cloud2.moveCloud();
            }
        }, 20000);


        sCloud1 = new SmallCloud(Assets.WORLD_HEIGHT * 0.3f);
        stage.addActor(sCloud1);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sCloud1.moveCloud();
            }
        }, 16000);

        sCloud2 = new SmallCloud(Assets.WORLD_HEIGHT * 0.2f);
        stage.addActor(sCloud2);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sCloud2.moveCloud();
            }
        }, 30000);


    }

    private static void setTheScoreLabel(){

        scoreImage.setZIndex(1000);
        stage.addActor(scoreImage);

        //add the score label
        //label style for the score and level labels
        LabelStyle labelStyle = new LabelStyle();
        BitmapFont labelFont = new BitmapFont(Gdx.files.internal("scores_font.fnt"), Gdx.files.internal("scores_font_0.png"), false);
        labelStyle.font = labelFont;
        labelStyle.fontColor = Color.BLACK;
        scoreLabel = new Label(String.valueOf(score), labelStyle);
        scoreLabel.setAlignment(Align.center);
        scoreLabel.setPosition(Assets.WORLD_WIDTH - 220, Assets.WORLD_HEIGHT - 110);
        scoreLabel.setZIndex(1010);
        stage.addActor(scoreLabel);
        //add the ground
        stage.addActor(ground);
    }


    /**
     * Count the ball touch
     */
    public static void ballTouched(){
        ++touchCount;
        if(touchCount == ballCount){
            touchCount = 0;
            levelUp();
        }
    }

    /**
     * Level up the game
     */
    public static void levelUp(){
        touchCount = 0;
        ballCount += 5;
        if(ballSpeed > 1.0f){
            ballSpeed -= 0.1f;
        }else{
            ballSpeed = 1.0f;
        }

        scoreImage.remove();
        scoreLabel.remove();
        setTheBallsInPlay();
        setTheScoreLabel();
        startGame();
    }

    /**
     *Add a point
     */
    public static void addPoint(int point){
        score += point;
        if(score > 0){
            updateScoreLabel(score);
        }else{
            updateScoreLabel(0);
            gameOver();
        }
    }

    /**
     * Update the score
     */
    private static void updateScoreLabel(int score){
        if(score > 0){
            scoreLabel.setText(String.valueOf(score));
        }else{
            scoreLabel.setText(String.valueOf(0));
        }

    }

    /**
     * Put the balls in play for the level
     */
    public static void setTheBallsInPlay(){

        Random rand = new Random();
        int type = rand.nextInt(200);

        ballsInPlay.clear();
        int previous = -1;
        for(int i = 1; i <= ballCount; i++){
            if (type >= 95 && type <= 100) {
                int next = rand.nextInt(xPlaces.size());
                if(next == previous){
                    next = rand.nextInt(xPlaces.size());
                }
                BombBall tempBall = new BombBall(xPlaces.get(next));
                stage.addActor(tempBall);
                ballsInPlay.add(tempBall);
                previous = next;
                type = rand.nextInt(200);
            }else if (type == 29 || type == 31 || type == 56 || type == 84) {
                int next = rand.nextInt(xPlaces.size());
                if(next == previous){
                    next = rand.nextInt(xPlaces.size());
                }
                BonusBall tempBall = new BonusBall(xPlaces.get(next));
                stage.addActor(tempBall);
                ballsInPlay.add(tempBall);
                type = rand.nextInt(200);
            } else {
                int next = rand.nextInt(xPlaces.size());
                if(next == previous){
                    next = rand.nextInt(xPlaces.size());
                }
                StandardBall tempBall = new StandardBall(xPlaces.get(next));
                stage.addActor(tempBall);
                ballsInPlay.add(tempBall);
                type = rand.nextInt(200);
            }
        }
    }

    /**
     * Launch the balls which are set for the game
     */
    public static void startGame(){
        repeatTime = 1000l;
        for(final Ball b : ballsInPlay){
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    b.launchBall(ballSpeed);
                }
            }, repeatTime);
            repeatTime += 300l;

        }
    }

    private void setupGameOver(){
        skins = new Skin();
        skins.add("replay_button", Assets.getInstance().getReplay_button_unpressed());
        skins.add("menu_button", Assets.getInstance().getMenu_button_unpressed());

        //button style
        buttonFont = new BitmapFont();
        replayButtonStyle = new TextButtonStyle();
        replayButtonStyle.font = buttonFont;
        replayButtonStyle.up = skins.getDrawable("replay_button");

        //replay button
        replayButton = new TextButton("", replayButtonStyle);
        replayButton.setPosition((Assets.WORLD_WIDTH / 2) - (replayButton.getWidth() / 2), (Assets.WORLD_HEIGHT / 2) - (replayButton.getHeight() / 2) + 100);
        replayButton.setBounds(replayButton.getX(), replayButton.getY(), replayButton.getWidth(), replayButton.getHeight());
        replayButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
                ScoreManager.checkNewScore(score);
                game.setScreen(new GameScreen(game, handler));
                return false;
            }

        });

        menuButtonStyle = new TextButtonStyle();
        menuButtonStyle.font = buttonFont;
        menuButtonStyle.up = skins.getDrawable("menu_button");
        //exit to menu button
        menuButton = new TextButton("", menuButtonStyle);
        menuButton.setPosition((Assets.WORLD_WIDTH / 2) - (menuButton.getWidth() / 2), (Assets.WORLD_HEIGHT / 2) - (menuButton.getHeight() / 2 + 100));
        menuButton.setBounds(menuButton.getX(), menuButton.getY(), menuButton.getWidth(), menuButton.getHeight());
        menuButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
                ScoreManager.checkNewScore(score);

                game.setScreen(new screens.MenuScreen(game, handler));
                return true;
            }
        });
    }


    /**
     * Game over
     */
    public static void gameOver(){
        touchCount = 0;
        for(Ball b : ballsInPlay){
            b.setTouchable(Touchable.disabled);
            b.gameIsOver();
            if(b.getY() > Assets.WORLD_HEIGHT){
                b.remove();
            }
        }
        ballsInPlay.clear();


        stage.addActor(replayButton);
        stage.addActor(menuButton);

    }

    /**
     * populate the xPlaces list
     */
    private void populateXPlaces(){
        int count = 52;
        for(int i = 0; i < 17; i++){
            xPlaces.add(count);
            count += 115;

        }
    }

}
