package com.dartapp.service;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.dartapp.R;

import java.util.List;

public class AddScore extends AppCompatActivity {

    public int addScore(int currentScore, List<CheckBox> checkBoxes,
                        EditText dart1, EditText dart2, EditText dart3) {
        String dart1Text = dart1.getText().toString();
        String dart2Text = dart2.getText().toString();
        String dart3Text = dart3.getText().toString();

        int dart1Value = Integer.parseInt(dart1Text.isEmpty() ? "0" : dart1Text);
        int dart2Value = Integer.parseInt(dart2Text.isEmpty() ? "0" : dart2Text);
        int dart3Value = Integer.parseInt(dart3Text.isEmpty() ? "0" : dart3Text);
        int total = dart1Value + dart2Value + dart3Value;

        resetCheckBoxes(checkBoxes);
        dart1.getText().clear();
        dart2.getText().clear();
        dart3.getText().clear();


        return currentScore - total;
    }

    public void setBackGroundColourAfterAddScore(Context context, TextView playing, TextView notPlaying,
                                                 TextView playingScore, TextView notPlayingScore) {
        playing.setTextColor(ContextCompat.getColor(context, R.color.grey));
        playingScore.setTextColor(ContextCompat.getColor(context, R.color.grey));
        notPlaying.setTextColor(ContextCompat.getColor(context, R.color.white));
        notPlayingScore.setTextColor(ContextCompat.getColor(context, R.color.white));
    }

    private void resetCheckBoxes(List<CheckBox> checkBoxes) {
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setChecked(false);
        }
    }

}
