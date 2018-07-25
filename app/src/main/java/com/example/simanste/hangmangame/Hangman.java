package com.example.simanste.hangmangame;

import java.util.*;
import java.lang.*;


public class Hangman extends Game {

    public Hangman(String playerID, int score){
        super(playerID,score);
    }

    public String randomPhrase(ArrayList<String> phraseList) {
        Random randPhrase = new Random();
        int n = randPhrase.nextInt(phraseList.size());
        String phrase = phraseList.get(n);

        return phrase.toUpperCase();
    }

    public StringBuilder generateHiddenPhrase(String phrase) {
        StringBuilder hiddenPhrase = new StringBuilder();
        int i = 0;
        while (i < phrase.length()) {
            if (phrase.charAt(i) != ' ') {
                hiddenPhrase.append("-");
            } else {
                hiddenPhrase.append(phrase.charAt(i));
            }
            i++;
        }
        return hiddenPhrase;
    }


    public StringBuilder processGuess(String phrase, StringBuilder hiddenPhrase, String guess) {
        int iP = 0;
        int ihP = 0;
        while (iP < phrase.length() && ihP < hiddenPhrase.length()) {
            if (phrase.charAt(iP) == guess.charAt(0)) {
                hiddenPhrase.setCharAt(ihP, guess.charAt(0));
            }
            iP++;
            ihP++;
        }
        return hiddenPhrase;
    }

    public int getScore(){
        return score;
    }

    public String getPlayerID(){
        return playerID;
    }

    @Override
    public int compareTo(Game o) {
        if(this.getScore()==o.getScore()){
            return 0;
        } else if(this.getScore() < o.getScore()){
            return -1;
        } else{
            return 1;
        }
    }
}

