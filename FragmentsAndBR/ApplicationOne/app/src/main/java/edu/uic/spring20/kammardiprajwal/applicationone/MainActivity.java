package edu.uic.spring20.kammardiprajwal.applicationone;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public View.OnClickListener restoBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Toast.makeText(MainActivity.this, "Opening Restaurants", Toast.LENGTH_SHORT).show();
            sendBroadcast(v, "RESTO");
            Log.i(TAG, "onClick: Resto clicked");
        }
    };

    public View.OnClickListener attactionBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Toast.makeText(MainActivity.this, "Opening Attractions", Toast.LENGTH_SHORT).show();
            sendBroadcast(v, "ATTR");
            Log.i(TAG, "onClick: Attraction Clicked");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkSelfPermission("edu.uic.cs478.sp2020.project3")
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onCreate: Permission Not granted. Requesting ");
            Log.d(TAG, "onCreate: " + checkSelfPermission("edu.uic.cs478.sp2020.project3"));
            requestPermissions(new String[]{"edu.uic.cs478.sp2020.project3"}, 0);
        } else {
            Log.d(TAG, "onCreate: Permission Granted");
        }


        Button restoBtn = findViewById(R.id.restoBtn);
        Button attractionBtn = findViewById(R.id.attractionBtn);

        restoBtn.setOnClickListener(restoBtnClickListener);
        attractionBtn.setOnClickListener(attactionBtnListener);
    }


    private void sendBroadcast(View v, String type) {
        String INTENT_STRING = "edu.spring.2020.Project3.";
        // Send 2 different Intents
        String action = INTENT_STRING + type;
        Log.i(TAG, "sendBroadcast: "+ action);
        Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

}

