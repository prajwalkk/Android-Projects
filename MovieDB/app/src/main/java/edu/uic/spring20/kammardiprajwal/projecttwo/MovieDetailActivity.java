package edu.uic.spring20.kammardiprajwal.projecttwo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String TAG = "MovieDetailActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        Bundle data = getIntent().getExtras();
        final Movie movie = (Movie) data.getParcelable("Movie");
        TextView title = (TextView) findViewById(R.id.md_movie_title);
        TextView releaseDate = (TextView) findViewById(R.id.md_release_date);
        TextView duration = (TextView) findViewById(R.id.md_duration);
        TextView IMDBRating = (TextView) findViewById(R.id.md_imdb_rating);
        TextView RottenRating = (TextView) findViewById(R.id.md_rotten_rating);
        TextView directorDetails = (TextView) findViewById(R.id.md_director_details);
        TextView castDetails = (TextView) findViewById(R.id.md_stars_details);

        if(data!=null){
            title.setText(movie.getMovieName());
            releaseDate.setText(movie.getReleaseDate());
            duration.setText(movie.getDuration());
            IMDBRating.setText(movie.getRating_1());
            RottenRating.setText(movie.getRating_2());
            directorDetails.setText("Director: " + movie.getDirector());
            castDetails.setText("Cast: " + movie.getCast());
        }
    }
}
