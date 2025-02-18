package com.example.notebook;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView dataNotesTextView = findViewById(R.id.Data_Notes);

        // Get the data passed from MainActivity
        String item = getIntent().getStringExtra("ITEM");
        dataNotesTextView.setText(item);
    }
}