package com.example.simanste.hangmangame;

import java.lang.Comparable;

public abstract class Game implements Comparable<Game>{
    public static String playerID;
    public static int score;

    public Game(String playerID, int score){
        Game.playerID = playerID;
        Game.score = score;
    }

    public abstract int getScore();

    public abstract String getPlayerID();

    @Override
    public int compareTo(Game o) {
        if(this.getScore()==o.getScore()){
            return 0;
        } else if(this.getScore() < o.getScore()){
            return 1;
        } else{
            return -1;
        }
    }
}
