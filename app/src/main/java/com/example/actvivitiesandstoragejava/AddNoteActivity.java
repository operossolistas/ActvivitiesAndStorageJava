package com.example.actvivitiesandstoragejava;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddNoteActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextContent;
    private static final String PREFS_NAME = "NotesPrefs";
    private static final String NOTES_KEY = "notes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add Note");
        }

        editTextName = findViewById(R.id.editTextName);
        editTextContent = findViewById(R.id.editTextContent);
        Button buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(v -> saveNote());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void saveNote() {
        String name = editTextName.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();

        if (name.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(NOTES_KEY, null);
        Type type = new TypeToken<ArrayList<Note>>() {}.getType();
        ArrayList<Note> notesList = gson.fromJson(json, type);

        if (notesList == null) {
            notesList = new ArrayList<>();
        }

        notesList.add(new Note(name, content));
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(NOTES_KEY, gson.toJson(notesList));
        editor.apply();

        Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
} 