package edu.uic.spring20.kammardiprajwal.createcontact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class TextEntry extends Activity {
    private EditText contactName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_edit);

        contactName  = findViewById(R.id.fullName);
        contactName.setOnEditorActionListener(onEditActionListener);
        Log.i("TextEntry", "Activity 2 created. Listening for input");
    }

    public  EditText.OnEditorActionListener onEditActionListener = new EditText.OnEditorActionListener(){

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == KeyEvent.KEYCODE_ENTER) {
                // Remove Extra whitespace and trim spaces before and after
                String fullNameString = contactName.getText().toString().replaceAll("\\s+", " ").trim();
                if (fullNameString.isEmpty()){
                    Intent invalidContact = new Intent();
                    invalidContact.putExtra("fullName", fullNameString);
                    setResult(Activity.RESULT_CANCELED, invalidContact);
                    finish();
                    Log.i("TextEntry", "Invalid name: Null String. Returning back");
                    return true;
                }
                else {

                    String[] splitNames = fullNameString.split(" ");
                    {
                        // Checks for name to be more than 1 word
                        // and just legal alphabet (not just the english alphabet. unicode legal alphabet)
                        // in the names with usable punctuations (includes space)
                        if(splitNames.length > 1 && fullNameString.matches("^[\\p{L} .'-]+$")) {
                            Intent validContact = new Intent();
                            validContact.putExtra("fullName", fullNameString);
                            setResult(Activity.RESULT_OK, validContact);
                            finish();
                            Log.i("TextEntry", "Name found to be legal. Returning to main activity");
                            return true;
                        }
                        else{
                            Intent invalidContact = new Intent();
                            invalidContact.putExtra("fullName", fullNameString);
                            setResult(Activity.RESULT_CANCELED, invalidContact);
                            finish();
                            Log.i("TextEntry", "Invalid name: Bad format. Returning back");
                            return true;
                        }
                    }
                }
            }
            return false;

        }
    };
}
