package com.dartapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dartapp.database.DatabaseHelper;
import com.dartapp.adapters.HintAdapter;
import com.dartapp.model.Player;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    Button button1;
    Spinner spinnerPlayer1, spinnerPlayer2;
    ImageButton addPlayerButton;

    DatabaseHelper playersDb;

    String choosePlayer = "Choose Player";

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        playersDb = new DatabaseHelper(this);

        button1 = findViewById(R.id.button_1id);
        addPlayerButton = findViewById(R.id.btnAddPlayer);
        spinnerPlayer1 = findViewById(R.id.spinnerPlayer1);
        spinnerPlayer2 = findViewById(R.id.spinnerPlayer2);

        List<Player> playerList = playersDb.getAllPlayers();
        List<String> playerNames = AddPlayer.getPlayerNames(playerList);

        playerNames.add(0, choosePlayer);

        ArrayAdapter<String> adapter1 = new HintAdapter(this, android.R.layout.simple_spinner_item, playerNames);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlayer1.setAdapter(adapter1);

        spinnerPlayer1.setPrompt(choosePlayer);

        // Use the custom adapter for spinnerPlayer2
        ArrayAdapter<String> adapter2 = new HintAdapter(this, android.R.layout.simple_spinner_item, playerNames);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlayer2.setAdapter(adapter2);

        // Set a hint for spinnerPlayer2
        spinnerPlayer2.setPrompt(choosePlayer);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName1 = spinnerPlayer1.getSelectedItem().toString();
                String playerName2 = spinnerPlayer2.getSelectedItem().toString();

                // Check if the selected players are not the default "Choose Player"
                if (!playerName1.equals(choosePlayer) && !playerName2.equals(choosePlayer)) {
                    Intent intent = new Intent(Home.this, GameChoose.class);
                    intent.putExtra("PLAYER_NAME", playerName1);
                    intent.putExtra("PLAYER_NAME2", playerName2);
                    startActivity(intent);
                } if (!playerName1.equals(choosePlayer) && playerName2.equals(choosePlayer)) {
                    Intent intent = new Intent(Home.this, GameChoose.class);
                    intent.putExtra("PLAYER_NAME", playerName1);
                    startActivity(intent);
                } if (playerName1.equals(choosePlayer) && !playerName2.equals(choosePlayer)) {
                    Intent intent = new Intent(Home.this, GameChoose.class);
                    intent.putExtra("PLAYER_NAME2", playerName2);
                    startActivity(intent);
                }else {
                    // Handle the case where the user hasn't selected valid players
                    Toast.makeText(Home.this, "Please select valid players", Toast.LENGTH_SHORT).show();
                }
            }
        });


        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, AddPlayer.class);
                startActivity(intent);
            }
        });
    }

}