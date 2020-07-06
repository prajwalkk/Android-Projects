package edu.uic.spring20.kammardiprajwal.musicserver;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MusicCentralImpl extends Service {
    private static final String TAG = "MusicCentralImpl";
    private final static Set<String> mStrings = new HashSet<String>();
    private final static List<MusicModel> mSongs = Collections.synchronizedList(new ArrayList<MusicModel>());
    private final IMusicPlayService.Stub mBinder = new IMusicPlayService.Stub() {


        @Override
        public String connectFlag() throws RemoteException {

            if(mSongs.size() == 0) {
                mSongs.add(new MusicModel(1, "Bro Time", "Nat Keefe", "https://www.youtube.com/audiolibrary_download?vid=0c4199aac1d412f1", ((BitmapDrawable) getDrawable(R.drawable.image1)).getBitmap()));
                mSongs.add(new MusicModel(2, "Cherokees Shuffle", "Nat Keefe and Hot Buttered Rum", "https://www.youtube.com/audiolibrary_download?vid=ac4a944c99e0c7bf", ((BitmapDrawable) getDrawable(R.drawable.image2)).getBitmap()));
                mSongs.add(new MusicModel(3, "Carmelized", "Craig McArthur", "https://www.youtube.com/audiolibrary_download?vid=c9681d6e29322d3d", ((BitmapDrawable) getDrawable(R.drawable.image3)).getBitmap()));
                mSongs.add(new MusicModel(4, "Phantom", "Density and Time", "https://www.youtube.com/audiolibrary_download?vid=dc80d7f0cfef52e7", ((BitmapDrawable) getDrawable(R.drawable.image4)).getBitmap()));
                mSongs.add(new MusicModel(5, "I feel Like Partying right now", "Nat Keefe and BeatMower", "https://www.youtube.com/audiolibrary_download?vid=44d3f5eb6916f887", ((BitmapDrawable) getDrawable(R.drawable.image5)).getBitmap()));
            }return "Connected";
        }

        @Override
        public List<MusicModel> getAll() throws RemoteException {
            if(mSongs.size() > 0)
                return mSongs;
            return null;
        }

        @Override
        public MusicModel getSongInfo(int songNo) throws RemoteException {
            for (MusicModel musicModel:mSongs){
                if(musicModel.getId() == songNo){
                    Log.i(TAG, "getSongInfo: " + musicModel.toString());
                    return musicModel;
                }
            };
            return null;
        }


        @Override
        public String getURL(int songNo) throws RemoteException {
            for (MusicModel musicModel:mSongs){
                if(musicModel.getId() == songNo){
                    Log.i(TAG, "getSongInfo: " + musicModel.toString());
                    return musicModel.getUrl();
                }
            }
            return null;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
