package Ball;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Richard Hall on 23/04/15.
 */
public abstract class Ball extends Actor{


    public abstract void launchBall(float duration);
    public abstract void gameIsOver();
    public abstract void dispose();

}
