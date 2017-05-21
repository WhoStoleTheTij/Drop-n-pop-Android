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

import imageActors.Background;

/**
 * Created by Richard Hall on 23/07/15.
 */
public class CreditScreen implements Screen {

    private static Game game;
    private Stage stage;
    private Viewport viewPort;
    private OrthographicCamera camera;

    private Background background;

    private TextButton backButton, playButton;
    private TextButtonStyle buttonStyle, playButtonStyle;
    private BitmapFont font;

    private LabelStyle labelStyle;

    private Skin skins;


    private static IActivityRequestHandler handler;

    public CreditScreen(Game game, IActivityRequestHandler handler){
        this.game = game;
        this.handler = handler;
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
        skins.add("play_button_unpressed", Assets.getInstance().getPlayButton_unpressed());

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
                game.setScreen(new ScoreScreen(game, handler));
                return true;
            }
        });
        stage.addActor(backButton);

        playButtonStyle = new TextButtonStyle();
        playButtonStyle.font = font;
        playButtonStyle.up = skins.getDrawable("play_button_unpressed");
        playButton = new TextButton("", playButtonStyle);
        playButton.setPosition(Assets.WORLD_WIDTH - (playButton.getWidth() + 10), Assets.WORLD_HEIGHT - 180);
        playButton.setBounds(playButton.getX(), playButton.getY(), playButton.getWidth(), playButton.getHeight());
        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button){
                game.setScreen(new GameScreen(game, handler));
                return true;
            }
        });
        stage.addActor(playButton);

        this.setCreditLabels();

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

    private void setCreditLabels(){

        BitmapFont font = new BitmapFont(Gdx.files.internal("scores_font.fnt"), Gdx.files.internal("scores_font_0.png"), false);
        labelStyle = new LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;

        Label artistNameTitleLabel = new Label("Artist:", labelStyle);
        artistNameTitleLabel.setPosition(Assets.WORLD_WIDTH * 0.2f, Assets.WORLD_HEIGHT * 0.7f);
        stage.addActor(artistNameTitleLabel);

        Label artistNameLabel = new Label("Alena Millen", labelStyle);
        artistNameLabel.setPosition(Assets.WORLD_WIDTH * 0.35f, Assets.WORLD_HEIGHT * 0.7f);
        stage.addActor(artistNameLabel);

        Label soundEffectsTitleLabel = new Label("Sound Effects:", labelStyle);
        soundEffectsTitleLabel.setPosition(Assets.WORLD_WIDTH * 0.2f, Assets.WORLD_HEIGHT * 0.5f);
        stage.addActor(soundEffectsTitleLabel);

        Label explosionRecorderLabel = new Label("Explosion(Blast): Recorded by Mike Koenig - soundbible.com", labelStyle);
        explosionRecorderLabel.setPosition(Assets.WORLD_WIDTH * 0.35f, Assets.WORLD_HEIGHT * 0.5f);
        stage.addActor(explosionRecorderLabel);

        Label popRecorderLabel = new Label("Pop(Blop): Recorded by Mark DiAngelo - soundbible.com", labelStyle);
        popRecorderLabel.setPosition(Assets.WORLD_WIDTH * 0.35f, Assets.WORLD_HEIGHT * 0.45f);
        stage.addActor(popRecorderLabel);

        Label musicTitleLabel = new Label("Music:", labelStyle);
        musicTitleLabel.setPosition(Assets.WORLD_WIDTH * 0.2f, Assets.WORLD_HEIGHT * 0.3f);
        stage.addActor(musicTitleLabel);

        Label musicianLabel = new Label("by Eric Matyas - soundimage.org", labelStyle);
        musicianLabel.setPosition(Assets.WORLD_WIDTH * 0.35f, Assets.WORLD_HEIGHT * 0.3f);
        stage.addActor(musicianLabel);
    }

}
