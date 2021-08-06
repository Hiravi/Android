package com.example.colordescriptor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerOfColors;
    private TextView textViewCharacterDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerOfColors = findViewById(R.id.spinnerOfColors);
        textViewCharacterDescription = findViewById(R.id.textViewCharacterDescription);
    }

    public void showDescription(View view) {
        int position = spinnerOfColors.getSelectedItemPosition();
        String description = getDescriptionByPosition(position);
        textViewCharacterDescription.setText(description);
    }

    public String getDescriptionByPosition(int position) {
        String[] characterDescriptions =
                getResources().getStringArray(R.array.character_description);
        return characterDescriptions[position];
    }
}