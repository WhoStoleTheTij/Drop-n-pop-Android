package imageActors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.richardsspot.game.Assets;

/**
 * Created by Richard Hall on 24/06/15.
 */
public class HowToInstructions extends Image {

    private Sprite sprite;

    public HowToInstructions(){
        sprite = new Sprite(Assets.getInstance().getHow_to_instructions());
        sprite.setOrigin(sprite.getWidth() / 2, 0);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, Assets.WORLD_WIDTH / 4, Assets.WORLD_HEIGHT / 3);
    }
}
