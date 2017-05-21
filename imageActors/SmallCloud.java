package imageActors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.richardsspot.game.Assets;

/**
 * Created by Richard Hall on 23/07/15.
 */
public class SmallCloud extends Image {

    private Sprite sprite;
    private float yPos;

    private MoveToAction mta;

    public SmallCloud(float yPosition){
        sprite = new Sprite(Assets.getInstance().getSmallCloud());
        this.yPos = yPosition;
        this.setOrigin(0.0f, 0.0f);
        this.setPosition(-(sprite.getTexture().getWidth() + 10), yPosition);
        this.setBounds(this.getX(), this.getY(), sprite.getWidth() / 2, sprite.getHeight() / 2);
        this.setTouchable(Touchable.disabled);
        this.setZIndex(10);

    }

    public void resetCloud(){
        this.setPosition(-(sprite.getTexture().getWidth() + 10), this.yPos);
    }

    public void moveCloud(){
        mta = new MoveToAction();
        mta.setPosition(Assets.WORLD_WIDTH + 20, this.yPos);
        mta.setDuration(80.0f);
        this.addAction(mta);

    }

    @Override
    public void act(float delta){
        super.act(delta);
        if(this.getX() >= Assets.WORLD_WIDTH + 10){
            this.removeAction(mta);
            this.resetCloud();
            this.moveCloud();
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        this.setBounds(this.getX(), this.getY(), sprite.getWidth() / 2, sprite.getHeight() / 2);
        batch.draw(sprite, this.getX(), this.getY());


    }


    public void dispose(){
        sprite.getTexture().dispose();
    }
}
