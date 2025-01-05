package com.example.actvivitiesandstoragejava;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listViewNotes;
    private ArrayList<Note> notesList;
    private ArrayAdapter<Note> adapter;
    private static final String PREFS_NAME = "NotesPrefs";
    private static final String NOTES_KEY = "notes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewNotes = findViewById(R.id.listViewNotes);
        notesList = new ArrayList<>();
        
        adapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_list_item_1, notesList);
        listViewNotes.setAdapter(adapter);
        
        loadNotes();

        listViewNotes.setOnItemClickListener((parent, view, position, id) -> {
            Note selectedNote = notesList.get(position);
            Intent intent = new Intent(MainActivity.this, ViewNoteActivity.class);
            intent.putExtra("note_title", selectedNote.getName());
            intent.putExtra("note_content", selectedNote.getContent());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
        adapter.notifyDataSetChanged();
    }

    private void loadNotes() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String json = prefs.getString(NOTES_KEY, null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<Note>>(){}.getType();
            ArrayList<Note> loadedNotes = new Gson().fromJson(json, type);
            if (loadedNotes != null) {
                notesList.clear();
                notesList.addAll(loadedNotes);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_note) {
            startActivity(new Intent(this, AddNoteActivity.class));
            return true;
        } else if (id == R.id.action_delete_note) {
            startActivity(new Intent(this, DeleteNoteActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}