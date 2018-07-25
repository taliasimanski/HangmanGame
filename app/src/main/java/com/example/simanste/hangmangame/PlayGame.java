package com.example.simanste.hangmangame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PlayGame extends AppCompatActivity {

    public Hangman hm;
    public int misses = 0;
    public ArrayList<String> guessList = new ArrayList<>();
    public static ArrayList<String> phraseList = new ArrayList<>();
    public StringBuilder hiddenPhrase = new StringBuilder();
    public String phrase;
    public String guess;
    public String playerID;
    public GamesRecord gameRec = new GamesRecord();

    //components
    TextView playerLabel;
    ImageView imageView;
    TextView hiddenPhraseLabel;
    TextView updateLabel;
    EditText guessTextBox;
    Button submitButton;
    Button playAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        //initialize components
        playerLabel = findViewById(R.id.playerLabel);
        imageView = findViewById(R.id.imageView);
        hiddenPhraseLabel = (TextView) findViewById(R.id.hiddenPhraseLabel);
        updateLabel = findViewById(R.id.updateLabel);
        guessTextBox = findViewById(R.id.guessTextBox);
        submitButton = findViewById(R.id.submitButton);

        //set labels and image
        Intent intent = getIntent();
        playerID = intent.getStringExtra(MainActivity.EXTRA_ID);
//        Bundle bundle = getIntent().getExtras();
//        assert bundle != null;
//        gameRec = (GamesRecord) bundle.getParcelable(MainActivity.EXTRA_GAME_REC);
        String displayPlayer = "Player: " + playerID;
        playerLabel.setText(displayPlayer);
        imageView.setImageResource(R.drawable.stand);

        //create player and phrase list
        hm = new Hangman(playerID,misses);
        readPhrases();

        //add short description
        String rules = "1 letter = 1 guess"+"\n"+"Solve the puzzle before the Hangman is complete and you win!"+"\n"+"The category is:" +"\n" + "TV SHOWS ON NETFLIX" + "\n" + "Good Luck!";
        updateLabel.setText(rules);

        //get puzzle
        phrase = hm.randomPhrase(phraseList);
        hiddenPhrase = hm.generateHiddenPhrase(phrase);

        //display puzzle
        hiddenPhraseLabel.setText(hiddenPhrase.toString());
    }


    public void readPhrases (){
        try {
            InputStream stream = getAssets().open("phrases.txt");
            BufferedReader breader = new BufferedReader(new InputStreamReader(stream));
            String line = breader.readLine();
            while ( line != null){
                phraseList.add(line);
                line = breader.readLine();
            }
        } catch (IOException e) {
            updateLabel.setText(e.toString());
        }
    }


    public void checkMisses (){
        //checks if missed
        if (!phrase.contains(guess.toUpperCase())){
            misses++;
            String wrongGuess = "Sorry, " + Character.toUpperCase(guess.charAt(0)) + " is not in the word/phrase." + "\n" + "Please guess again.";
            updateLabel.setText(wrongGuess);
        }
        //changes hangman image
        if (misses == 1){
            imageView.setImageResource(R.drawable.head);
        } else if (misses == 2){
            imageView.setImageResource(R.drawable.torso);
        } else if (misses == 3){
            imageView.setImageResource(R.drawable.rightarm);
        } else if (misses == 4){
            imageView.setImageResource(R.drawable.leftarm);
        } else if (misses == 5){
            imageView.setImageResource(R.drawable.rightleg);
        } else if (misses == 6){
            imageView.setImageResource(R.drawable.rightfoot);
        } else if (misses == 7){
            imageView.setImageResource(R.drawable.leftleg);
        }
        //losing condition
        else if (misses == 8){
            imageView.setImageResource(R.drawable.fullbody);
            guessTextBox.setVisibility(View.GONE);
            submitButton.setVisibility(View.GONE);
            gameRec.add(hm);
            String gameOver = "GAME OVER" + "\n" + "The secret phrase was: " + phrase;
            updateLabel.setText(gameOver);
            guessList.clear();

        }
        //winning condition
        if(!hiddenPhrase.toString().contains("-")){
            guessTextBox.setVisibility(View.GONE);
            submitButton.setVisibility(View.GONE);
//            gameRec.add(hm);
//            String stats = "STATS:"+"\n"+"Average misses per game: "+gameRec.average(playerID);
            String congrats = "Congratulations, " + playerID.toUpperCase() + "! You won!";
            updateLabel.setText(congrats);
//            playAgainButton.setVisibility(View.VISIBLE);
            guessList.clear();
        }
    }


    public void onSubmitButtonClick(View view) {
        updateLabel.setText("");

        //makes sure user entered something
        if (guessTextBox.getText().toString().isEmpty()){
            String oops = "Oops! You forgot to enter a guess! Please try again.";
            updateLabel.setText(oops);
            return;
        }

        //get guess
        guess = guessTextBox.getText().toString();

        //input validation
        String oops = "";
        if (!Character.isLetter(guess.charAt(0))){
            oops = "Oops! That's not a valid guess! Please enter a letter.";
            updateLabel.setText(oops);
            guessTextBox.setText("");
            return;
        }
        if (guessList.contains(guess)){
            oops = "Oops! You've already guessed that! Please enter a new guess.";
            updateLabel.setText(oops);
            guessTextBox.setText("");
            return;
        }

        //add guess to list of previous guesses
        guessList.add(guess);
        //clear the text box
        guessTextBox.setText("");

        //process guess & display updated phrase
        hm.processGuess(phrase,hiddenPhrase,guess.toUpperCase());
        hiddenPhraseLabel.setText(hiddenPhrase.toString());

        //checks misses/win/lose
        checkMisses();
    }

    public void playAgainButtonClick(View view) {
        Intent refresh = new Intent(this, PlayGame.class);
        finish(); //finish Activity.
        startActivity(refresh);//Start the same Activity

    }
}
