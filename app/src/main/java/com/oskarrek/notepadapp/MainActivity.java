package com.oskarrek.notepadapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.oskarrek.notepadapp.recycler_view_adapters.NotesAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.oskarrek.notepadapp.models.Note;
import com.oskarrek.notepadapp.view_models.NotesListViewModel;

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
    protected void onResume() {
        super.onResume();
        //If notes are note changed, restore recycler view.
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(
                    MainActivity.this,
                    "Implement settings if needed.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*Initialize recycler view. */
    private void setUpRecycleView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter = new NotesAdapter();

        recyclerView = findViewById(R.id.notes_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(itemSwipeHelper).attachToRecyclerView(recyclerView);
    }

    /*Initialize view model. */
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
                    Toast.makeText(
                            MainActivity.this,
                            R.string.empty_db,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                adapter.setNotes(notes);
            }
        });
    }

    /*Initialize action button. */
    private void setUpAddNoteButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to activity that allows add/edit notes.
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                startActivity(intent);

            }
        });
    }

    /*Adding swiping functionality.*/
    private ItemTouchHelper.SimpleCallback itemSwipeHelper =
            new ItemTouchHelper.SimpleCallback(
                    0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(
                @NonNull RecyclerView recyclerView,
                @NonNull RecyclerView.ViewHolder viewHolder,
                @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            //Delete note if swiped left.
            if(direction == ItemTouchHelper.LEFT){
               viewModel.deleteNote(adapter.getNoteAt(position));

            }
            else {
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                intent.putExtra("edit_note_id", adapter.getNoteAt(position).getNoteID());
                startActivity(intent);
            }
        }
    };
}
