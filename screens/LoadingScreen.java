package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.richardsspot.game.Assets;

import imageActors.LoadingScreenImage;

/**
 * Created by Richard Hall on 27/05/15.
 */
public class LoadingScreen implements Screen{


    private Stage stage;

    private Viewport viewPort;
    private OrthographicCamera camera;



    private LoadingScreenImage loadImage;

    public LoadingScreen(){
    }

    @Override
    public void show() {

        camera = new OrthographicCamera();
        viewPort = new StretchViewport(Assets.WORLD_WIDTH, Assets.WORLD_HEIGHT, camera);
        viewPort.apply();

        stage = new Stage();
        stage.setViewport(viewPort);
        viewPort.update((int) stage.getWidth(), (int) stage.getHeight(), true);

        loadImage = new LoadingScreenImage();
        stage.addActor(loadImage);
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
