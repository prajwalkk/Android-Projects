package edu.uic.spring20.kammardiprajwal.createcontact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button createContactBtn;
    private String fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button enterTextBtn = findViewById(R.id.enterText);
        createContactBtn = findViewById(R.id.createContact);
        enterTextBtn.setOnClickListener(enterTextListener);
    }

    public View.OnClickListener enterTextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, TextEntry.class);
            startActivityForResult(intent, 1);
            Log.i("MainActivity", "Enter Text Clicked. trying to call Activity B");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("Main Activity", "Activity-B finished with result" + resultCode);

        //This button activates only if onActivityResult is finished


        if (requestCode == 1) {
            fullName = data.getStringExtra("fullName");
            if (resultCode == Activity.RESULT_CANCELED) {
                createContactBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Contact Name Error: " + fullName, Toast.LENGTH_LONG).show();
                    }
                });
            } else if (resultCode == Activity.RESULT_OK) {
                Log.i("Main Activity", "contact correctly entered");

                createContactBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Assumption fist word is firstName. Rest of the words belong to last name
                        insertContact(fullName);
                    }
                });
            }

        }
    }

    private void insertContact(String fullName) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, fullName);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
