package com.dartapp.service;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dartapp.AddPlayer;
import com.dartapp.Home;
import com.dartapp.R;
import com.dartapp.adapters.HintAdapter;
import com.dartapp.database.DatabaseHelper;
import com.dartapp.model.Player;

import java.util.List;

public class UpdatePlayer extends AppCompatActivity {

    Spinner spPlayerList;

    EditText etUpdateName, etUpdateFavDoubles;

    ImageButton btnUpdateData, btnBackToAddPlayer;

    DatabaseHelper playersDb;

    ArrayAdapter<String> adapter1;
    List<String> playerNames;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_player);

        playersDb = new DatabaseHelper(this);
        spPlayerList = (Spinner) findViewById(R.id.spPlayerList);
        btnUpdateData = (ImageButton) findViewById(R.id.btnUpdateData);
        btnBackToAddPlayer = (ImageButton) findViewById(R.id.btnBackToAddPlayer);
        etUpdateName = (EditText) findViewById(R.id.etUpdateName);
        etUpdateFavDoubles = (EditText) findViewById(R.id.etUpdateFavDoubles);

        List<Player> playerList = playersDb.getAllPlayers();
        playerNames = AddPlayer.getPlayerNames(playerList);

        playerNames.add(0, "Choose Player");

        adapter1 = new HintAdapter(this, android.R.layout.simple_spinner_item, playerNames);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPlayerList.setAdapter(adapter1);

        spPlayerList.setPrompt("Choose Player");
        etUpdateName.setText("");
        etUpdateFavDoubles.setText("");

        spPlayerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedPlayerName = (String) parentView.getItemAtPosition(position);

                if (position != 0) {
                    Player selectedPlayer = playersDb.getPlayerByName(selectedPlayerName);

                    if (selectedPlayer != null) {
                        etUpdateName.setText(selectedPlayer.getName());
                        etUpdateFavDoubles.setText(selectedPlayer.getFavouriteDoubles());
                    } else {
                        etUpdateName.setText("");
                        etUpdateFavDoubles.setText("");
                    }
                } else {
                    etUpdateName.setText("");
                    etUpdateFavDoubles.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });



        updateData();
        backToAddPlayer();

    }

    public void updateData() {
        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedPlayerName = (String) spPlayerList.getSelectedItem();
                if (!selectedPlayerName.equals("Choose Player")) {
                    Player selectedPlayer = playersDb.getPlayerByName(selectedPlayerName);
                    if (selectedPlayer != null) {
                        boolean update = playersDb.updateData(selectedPlayer.getId(),
                                etUpdateName.getText().toString(),
                                etUpdateFavDoubles.getText().toString());
                        if (update) {
                            Toast.makeText(UpdatePlayer.this, "Successfully updated " + etUpdateName.getText().toString(), Toast.LENGTH_LONG).show();
                            refreshPage();
                            updateSpinnerItem(selectedPlayer.getName(), etUpdateName.getText().toString());
                        } else {
                            Toast.makeText(UpdatePlayer.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(UpdatePlayer.this, "Selected player not found", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(UpdatePlayer.this, "Please select a player to update.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void backToAddPlayer() {
        btnBackToAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdatePlayer.this, AddPlayer.class);
                startActivity(intent);
            }
        });
    }

    private void refreshPage() {
        String playerName = etUpdateName.getText().toString();
        Player updatedPlayer = playersDb.getPlayerByName(playerName);
        if (updatedPlayer != null) {
            etUpdateName.setText(updatedPlayer.getName());
            etUpdateFavDoubles.setText(updatedPlayer.getFavouriteDoubles());
        } else {
            etUpdateName.setText("");
            etUpdateFavDoubles.setText("");
        }
    }

    private void updateSpinnerItem(String oldName, String newName) {
        int index = playerNames.indexOf(oldName);
        if (index != -1) {
            playerNames.set(index, newName);
            // Update the spinner adapter if needed
            adapter1.notifyDataSetChanged();
        }
    }

}
