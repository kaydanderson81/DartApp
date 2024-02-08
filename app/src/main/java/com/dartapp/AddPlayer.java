package com.dartapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dartapp.adapters.PlayerAdapter;
import com.dartapp.database.DatabaseHelper;
import com.dartapp.model.Player;
import com.dartapp.service.UpdatePlayer;

import java.util.ArrayList;
import java.util.List;

public class AddPlayer extends AppCompatActivity {

    DatabaseHelper playersDb;

    ImageButton btnAddData, btnViewData, btnUpdateData, btnBackToHome;

    EditText etName, etFavDoubles;

    private PlayerAdapter playerAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_player);

        playersDb = new DatabaseHelper(this);

        etName = (EditText) findViewById(R.id.etNewName);
        etFavDoubles = (EditText) findViewById(R.id.etFavDoubles);
        btnAddData = (ImageButton) findViewById(R.id.btnAddData);
        btnViewData = (ImageButton) findViewById(R.id.btnViewData);
        btnUpdateData = (ImageButton) findViewById(R.id.btnUpdateData);
        btnBackToHome = (ImageButton) findViewById(R.id.btnBackToHome);

        addData();
        viewData();
        updateData();
        backToHome();
    }

    public void addData() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Player player = new Player();

                player.setName(etName.getText().toString());
                player.setFavouriteDoubles(etFavDoubles.getText().toString());

                boolean insertData = playersDb.addData(player);

                if (insertData) {
                    Toast.makeText(AddPlayer.this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddPlayer.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void viewData() {
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Player> playerList = playersDb.getAllPlayers();

                if (playerList.isEmpty()) {
                    display("Error", "No data found.");
                    return;
                }

                // Use RecyclerView and PlayerAdapter
                RecyclerView recyclerView = new RecyclerView(AddPlayer.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(AddPlayer.this));

                PlayerAdapter.OnItemClickListener itemClickListener = new PlayerAdapter.OnItemClickListener() {

                    @Override
                    public void onDeleteClick(Player player) {
                        int deleteRow = playersDb.deleteData(String.valueOf(player.getId()));
                        if (deleteRow > 0) {
                            Toast.makeText(AddPlayer.this, "Successfully deleted data.", Toast.LENGTH_LONG).show();
                            if (playerAdapter != null) {
                                List<Player> playerUpdatedList = playersDb.getAllPlayers();
                                System.out.println("Update: " + playerUpdatedList);
                                playerAdapter.updatePlayerList(playerUpdatedList);
                            } else {
                                Toast.makeText(AddPlayer.this, "Something went wrong again.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(AddPlayer.this, "Something went wrong.", Toast.LENGTH_LONG).show();
                        }
                    }
                };

                playerAdapter = new PlayerAdapter(playerList, itemClickListener);
                recyclerView.setAdapter(playerAdapter);

                AlertDialog.Builder builder = new AlertDialog.Builder(AddPlayer.this);
                builder.setView(recyclerView);
                builder.setTitle("All stored data");
                builder.setPositiveButton("OK", null); // You can add a positive button if needed
                builder.show();
            }
        });
    }

    public void display(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    public void updateData() {
        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPlayer.this, UpdatePlayer.class);
                startActivity(intent);
            }
        });
    }

    public void backToHome() {
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPlayer.this, Home.class);
                startActivity(intent);
            }
        });
    }

    public static List<String> getPlayerNames(List<Player> players) {
        List<String> playerNames = new ArrayList<>();
        for (Player player : players) {
            playerNames.add(player.getName());
        }
        return playerNames;
    }
}