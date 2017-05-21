package com.richardsspot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;



/**
 * Created by Richard Hall on 04/06/15.
 */
public class Assets {

    private static boolean menuPlaying, gamePlaying;

    private static Assets asset = null;

    public static Texture greenTexture;
    public static Texture blueTexture;
    public static Texture purpleTexture;
    public static Texture bombTexture;
    public static Texture bonusTexture;
    public static Texture groundSpikes;
    public static Texture scoreImage;
    public static Texture loadingImage;

    public static Texture how_to_instructions;

    public static Texture menuBackground;
    public static Texture background;

    public static Texture playButton_unpressed;
    public static Texture score_button_unpressed;
    public static Texture how_to_button_unpressed;
    public static Texture back_button_unpressed;
    public static Texture replay_button_unpressed;
    public static Texture menu_button_unpressed;
    public static Texture credit_button_unpressed;

    public static Texture smallCloud;
    public static Texture largeCloud;

    public static Sound popSound;
    public static Sound explosionSound;

    public static Music menuSound;
    public static Music gameSound;

    public static final int WORLD_WIDTH = 2048;
    public static final int WORLD_HEIGHT = 1536;


    public static Texture getBonusTexture(){
        return bonusTexture;
    }

    public static Texture getBombTexture(){
        return bombTexture;
    }

    public static Sound getExplosionSound(){
        return explosionSound;
    }

    public static Sound getPopSound(){
        return popSound;
    }

    public static Texture getPurpleTexture(){
        return purpleTexture;
    }

    public static Texture getGreenTexture(){
        return greenTexture;
    }

    public static Texture getBlueTexture() { return blueTexture; }

    public static Texture getBackground() { return background; }

    public static Texture getMenuBackground() { return menuBackground; }

    public static Texture getHow_to_instructions() { return how_to_instructions; }

    public static Texture getGroundSpikes() { return groundSpikes; }

    public static Texture getScoreImage() { return scoreImage; }

    public static Texture getLoadingImage() { return loadingImage; }

    public static Texture getPlayButton_unpressed() { return playButton_unpressed; }

    public static Texture getScore_button_unpressed() { return score_button_unpressed; }

    public static Texture getHow_to_button_unpressed() { return how_to_button_unpressed;}

    public static Texture getBack_button_unpressed() { return back_button_unpressed; }

    public static Texture getReplay_button_unpressed() { return replay_button_unpressed; }

    public static Texture getMenu_button_unpressed() { return menu_button_unpressed; }

    public static Texture getCredit_button_unpressed() { return credit_button_unpressed; }

    public static Texture getSmallCloud() { return smallCloud;}

    public static Texture getLargeCloud() { return largeCloud; }

    public static void loadAssets(){
        greenTexture = new Texture(Gdx.files.internal("standard_1_sprite_sheet.png"));
        purpleTexture = new Texture(Gdx.files.internal("standard_2_sprite_sheet.png"));
        blueTexture = new Texture(Gdx.files.internal("standard_3_sprite_sheet.png"));

        bombTexture = new Texture(Gdx.files.internal("bomb_sprite_sheet.png"));
        bonusTexture = new Texture(Gdx.files.internal("bonus_sprite_sheet.png"));

        groundSpikes = new Texture(Gdx.files.internal("spikes-android.png"));
        scoreImage = new Texture(Gdx.files.internal("score.png"));
        loadingImage = new Texture(Gdx.files.internal("loading-android.png"));

        how_to_instructions = new Texture(Gdx.files.internal("how-to-instructions.png"));

        menuBackground = new Texture(Gdx.files.internal("menu_background-android.png"));
        background = new Texture(Gdx.files.internal("cloudless_background.png"));

        smallCloud = new Texture(Gdx.files.internal("small_cloud.png"));
        largeCloud = new Texture(Gdx.files.internal("large_cloud.png"));

        playButton_unpressed = new Texture(Gdx.files.internal("play_button.png"));
        score_button_unpressed = new Texture(Gdx.files.internal("score_button.png"));
        how_to_button_unpressed = new Texture(Gdx.files.internal("how_to_button.png"));
        back_button_unpressed = new Texture(Gdx.files.internal("back_button.png"));
        replay_button_unpressed = new Texture(Gdx.files.internal("replay_button.png"));
        menu_button_unpressed = new Texture(Gdx.files.internal("menu_button.png"));
        credit_button_unpressed = new Texture(Gdx.files.internal("credit_button.png"));

        popSound = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.mp3"));

        menuSound = Gdx.audio.newMusic(Gdx.files.internal("Game-Menu_v001.mp3"));
        gameSound = Gdx.audio.newMusic(Gdx.files.internal("Whimsical-Popsicle.mp3"));
        menuPlaying = false;
        gamePlaying = false;

    }

    public static void playMenuSound(){
        if(gamePlaying){
            gameSound.stop();
            gamePlaying = false;
        }
        menuPlaying = true;
        menuSound.setLooping(true);
        menuSound.play();

    }

    public static void playGameSound(){
        if(menuPlaying){
            menuSound.stop();
            menuPlaying = false;
        }
        gamePlaying = true;
        gameSound.setLooping(true);
        gameSound.play();

    }

    public static Assets getInstance(){
        if(asset == null){
            asset = new Assets();
        }
        return asset;
    }
}
