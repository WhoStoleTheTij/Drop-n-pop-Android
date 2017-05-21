package Ball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.richardsspot.game.Assets;

import screens.GameScreen;

import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Richard Hall on 01/05/15.
 */
public class StandardBall extends Ball{


    private MoveToAction mta;

    private float elapsedTime;

    private boolean touched = false;
    private boolean gameOver;

    protected TextureRegion[] animationFrames;
    protected Texture texture;

    protected Animation animation;

    protected float xPosition;
    protected float scale;

    public Rectangle bounds;


    //sound for standard and bonus ball popping
    public Sound ballPop;

    /**
     *
     * @param xPosition - x position of the ball
     */
    public StandardBall(float xPosition){
        this.xPosition = xPosition;
        this.ballPop = Assets.getInstance().getPopSound();
        this.gameOver = false;
        this.bounds = new Rectangle();
        this.setOrigin(0.0f, 0.0f);

        Random rand = new Random();
        switch(rand.nextInt(3)){
            case 0:
                texture = Assets.getInstance().getGreenTexture();
                break;
            case 1:
                texture = Assets.getInstance().getPurpleTexture();
                break;
            case 2:
                texture = Assets.getInstance().getBlueTexture();
                break;
            default:
                texture = Assets.getInstance().getGreenTexture();
        }
        this.setZIndex(50);
        this.setTouchable(Touchable.enabled);
        //this.setDebug(true);
        this.setPosition(xPosition, Assets.WORLD_HEIGHT + 20);
        this.setBounds(this.xPosition, Assets.WORLD_HEIGHT + 20, this.texture.getWidth() / 2, (this.texture.getHeight() / 2) * 1.5f);

        //split up the sprite sheet and add to the animationFrames array
        TextureRegion[][] tmpFrames = TextureRegion.split(texture, 111, 111);
        animationFrames = new TextureRegion[4];
        int index = 0;
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                animationFrames[index++] = tmpFrames[j][i];
            }
        }

        animation = new Animation(1f/30f, animationFrames);
        animation.setPlayMode(Animation.PlayMode.NORMAL);

        this.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                StandardBall.this.removeAction(mta);
                touched = true;
                return true;
            }
        });


    }

    @Override
    public void act(float delta){
        super.act(delta);

        if(this.getY() <= 30){
            this.clearActions();
            GameScreen.gameOver();

        }

    }

    public void gameIsOver(){
        this.gameOver = true;
        this.setTouchable(Touchable.disabled);
        this.clearActions();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setBounds(this.getX(), this.getY(), this.texture.getWidth() / 2, (this.texture.getHeight() / 2) * 1.5f);
        if(!touched){
            batch.draw(animationFrames[0], getX(), getY());

        }else{
            this.clearActions();
            elapsedTime += Gdx.graphics.getDeltaTime();
            batch.draw(animation.getKeyFrame(elapsedTime), this.getX(), this.getY());
            if (animation.isAnimationFinished(elapsedTime)){
                if(touched){
                    GameScreen.ballTouched();
                    GameScreen.addPoint(1);
                    this.remove();
                    ballPop.play();
                    touched = false;
                }
            }
        }


    }

    public void launchBall(float ballNumber){
        mta = new MoveToAction();
        mta.setPosition(this.xPosition, -20f);
        mta.setDuration(ballNumber);
        this.addAction(mta);
    }

    public void dispose(){
        texture.dispose();
    }

}
