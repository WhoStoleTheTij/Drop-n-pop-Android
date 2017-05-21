package com.richardsspot.game;

/**
 * Created by Richard Hall on 06/06/15.
 */
public class PlayerScore implements Comparable<PlayerScore>{

    private int score;

    public PlayerScore(int score){
        this.score = score;
    }

    @Override
    public int compareTo(PlayerScore ps) {
        return Integer.compare(this.getScore(), ps.getScore());
    }

    public int getScore(){ return this.score;}
}
