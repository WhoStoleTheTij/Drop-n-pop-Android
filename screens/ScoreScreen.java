package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.richardsspot.game.Assets;
import com.richardsspot.game.IActivityRequestHandler;
import com.richardsspot.game.PlayerScore;
import com.richardsspot.game.ScoreManager;

import java.util.Collections;
import java.util.List;

import imageActors.Background;

/**
 * Created by Richard Hall on 08/05/15.
 */
public class ScoreScreen implements Screen{

    private static Game game;
    private Stage stage;
    private Viewport viewPort;
    private OrthographicCamera camera;

    private Background background;

    private List<PlayerScore> scores;

    private TextButton backButton, creditButton;
    private TextButtonStyle buttonStyle, creditButtonStyle;
    private BitmapFont font;

    private LabelStyle scoreLabelStyle;

    private Skin skins;

    private static IActivityRequestHandler handler;

    //CONSTRUCTOR
    public ScoreScreen(final Game game, IActivityRequestHandler handler){
        this.game = game;
        this.handler = handler;
        this.handler.showAds(true);
        ScoreManager.checkForScoreFile();
        this.scores = ScoreManager.returnScores();
    }


    @Override
    public void show() {


        camera = new OrthographicCamera();
        viewPort = new StretchViewport(Assets.WORLD_WIDTH, Assets.WORLD_HEIGHT, camera);
        viewPort.apply();

        stage = new Stage();
        stage.setViewport(viewPort);
        viewPort.update((int) stage.getWidth(), (int) stage.getHeight(), true);

        Gdx.input.setInputProcessor(stage);

        this.setTheStage();

        skins = new Skin();
        skins.add("back_button_unpressed", Assets.getInstance().getBack_button_unpressed());
        skins.add("credit_button_unpressed", Assets.getInstance().getCredit_button_unpressed());

        font = new BitmapFont();
        buttonStyle = new TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.up = skins.getDrawable("back_button_unpressed");
        backButton = new TextButton("", buttonStyle);
        backButton.setPosition(10, Assets.WORLD_HEIGHT - 180);
        backButton.setBounds(backButton.getX(), backButton.getY(), backButton.getWidth(), backButton.getHeight());
        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game, handler));
                return true;
            }
        });
        stage.addActor(backButton);

        creditButtonStyle = new TextButtonStyle();
        creditButtonStyle.font = font;
        creditButtonStyle.up = skins.getDrawable("credit_button_unpressed");
        creditButton = new TextButton("", creditButtonStyle);
        creditButton.setPosition(Assets.WORLD_WIDTH - (creditButton.getWidth() + 10), Assets.WORLD_HEIGHT - 180);
        creditButton.setBounds(creditButton.getX(), creditButton.getY(), creditButton.getWidth(), creditButton.getHeight());
        creditButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button){
                game.setScreen(new CreditScreen(game, handler));
                return true;
            }
        });
        stage.addActor(creditButton);



        BitmapFont scoreFont = new BitmapFont(Gdx.files.internal("scores_font.fnt"), Gdx.files.internal("scores_font_0.png"), false);
        scoreLabelStyle = new LabelStyle();
        scoreLabelStyle.font = scoreFont;
        scoreLabelStyle.fontColor = Color.BLACK;
        int position = Assets.WORLD_HEIGHT - 200;
        if (scores != null) {
            //sort the collection
            Collections.sort(scores);
            Collections.reverse(scores);
            for(PlayerScore ps : scores){
                Label label = new Label(String.valueOf(ps.getScore()), scoreLabelStyle);
                label.setPosition((Assets.WORLD_WIDTH / 2) - (label.getWidth() / 2), position);
                stage.addActor(label);
                position -= (label.getHeight() + 80);
            }
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
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

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    //set the screen
    private void setTheStage(){
        //add the background
        background = new Background(true);
        stage.addActor(background);
    }

}
