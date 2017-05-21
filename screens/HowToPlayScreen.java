package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

import imageActors.Background;
import imageActors.HowToInstructions;

/**
 * Created by Richard Hall on 06/06/15.
 */
public class HowToPlayScreen implements Screen {

    private Game game;

    private final String INSTRUCTIONS = "Watch them drop, touch them to pop. Lookout for the bonus\n " +
            "and avoid the bombs. Add 1 point per ball, 3 for a bonus or lose 10 on a\n bomb. Don't let the balls reach the bottom or your score return to 0.";

    private final int WORLD_WIDTH = 1024;
    private final int WORLD_HEIGHT = 768;

    private Stage stage;
    private Viewport viewPort;
    private OrthographicCamera camera;

    private Background background;

    private TextButton backButton;
    private TextButton playButton;
    private TextButtonStyle playButtonStyle;
    private TextButtonStyle backButtonStyle;
    private BitmapFont font;
    private Skin skins;

    private static IActivityRequestHandler handler;

    public HowToPlayScreen(Game game, IActivityRequestHandler handler){
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

        this.setTheStage();

        Gdx.input.setInputProcessor(stage);

        skins = new Skin();
        skins.add("back_button_unpressed", Assets.getInstance().getBack_button_unpressed());
        skins.add("play_button_unpressed", Assets.getInstance().getPlayButton_unpressed());

        font = new BitmapFont();
        playButtonStyle = new TextButtonStyle();
        playButtonStyle.font = font;
        playButtonStyle.up = skins.getDrawable("back_button_unpressed");

        backButton = new TextButton("", playButtonStyle);
        backButton.setPosition(10, Assets.WORLD_HEIGHT - 180);
        backButton.setBounds(backButton.getX(), backButton.getY(), backButton.getWidth(), backButton.getHeight());
        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
                game.setScreen(new screens.MenuScreen(game, handler));
                return true;
            }
        });
        stage.addActor(backButton);

        backButtonStyle = new TextButtonStyle();
        backButtonStyle.font = font;
        backButtonStyle.up = skins.getDrawable("play_button_unpressed");

        playButton = new TextButton("", backButtonStyle);
        playButton.setPosition(Assets.WORLD_WIDTH - (playButton.getWidth() + 20), Assets.WORLD_HEIGHT - 180);
        playButton.setBounds(playButton.getX(), playButton.getY(), playButton.getWidth(), playButton.getHeight());
        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game, handler));
                return true;
            }
        });
        stage.addActor(playButton);
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


    private void setTheStage(){
        //add the background
        background = new Background(true);
        stage.addActor(background);

        HowToInstructions hti = new HowToInstructions();

        stage.addActor(hti);

        BitmapFont scoreFont = new BitmapFont(Gdx.files.internal("scores_font.fnt"), Gdx.files.internal("scores_font_0.png"), false);
        LabelStyle style = new LabelStyle();
        style.font = scoreFont;
        style.fontColor = Color.BLACK;
        Label label = new Label(INSTRUCTIONS, style);
        label.setOrigin(0, 0);
        label.setAlignment(Align.center);
        label.setPosition((Assets.WORLD_WIDTH / 2) - (label.getWidth() / 2), 180);

        stage.addActor(label);

    }

}
