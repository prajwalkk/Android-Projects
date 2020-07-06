package edu.uic.spring20.kammardiprajwal.musicserver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;

public class MusicPlaybackService extends Service  {

    private static final String TAG = "MusicPlaybackService";
    private static final String ACTION_PLAY = "com.example.action.PLAY";
    private static final int NOTIFICATION_ID = 1;
    private static String URL = "";
    private static String CHANNEL_ID = "Music player style";
    private MediaPlayer mediaPlayer;
    private int mStartID;
    private Notification notification;

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ");
        super.onCreate();
        this.createNotificationChannel();
        final Intent notificationIntent = new Intent(getApplicationContext(), MusicServiceClient.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        notification =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_media_play)
                        .setOngoing(true).setContentTitle("Music Playing")
                        .setContentText("Click to Access Music Player")
                        .setTicker("Music is playing!")
                        .setFullScreenIntent(pendingIntent, false)
                        .build();
        if (null != mediaPlayer) {
            mediaPlayer.setLooping(false);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopSelf(mStartID);
                }
            });
        }
        startForeground(NOTIFICATION_ID, notification);
    }

    private void createNotificationChannel() {
        Log.i(TAG, "createNotificationChannel: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Music player notification";
            String description = "The channel for music player notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        URL = intent.getStringExtra("URL");
            if (mediaPlayer == null) {
                Log.i(TAG, "onStartCommand: Music Player Null");
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setAudioAttributes(new AudioAttributes
                            .Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build());
                    mediaPlayer.setDataSource(URL);
                    mediaPlayer.prepareAsync();
                } catch (Exception e) {
                    Toast.makeText(this, "Bad Audio", Toast.LENGTH_LONG).show();
                }
            }else{
                Log.i(TAG, "onStartCommand: Music Player not null");
                mStartID = startId;
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.reset();
                    try {
                        mediaPlayer.setDataSource(URL);
                        mediaPlayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
