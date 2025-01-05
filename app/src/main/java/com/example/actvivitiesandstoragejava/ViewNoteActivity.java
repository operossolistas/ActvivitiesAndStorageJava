package com.example.actvivitiesandstoragejava;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewNoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("View Note");
        }

        TextView titleView = findViewById(R.id.textViewTitle);
        TextView contentView = findViewById(R.id.textViewContent);

        String title = getIntent().getStringExtra("note_title");
        String content = getIntent().getStringExtra("note_content");

        titleView.setText(title);
        contentView.setText(content);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
} 