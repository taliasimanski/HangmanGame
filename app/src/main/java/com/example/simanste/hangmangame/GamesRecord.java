package com.example.simanste.hangmangame;

import java.util.*;
import java.lang.*;

public class GamesRecord {
    private ArrayList<Game> gamesRecord = new ArrayList<Game>();
    private ArrayList<Game> highScores = new ArrayList<Game>();

    public void add(Game game){
        gamesRecord.add(game);
    }
    public float average(){
        int sum = 0;
        for (Game game : gamesRecord){
            sum += game.getScore();
        }
        System.out.println("Total games played: " + gamesRecord.size());
        return sum / gamesRecord.size();
    }

    public double average(String playerID){
        int sum = 0;
        int total = 0;
        for (Game game : gamesRecord){
            if (game.getPlayerID().equals(playerID)){
                sum += game.getScore();
                total++;
            }
        }
        return sum / total;
    }

    public String highGameList(int n) {
        //sort low to high
        Collections.sort(gamesRecord);
        //create topscore list
        for (int i=0; i < n; i++){
            highScores.add(gamesRecord.get(i));
        }
        //display topscores
        String s = "";
        for (int i=0; i < highScores.size(); i++){
            s = "\n" + "player: " + highScores.get(i).getPlayerID().toUpperCase() + "\t" + "high score: " + highScores.get(i).getScore();
        }
        return s;
    }
}
