package com.dartapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.dartapp.model.Player;
import com.dartapp.service.AddScore;
import com.dartapp.service.DartDoubleOut;
import com.dartapp.service.UpdateDoubleCheckBox;
import com.dartapp.service.UpdateTripleCheckBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameScreen extends AppCompatActivity {

    TextView gameTv, playerTv, game2Tv, player2Tv, possibleOuts;
    EditText dart1, dart2, dart3;
    CheckBox dart1Double, dart1Triple, dart2Double, dart2Triple, dart3Double, dart3Triple;
    List<CheckBox> checkBoxes = new ArrayList<>();;
    Button addScore;
    LinearLayout player2Layout;
    Player player1 = new Player();
    Player player2 = new Player();
    AddScore addScoreService = new AddScore();
    DartDoubleOut dartDoubleOut = new DartDoubleOut();

    int playerOneCurrentScore;
    int playerTwoCurrentScore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        initializeViews();
        setupGame(player1, player2);
    }

    private void initializeViews() {
        gameTv = findViewById(R.id.game);
        playerTv = findViewById(R.id.player);
        game2Tv = findViewById(R.id.game2);
        player2Tv = findViewById(R.id.player2);
        possibleOuts = findViewById(R.id.player1PossibleOuts);
        addScore = findViewById(R.id.addScore);
        player2Layout = findViewById(R.id.linearLayoutPlayer2);

        dart1 = findViewById(R.id.dart1);
        dart1Double = findViewById(R.id.dart1BoxDouble);
        dart1Triple = findViewById(R.id.dart1BoxTriple);

        dart2 = findViewById(R.id.dart2);
        dart2Double = findViewById(R.id.dart2BoxDouble);
        dart2Triple = findViewById(R.id.dart2BoxTriple);

        dart3 = findViewById(R.id.dart3);
        dart3Double = findViewById(R.id.dart3BoxDouble);
        dart3Triple = findViewById(R.id.dart3BoxTriple);

        checkBoxes = Arrays.asList(dart1Double, dart2Double, dart3Double,
                                    dart1Triple, dart2Triple, dart3Triple);

    }

    private void setupGame(Player player1, Player player2) {
        String player1Name = getIntent().getStringExtra("PLAYER_NAME");
        String player2Name = getIntent().getStringExtra("PLAYER_NAME2");


        player1.setName(player1Name);
        player2.setName(player2Name);

        player1.setTotal(playerOneCurrentScore = getIntent().getIntExtra("GAME", 0));
        player2.setTotal(playerTwoCurrentScore= getIntent().getIntExtra("GAME", 0));

        gameTv.setText(String.valueOf(playerOneCurrentScore));
        playerTv.setText(player1.getName());
        player1.setPlayerTurn(true);
        playerTv.setTextColor(ContextCompat.getColor(this, R.color.white));


        game2Tv.setText(String.valueOf(playerTwoCurrentScore));
        player2Tv.setTextColor(ContextCompat.getColor(this, R.color.grey));
        game2Tv.setTextColor(ContextCompat.getColor(this, R.color.grey));
        player2Tv.setText(player2.getName());
        possibleOuts.setVisibility(View.GONE);

        removePlayer2IfPlayingAlone();
        switchBetweenDoubleAndTripleCheckBox();
        addPossibleOutsTextView(playerOneCurrentScore, playerTwoCurrentScore);


        new UpdateDoubleCheckBox(dart1Double, dart1, dart1Triple);
        new UpdateDoubleCheckBox(dart2Double, dart2, dart2Triple);
        new UpdateDoubleCheckBox(dart3Double, dart3, dart3Triple);
        new UpdateTripleCheckBox(dart1Triple, dart1, dart1Double);
        new UpdateTripleCheckBox(dart2Triple, dart2, dart2Double);
        new UpdateTripleCheckBox(dart3Triple, dart3, dart3Double);

        setupButtonClick(player2Name);

    }

    private void setupButtonClick(String player2Name) {
        if (player2Name != null && !player2Name.isEmpty()) {
            addScore.setOnClickListener(v -> {
                if (player1.isPlayerTurn()) {
                    playerOneCurrentScore = addScoreService.addScore(playerOneCurrentScore, checkBoxes, dart1, dart2, dart3);
                    gameTv.setText(String.valueOf(playerOneCurrentScore));
                    player1.setPlayerTurn(false);
                    addScoreService.setBackGroundColourAfterAddScore(this, playerTv, player2Tv, gameTv, game2Tv);
                    addPossibleOutsTextView(playerOneCurrentScore, playerTwoCurrentScore);
                } else {
                    playerTwoCurrentScore = addScoreService.addScore(playerTwoCurrentScore,checkBoxes, dart1, dart2, dart3);
                    game2Tv.setText(String.valueOf(playerTwoCurrentScore));
                    player1.setPlayerTurn(true);
                    addScoreService.setBackGroundColourAfterAddScore(this, player2Tv, playerTv, game2Tv, gameTv);
                    addPossibleOutsTextView(playerOneCurrentScore, playerTwoCurrentScore);
                }
            });
        } else {
            addScore.setOnClickListener(v -> {
                playerOneCurrentScore = addScoreService.addScore(playerOneCurrentScore,checkBoxes, dart1, dart2, dart3);
                gameTv.setText(String.valueOf(playerOneCurrentScore));
                addPossibleOutsTextView(playerOneCurrentScore, playerTwoCurrentScore);
            });
        }

    }

    private void removePlayer2IfPlayingAlone() {
        if (player2.getName() == null || player2.getName().isEmpty()) {
            player2Layout.setVisibility(View.GONE);
            player2Tv.setVisibility(View.GONE);
            game2Tv.setVisibility(View.GONE);
        }
    }

    private void switchBetweenDoubleAndTripleCheckBox() {
        dart1Double.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dart1Triple.setChecked(false);
            }
        });

        dart1Triple.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dart1Double.setChecked(false);
            }
        });
    }

    private void addPossibleOutsTextView(int p1Score, int p2Score) {
        if (p1Score <= 170 || p2Score <= 170) {
            possibleOuts.setVisibility(View.VISIBLE);
        } else {
            possibleOuts.setVisibility(View.GONE);
        }
    }


}