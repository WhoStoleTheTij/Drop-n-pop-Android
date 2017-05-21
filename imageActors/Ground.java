package imageActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.richardsspot.game.Assets;

/**
 * Created by Richard Hall on 01/05/15.
 */
public class Ground extends Actor{

    private Sprite sprite;

    private Rectangle bounds;

    public Ground(){
        sprite = new Sprite(Assets.getInstance().getGroundSpikes());
        sprite.setSize(1024, 15);
        this.setTouchable(Touchable.disabled);
        this.setZIndex(50);
        bounds = new Rectangle();
        bounds.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, 0, 0);

    }

    public Rectangle getBounds(){
        return this.bounds;
    }
}
