package com.richardsspot.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Richard Hall on 08/06/15.
 */
public class ScoreManager{

    private static List<PlayerScore> scores;


    //check if a score is within the top ten
    public static void checkNewScore(int newScore){
        getScores();
        int lastPlace = 10;
        if(scores.size() == 10){
            if(newScore >= scores.get(0).getScore() && newScore > 0){
                PlayerScore ps = new PlayerScore(newScore);
                scores.remove(0);
                scores.add(ps);
                saveScores();
            }
        }else if(newScore > 0){
            PlayerScore ps = new PlayerScore(newScore);
            scores.add(ps);
            saveScores();
        }

    }

    //save the scores
    public static void saveScores(){

        FileHandle file = Gdx.files.local("scores.csv");
        if(scores.size() >= 0){
            file.writeString("", false);
            for(PlayerScore ps : scores){
                file.writeString(String.valueOf(ps.getScore()) + ",", true);
            }
        }
    }

    //get the scores
    public static void getScores(){
        scores = new ArrayList<PlayerScore>();
        BufferedReader br = null;
        FileHandle file = Gdx.files.local("scores.csv");
        br = file.reader(100);
        try {
            String line = br.readLine();
            String[] strings = null;
            while(line != null){
                strings = line.split(",");
                line = br.readLine();
            }
            if (strings != null) {
                for (String st : strings) {
                    PlayerScore ps = new PlayerScore(Integer.parseInt(st));
                    scores.add(ps);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Collections.sort(scores);
    }

    //check for score file
    public static void checkForScoreFile(){

        FileHandle f = Gdx.files.local("scores.csv");
        if(!f.exists()){
            try {
                f.file().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            getScores();
        }
    }

    public static List<PlayerScore>returnScores(){
        getScores();
        return scores;
    }
}
