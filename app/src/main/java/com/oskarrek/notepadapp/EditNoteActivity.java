package com.oskarrek.notepadapp;

import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import com.oskarrek.notepadapp.models.Note;
import com.oskarrek.notepadapp.view_models.EditNoteViewModel;

import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import org.w3c.dom.Text;

public class EditNoteActivity extends AppCompatActivity {

    private EditText editTextView;
    private EditText editTitleView;

    private EditNoteViewModel viewModel;


   /* /** Denotes annotated forecast type to integer type
    @IntDef({MODE_VIEW, MODE_EDIT})
    public @interface Forecast_Type {}
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        editTextView = findViewById(R.id.edit_text);
        editTitleView = findViewById(R.id.edit_title);

        checkIncomingIntent();
    }



    /*Return true if intent was set to edit existing note or add new.*/
    private boolean checkIncomingIntent() {
        //If it's true it means that we edit existing note.
        if(getIntent().hasExtra("edit_note_id")) {
            int id = getIntent().getIntExtra("edit_note_id", -1);
            setUpViewModel(id);

            return true;
        } else {

            return false;
        }
    }

    private void setUpViewModel(int id) {
        viewModel = ViewModelProviders.of(this).get(EditNoteViewModel.class);
        viewModel.fetchNoteById(id);
        viewModel.getEditedNote().observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                editTitleView.setText(note.getTitle());
                editTextView.setText(note.getText());
            }
        });
    }


}
