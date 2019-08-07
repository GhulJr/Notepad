package com.oskarrek.notepadapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import com.oskarrek.notepadapp.models.Note;
import com.oskarrek.notepadapp.view_models.EditNoteViewModel;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

public class EditNoteActivity extends AppCompatActivity {

    private final static int REQUEST_GET_SINGLE_FILE = 0;

    private EditText editTextView;
    private EditText editTitleView;
    private EditNoteViewModel viewModel;
    private Note editedNote;

    /*If existing note is edited then it's set to true, otherwise it's set to false.*/
    private boolean isExistingNote;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        editTextView = findViewById(R.id.edit_text);
        editTitleView = findViewById(R.id.edit_title);

        viewModel = ViewModelProviders.of(this).get(EditNoteViewModel.class);

        isExistingNote = checkIncomingIntent();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO: set image from taken uri.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_discard: {
                finish();
                return true;
            }
            case R.id.action_save: {
                String title = editTitleView.getText().toString();
                String text = editTextView.getText().toString();

                //Checks if we add new note or edit existing one.
                if(isExistingNote){
                    editedNote.setTitle(title);
                    editedNote.setText(text);
                    viewModel.updateNote(editedNote);
                }
                else {
                    viewModel.insertNote(title, text);
                }
                finish();
                return true;
            }

            case R.id.action_get_image_from_gallery: {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent
                        .createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_FILE);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    /*Return true if intent was set to edit existing note or add new.*/
    private boolean checkIncomingIntent() {

        //If it's true it means that we edit existing note.
        if(getIntent().hasExtra(getString(R.string.edit_note_id))) {
            //Hide keyboard if note already exists.
            this.getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            int id = getIntent().getIntExtra(getString(R.string.edit_note_id), -1);
            setUpViewModel(id);

            return true;
        } else {



            return false;
        }
    }

    private void setUpViewModel(int id) {
        viewModel.fetchNoteById(id);
        viewModel.getEditedNote().observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                editedNote = note;
                editTitleView.setText(note.getTitle());
                editTextView.setText(note.getText());
            }
        });
    }


}
