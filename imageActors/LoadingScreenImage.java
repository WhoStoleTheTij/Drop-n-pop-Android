package imageActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.richardsspot.game.Assets;

/**
 * Created by Richard Hall on 29/05/15.
 */
public class LoadingScreenImage extends Image {

    private Sprite sprite;

    public LoadingScreenImage(){
        sprite = new Sprite(Assets.getInstance().getLoadingImage());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, 0, 0);
    }
}
