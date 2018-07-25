package com.example.simanste.hangmangame;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.hangmangame.ID";
//    public static final String EXTRA_GAME_REC =  "com.example.hangmangame.GAME_REC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView title = (TextView) findViewById(R.id.title);
        String titleText = "HANGMAN";
        title.setText(titleText);
    }

    public void playGameButtonClick(View view) {
        Intent intent = new Intent(this, PlayGame.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        //create record
//        GamesRecord gameRec = new GamesRecord();
        //get playerID
        String playerID = editText.getText().toString().toUpperCase();
        intent.putExtra(EXTRA_ID, playerID);
//        intent.putExtra(EXTRA_GAME_REC, (Parcelable) gameRec);
        startActivity(intent);
    }
}
