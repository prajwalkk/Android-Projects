package edu.uic.spring20.kammardiprajwal.musicserver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MusicServiceClient extends AppCompatActivity {

    private static final String TAG = "MusicServiceClient";
    TextView output = null;
    ToggleButton getListBtn = null;
    TextView songInfo = null;
    TextView artistInfo = null;
    TextView songlink = null;
    ImageView albumArt = null;
    List<MusicModel> musicModelList = null;
    ListView listView;
    private IMusicPlayService mIMusicPlayService;
    private boolean mIsBound = false;


    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIMusicPlayService = IMusicPlayService.Stub.asInterface(service);
            Log.i(TAG, "onServiceConnected: " + (mIMusicPlayService == null));
            Log.i(TAG, "onServiceConnected: ");
            mIsBound = true;
            onBindOperations();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected: ");
            Toast.makeText(MusicServiceClient.this, "Service died/Killed. Please click Bind again", Toast.LENGTH_LONG).show();
            mIMusicPlayService = null;
            mIsBound = false;
            unbindFunction();
            onUnbindOperations();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = findViewById(R.id.textview_show_bound);
        getListBtn = findViewById(R.id.button_bind);
        songInfo = findViewById(R.id.textview_currtrack);
        artistInfo = findViewById(R.id.textview_artist);
        songlink = findViewById(R.id.textview_url);
        albumArt = findViewById(R.id.imageview_dp);

        getListBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bindFunction();


                } else {
                    unbindFunction();
                    //stopMusicService();
                    mIsBound = false;
                    onUnbindOperations();
                }
            }
        });
    }

    protected void bindFunction() {
        if (!mIsBound) {
            boolean b = false;
            Intent i = new Intent(IMusicPlayService.class.getName());
            ResolveInfo info = getPackageManager().resolveService(i, 0);
            try {
                i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));
                b = bindService(i, this.mConnection, Context.BIND_AUTO_CREATE);
            }catch (NullPointerException e){
                Toast.makeText(this, "Server App not Installed", Toast.LENGTH_LONG).show();
                getListBtn.setChecked(false);
            }
            if (b) {
                Toast.makeText(this, "Service bound", Toast.LENGTH_LONG).show();
                Log.i(TAG, "bindFunction: Bind Succeeded" + mIsBound);
            } else {
                Log.i(TAG, "bindFunction: bindFailed" + mIsBound);
            }
        }
    }

    protected void onBindOperations() {
        getListBtn.setChecked(true);
        final Intent musicintent = new Intent(getApplicationContext(), MusicPlaybackService.class);
        try {
            if (mIMusicPlayService != null) {
                output.setText(mIMusicPlayService.connectFlag());
                Log.i(TAG, "onCreate: Connected");
                listView = findViewById(R.id.listview_music);
                musicModelList = mIMusicPlayService.getAll();
                if (musicModelList.size() > 0) {
                    Log.i(TAG, "onClick: " + musicModelList.size());
                    MyListAdapter adapter = new MyListAdapter(MusicServiceClient.this, R.layout.individual_list, musicModelList);
                    listView.setAdapter(adapter);
                    listView.setVisibility(View.VISIBLE);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                Log.i(TAG, "onItemClick: calling MusicPlay");
                                musicintent.putExtra("URL", mIMusicPlayService.getURL(position + 1));
                                MusicModel currTrack = mIMusicPlayService.getSongInfo(position + 1);
                                albumArt.setImageBitmap(currTrack.getImage());
                                songInfo.setText(currTrack.getTitle());
                                artistInfo.setText(currTrack.getArtist());
                                songlink.setText(currTrack.getUrl());
                                startService(musicintent);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            } else {
                output.setText("Not Connected");
                Log.i(TAG, "onCreate: Not Connected");
            }
        } catch (RemoteException e) {
            Log.e(TAG, e.toString());
        }
    }

    protected void unbindFunction() {
        if (mIsBound) {
            unbindService(this.mConnection);
        }


        //Toast.makeText(this, "Unbinding", Toast.LENGTH_SHORT).show();

    }
    protected void onUnbindOperations(){
        getListBtn.setChecked(false);
        listView.setVisibility(ListView.INVISIBLE);
        output.setText("Binding Removed");
        albumArt.setImageResource(R.mipmap.ic_launcher);
        songInfo.setText("Song");
        artistInfo.setText("Artist");
        songlink.setText("URL");

    }

    protected void stopMusicService(){
        Intent musicintent = new Intent(getApplicationContext(), MusicPlaybackService.class);
        stopService(musicintent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //bindFunction();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindFunction();
        stopMusicService();
    }
}
