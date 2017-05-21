package imageActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.richardsspot.game.Assets;

import java.util.Random;

/**
 * Created by Richard Hall on 23/04/15.
 */
public class Background extends Image {

    private Sprite sprite;

    public Background(boolean menu){
        if(menu){
            //change to the menu sprite
            sprite = new Sprite(Assets.getInstance().getMenuBackground());
        }else{
            sprite = new Sprite(Assets.getInstance().getBackground());
        }

        this.setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        this.setTouchable(Touchable.disabled);
        this.setZIndex(0);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, 0, 0);
    }

}
