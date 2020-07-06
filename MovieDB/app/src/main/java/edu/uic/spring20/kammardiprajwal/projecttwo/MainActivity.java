package edu.uic.spring20.kammardiprajwal.projecttwo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int YT = 1;
    private static final int IMDB = 2;
    private static final int WIKI = 3;
    List<Movie> movieList;
    ListView lv;
    private ArrayList<Integer> movieimages = new ArrayList<>(
            Arrays.asList(R.drawable.film1917, R.drawable.getout, R.drawable.joker, R.drawable.lalaland,
                    R.drawable.madmax, R.drawable.moonlight, R.drawable.parasite, R.drawable.honeyboy,
                    R.drawable.quietplace, R.drawable.spotlight));

    private String[] movies;
    private String[] years;
    private String[] directors;
    private String[] imdbLinks;
    private String[] trailerLinks;
    private String[] wikiLinks;
    private String[] rating_1;
    private String[] rating_2;
    private String[] stars;
    private String[] duration;
    private String[] releaseDate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();
        years = getResources().getStringArray(R.array.yearlist);
        movies = getResources().getStringArray(R.array.movieList);
        imdbLinks = getResources().getStringArray(R.array.movieLinks);
        trailerLinks = getResources().getStringArray(R.array.trailerLinks);
        wikiLinks = getResources().getStringArray(R.array.wikiLinks);
        directors = getResources().getStringArray(R.array.directorList);
        rating_1 = getResources().getStringArray(R.array.rating1);
        rating_2 = getResources().getStringArray(R.array.rating2);
        stars = getResources().getStringArray(R.array.cast);
        duration = getResources().getStringArray(R.array.duration);
        releaseDate = getResources().getStringArray(R.array.releaseDates);


        if (years.length == movies.length && movies.length == movieimages.size())
            for (int i = 0; i < movieimages.size(); i++) {
                movieList.add(new Movie(movieimages.get(i), movies[i], years[i], imdbLinks[i], trailerLinks[i], wikiLinks[i], directors[i], rating_1[i], rating_2[i], stars[i], duration[i], releaseDate[i]));
                Log.d(TAG, movieList.get(i).toString());
            }
        else {
            Log.e(TAG, "The arrays are not the same size");
        }

        lv = findViewById(R.id.movielist);
        MyListAdapter adapter = new MyListAdapter(this, R.layout.individual_list, movieList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, ImageViewActivity.class);
                intent.putExtra("Movie", movieList.get(position));
                startActivity(intent);
            }
        });
        registerForContextMenu(lv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Details");
        menu.add(0, v.getId(), 0, "Trailer Link");
        menu.add(0, v.getId(), 0, "Director's Wikipedia");
        menu.add(0, v.getId(), 0, "IMDb link");

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        String itemText = item.getTitle().toString();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (itemText) {
            case "Details":
                showDetails(info.position);
                return true;

            case "Trailer Link":
                showLink(info.position, YT);
                return true;

            case "Director's Wikipedia":
                showLink(info.position, WIKI);
                return true;

            case "IMDb link":
                showLink(info.position, IMDB);
                return true;

            default:
                return false;
        }
    }

    private void showDetails(int position) {
        Intent displayDetail = new Intent(MainActivity.this, MovieDetailActivity.class);
        displayDetail.putExtra("Movie", movieList.get(position));
        startActivity(displayDetail);
        return;
    }

    void showLink(int position, int type) {
        Intent linkIntent = new Intent(Intent.ACTION_VIEW);
        switch (type) {
            case YT:
                linkIntent.setData(Uri.parse(movieList.get(position).getYTLink()));
                break;
            case IMDB:
                linkIntent.setData(Uri.parse(movieList.get(position).getIMDBLink()));
                break;
            case WIKI:
                linkIntent.setData(Uri.parse(movieList.get(position).getWikiLink()));
                break;
        }
        startActivity(linkIntent);
    }
}
