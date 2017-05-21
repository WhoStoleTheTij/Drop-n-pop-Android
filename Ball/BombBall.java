package Ball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.richardsspot.game.Assets;

import screens.GameScreen;

/**
 * Created by Richard Hall on 01/05/15.
 */
public class BombBall extends Ball {

    private MoveToAction mta;
    private ScaleByAction sba;

    private Sound bombExplosion;

    private float elapsedTime;
    private float xPosition;

    private boolean touched = false;

    protected TextureRegion[] animationFrames;
    protected Texture texture;

    protected Animation animation;


    protected float scale;

    public Rectangle bounds;

    //sound for standard and bonus ball popping
    public Sound ballPop;


    public BombBall(float xPosition) {
        texture = Assets.getInstance().getBombTexture();
        this.xPosition = xPosition;
        this.bounds = new Rectangle();
        this.setOrigin(0.0f, 0.0f);
        this.setZIndex(50);
        this.setBounds(this.xPosition, Assets.WORLD_HEIGHT + 20, texture.getWidth() / 2, (this.texture.getHeight() / 2) * 1.5f);

        //split up the sprite sheet and add to the animationFrames array
        TextureRegion[][] tmpFrames = TextureRegion.split(texture, 111, 111);
        animationFrames = new TextureRegion[4];
        int index = 0;
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                animationFrames[index++] = tmpFrames[j][i];
            }
        }

        animation = new Animation(1f / 30f, animationFrames);
        animation.setPlayMode(Animation.PlayMode.NORMAL);

        this.ballPop = Assets.getInstance().getPopSound();
        bombExplosion = Assets.getInstance().getExplosionSound();

        this.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touched = true;
                GameScreen.addPoint(-10);
                return true;
            }
        });
    }


    public void launchBall(float ballNumber) {
        mta = new MoveToAction();
        mta.setPosition(this.xPosition, 0f);
        mta.setDuration(ballNumber);
        this.addAction(mta);

        sba = new ScaleByAction();
        sba.setAmount(this.scale, this.scale);
        sba.setDuration(0f);
        this.addAction(sba);
    }

    @Override
    public void act(float delta){
        super.act(delta);
        if(this.getY() <= 30){
            touched = true;

        }
    }

    public void gameIsOver(){
        this.clearActions();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.setBounds(this.getX(), this.getY(), texture.getWidth() / 2, (this.texture.getHeight() / 2) * 1.5f);
        if(!touched){
            batch.draw(animationFrames[0], getX(), getY());

        }else{
            this.clearActions();
            elapsedTime += Gdx.graphics.getDeltaTime();
            batch.draw(animation.getKeyFrame(elapsedTime), this.getX(), this.getY());
            if (animation.isAnimationFinished(elapsedTime)){
                bombExplosion.play();
                this.remove();
                GameScreen.ballTouched();
            }
        }

    }

    public void dispose(){
        texture.dispose();
    }
}
