package com.example.actvivitiesandstoragejava;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class DeleteNoteActivity extends AppCompatActivity {
    private Spinner spinnerNotes;
    private ArrayList<Note> notesList;
    private static final String PREFS_NAME = "NotesPrefs";
    private static final String NOTES_KEY = "notes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Delete Note");
        }

        spinnerNotes = findViewById(R.id.spinnerNotes);
        Button buttonDelete = findViewById(R.id.buttonDelete);

        loadNotes();
        ArrayAdapter<Note> adapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_spinner_item, notesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNotes.setAdapter(adapter);

        buttonDelete.setOnClickListener(v -> deleteNote());
    }

    private void loadNotes() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(NOTES_KEY, null);
        Type type = new TypeToken<ArrayList<Note>>() {}.getType();
        notesList = gson.fromJson(json, type);

        if (notesList == null) {
            notesList = new ArrayList<>();
        }
    }

    private void deleteNote() {
        if (notesList.isEmpty()) {
            Toast.makeText(this, "No notes to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        Note selectedNote = (Note) spinnerNotes.getSelectedItem();
        notesList.remove(selectedNote);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        editor.putString(NOTES_KEY, gson.toJson(notesList));
        editor.apply();

        Toast.makeText(this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
} 