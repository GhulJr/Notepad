package com.oskarrek.notepadapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.oskarrek.notepadapp.recycler_view_adapters.NotesAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.oskarrek.notepadapp.models.Note;
import com.oskarrek.notepadapp.view_models.NotesListViewModel;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private NotesListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpRecycleView();

        setUpViewModel();

        setUpAddNoteButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setUpRecycleView() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter = new NotesAdapter();

        recyclerView = findViewById(R.id.notes_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
    }

    private void setUpViewModel() {
        viewModel = ViewModelProviders
                .of(this)
                .get(NotesListViewModel.class);

        //TODO: use lambdas
        viewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //Check if any note exist.
                if(notes == null || notes.isEmpty()) {
                    //TODO: provide information about empty data
                    Toast.makeText(MainActivity.this,"No data.", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                adapter.setNotes(notes);
            }
        });
    }

    private void setUpAddNoteButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note note = new Note("Title", "Some long text to display");

                viewModel.insertNotes(note);
            }
        });
    }
}
