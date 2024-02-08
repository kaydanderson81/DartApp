package com.dartapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameChoose extends AppCompatActivity {

    TextView textView;

    Button fiveZeroOne;
    Button threeZeroOne;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_choose);

        textView = findViewById(R.id.choose_game);

        String player1 = getIntent().getStringExtra("PLAYER_NAME");
        String player2 = getIntent().getStringExtra("PLAYER_NAME2");

        setPlayerNames(player1, player2);

        fiveZeroOne = findViewById(R.id.button_501);
        threeZeroOne = findViewById(R.id.button_301);

        fiveZeroOne.setOnClickListener(v -> {
            int fiveOOne = 501;
            Intent intent = new Intent(GameChoose.this, GameScreen.class);
            intent.putExtra("PLAYER_NAME", player1);
            intent.putExtra("PLAYER_NAME2", player2);
            intent.putExtra("GAME", fiveOOne);
            startActivity(intent);

        });

        threeZeroOne.setOnClickListener(v -> {
            int threeOOne = 301;
            Intent intent = new Intent(GameChoose.this, GameScreen.class);
            intent.putExtra("PLAYER_NAME", player1);
            intent.putExtra("PLAYER_NAME2", player2);
            intent.putExtra("GAME", threeOOne);
            startActivity(intent);

        });

    }

    private void setPlayerNames(String player1, String player2) {
        String title = player1 + " vs " + player2;

        if (player1!=null && (player2==null || player2.isEmpty())) {
            textView.setText(player1);
        } else if (player2!=null && (player1==null || player1.isEmpty())) {
            textView.setText(player2);
        } else {
            textView.setText(title);
        }
    }
}