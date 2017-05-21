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

import imageActors.Background;

/**
 * Created by Richard Hall on 16/04/15.
 */
public class MenuScreen implements Screen {

    private IActivityRequestHandler handler;

    private final int WORLD_WIDTH = 1024;
    private final int WORLD_HEIGHT = 768;

    private Game game;

    private Stage stage;

    private Viewport viewPort;
    private OrthographicCamera camera;

    private Label playLabel;
    private LabelStyle labelStyle;

    //private TextButton playButton;
    private TextButton scoreButton;
    private TextButton howToPlayButton;

    private TextButtonStyle buttonStyle;

    private TextButton playButton;
    private TextButtonStyle playButtonStyle;
    private TextButtonStyle scoreButtonStyle;
    private TextButtonStyle howToButtonStyle;
    private Skin skins;


    public MenuScreen(Game game, IActivityRequestHandler handler){
        this.game = game;
        this.handler = handler;
        this.handler.showAds(true);
    }

    @Override
    public void show() {

        camera = new OrthographicCamera();
        viewPort = new StretchViewport(Assets.WORLD_WIDTH, Assets.WORLD_HEIGHT, camera);
        viewPort.apply();

        stage = new Stage();
        stage.setViewport(viewPort);
        viewPort.update((int) stage.getWidth(), (int) stage.getHeight(), true);

        Assets.getInstance().playMenuSound();

        Background bg = new Background(true);
        stage.addActor(bg);

        Gdx.input.setInputProcessor(stage);

        BitmapFont font = new BitmapFont(Boolean.parseBoolean("Arial"));
        labelStyle = new LabelStyle(font, Color.BLACK);
        playLabel = new Label("Play", labelStyle);
        playLabel.setFontScale(2);

        buttonStyle = new TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.BLACK;
        buttonStyle.font.setScale(2.0f);

        skins = new Skin();
        skins.add("playButton_unpressed", Assets.getInstance().getPlayButton_unpressed());
        skins.add("scoreButton_unpressed", Assets.getInstance().getScore_button_unpressed());
        skins.add("howToButton_unpressed", Assets.getInstance().getHow_to_button_unpressed());

        playButtonStyle = new TextButtonStyle();
        playButtonStyle.up = skins.getDrawable("playButton_unpressed");
        playButtonStyle.font = font;
        playButton = new TextButton("", playButtonStyle);
        playButton.setPosition((viewPort.getWorldWidth() / 2) - (playButton.getWidth() / 2), (viewPort.getWorldHeight() / 2) + playButton.getHeight() + 120);

        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game, handler));
                return true;
            }
        });
        stage.addActor(playButton);

        scoreButtonStyle = new TextButtonStyle();
        scoreButtonStyle.up = skins.getDrawable("scoreButton_unpressed");
        scoreButtonStyle.font = font;
        scoreButton = new TextButton("", scoreButtonStyle);
        scoreButton.setPosition((viewPort.getWorldWidth() / 2) - (scoreButton.getWidth() / 2), (viewPort.getWorldHeight() / 2) + scoreButton.getHeight()- 160);
        scoreButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
                game.setScreen(new ScoreScreen(game, handler));
                return true;
            }
        });
        stage.addActor(scoreButton);

        howToButtonStyle = new TextButtonStyle();
        howToButtonStyle.up = skins.getDrawable("howToButton_unpressed");
        howToButtonStyle.font = font;

        howToPlayButton = new TextButton("", howToButtonStyle);
        howToPlayButton.setPosition((viewPort.getWorldWidth() / 2) - (howToPlayButton.getWidth() / 2), (viewPort.getWorldHeight() / 2) - 300);
        howToPlayButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
                game.setScreen(new HowToPlayScreen(game, handler));

                return true;
            }
        });
        stage.addActor(howToPlayButton);

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
        stage.dispose();

    }
}
