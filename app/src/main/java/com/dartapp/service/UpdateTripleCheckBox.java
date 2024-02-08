package com.dartapp.service;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class UpdateTripleCheckBox {
    private int originalValue;
    private int storedOriginalValue;  // New variable to store the original value
    private final CheckBox correspondingDoubleCheckBox;

    public UpdateTripleCheckBox(CheckBox checkBox, EditText editText, CheckBox correspondingDoubleCheckBox) {
        this.correspondingDoubleCheckBox = correspondingDoubleCheckBox;
        updateTripleCheckBoxTextView(checkBox, editText);
    }

    public void updateTripleCheckBoxTextView(CheckBox checkedBox, EditText editText) {

        checkedBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!correspondingDoubleCheckBox.isChecked()) {
                        storedOriginalValue = Integer.parseInt(editText.getText().toString());
                        tripleEditTextValue(editText);
                    }
                    correspondingDoubleCheckBox.setChecked(false);
                } else {
                    resetEditTextValue(editText);
                }if (correspondingDoubleCheckBox.isChecked()) {
                    doubleEditTextValue(editText);
                }
            }
        });
    }

    private void doubleEditTextValue(EditText editText) {
        int doubledValue = storedOriginalValue * 2;
        editText.setText(String.valueOf(doubledValue));
        originalValue = storedOriginalValue;
    }

    private void tripleEditTextValue(EditText editText) {
        int tripledValue = storedOriginalValue * 3;  // Use the stored original value
        editText.setText(String.valueOf(tripledValue));
        originalValue = storedOriginalValue;  // Update the original value
    }

    private void resetEditTextValue(EditText editText) {
        editText.setText(String.valueOf(originalValue));
    }
}

