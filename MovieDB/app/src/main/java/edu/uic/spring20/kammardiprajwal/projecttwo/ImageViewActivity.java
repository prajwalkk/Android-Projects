package edu.uic.spring20.kammardiprajwal.projecttwo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ImageViewActivity extends AppCompatActivity {
    private static final String TAG = "ImageViewActivity";

    ImageView mImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_image);
        mImageView = (ImageView) findViewById(R.id.fullpic);
        Bundle data = getIntent().getExtras();
        final Movie movie = (Movie) data.getParcelable("Movie");

        mImageView.setImageResource(movie.getImage());

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent linkIntent = new Intent(Intent.ACTION_VIEW);
                linkIntent.setData(Uri.parse(movie.getIMDBLink()));
                startActivity(linkIntent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: Destroyed");
        mImageView.setImageDrawable(null);
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}
