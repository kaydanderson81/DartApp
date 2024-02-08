package com.dartapp.service;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class UpdateDoubleCheckBox {
    private int originalValue;
    private int storedOriginalValue;
    private final CheckBox correspondingTripleCheckBox;

    public UpdateDoubleCheckBox(CheckBox checkBox, EditText editText, CheckBox correspondingTripleCheckBox) {
        this.correspondingTripleCheckBox = correspondingTripleCheckBox;
        updateDoubleCheckBoxTextView(checkBox, editText);
    }

    public void updateDoubleCheckBoxTextView(CheckBox checkedBox, EditText editText){

        checkedBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!correspondingTripleCheckBox.isChecked()) {
                        storedOriginalValue = Integer.parseInt(editText.getText().toString());
                        System.out.println("StoredOriginalValue: " + storedOriginalValue);
                        doubleEditTextValue(editText);
                    }
                    correspondingTripleCheckBox.setChecked(false);
                } else {
                    resetEditTextValue(editText);
                } if (correspondingTripleCheckBox.isChecked()) {
                    tripleEditTextValue(editText);
                }
            }
        });
    }

    private void doubleEditTextValue(EditText editText) {
        int doubledValue = storedOriginalValue * 2;
        System.out.println("DoubledValue: " + doubledValue);
        editText.setText(String.valueOf(doubledValue));
        originalValue = storedOriginalValue;
    }

    private void tripleEditTextValue(EditText editText) {
        int tripledValue = storedOriginalValue * 3;
        System.out.println("TripledValue: " + tripledValue);
        editText.setText(String.valueOf(tripledValue));
        originalValue = storedOriginalValue;
    }

    private void resetEditTextValue(EditText editText) {
        editText.setText(String.valueOf(originalValue));
        originalValue = storedOriginalValue;
    }
}
