package imageActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.richardsspot.game.Assets;

/**
 * Created by Richard Hall on 25/04/15.
 */
public class Score extends Image {

    private Sprite sprite;
    private int height, width;

    public Score(int width, int height){
        sprite = new Sprite(Assets.getInstance().getScoreImage());
        this.setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        this.setTouchable(Touchable.disabled);
        this.setZIndex(200);
        this.height = height;
        this.width = width;

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, (width - sprite.getWidth() - 40), (height - sprite.getHeight() - 30));
    }

}
