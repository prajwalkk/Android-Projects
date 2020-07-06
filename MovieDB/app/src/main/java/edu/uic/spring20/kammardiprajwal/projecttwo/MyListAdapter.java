package edu.uic.spring20.kammardiprajwal.projecttwo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyListAdapter extends ArrayAdapter<Movie> {

    //TODO: Delete whole of this and implement viewHolder for better optimisation
    private static final String TAG = "MyListAdapter";

    List<Movie> mMovieList;
    Context mContext;
    int mResource;


    public MyListAdapter(@NonNull Context context, int resource, List<Movie> movieList) {
        super(context, resource, movieList);
        this.mMovieList = movieList;
        this.mContext = context;
        this.mResource = resource;


    }

    private static class ViewHolder{
        ImageView imageView;
        TextView movieTitle;
        TextView movieYear;

        public ViewHolder(View view) {
            imageView = (ImageView)view.findViewById(R.id.list_image);
            movieTitle = (TextView)view.findViewById(R.id.title);
            movieYear = (TextView)view.findViewById(R.id.year);
        }
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;
        if(view == null) {

            Log.d("ListAdaper", "No Views initially for " + getItem(position).getMovieName());
            LayoutInflater lf = LayoutInflater.from(mContext);
            view = lf.inflate(mResource, null, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else {
            Log.d("ListAdaper", "reusing views for " + getItem(position).getMovieName());
            viewHolder = (ViewHolder) view.getTag();
        }

            viewHolder.imageView.setImageDrawable(mContext.getResources().getDrawable(getItem(position).getImage(), null));
            viewHolder.movieTitle.setText(getItem(position).getMovieName());
            viewHolder.movieYear.setText(getItem(position).getYear());

        return view;

    }

    @Override
    public int getCount() {
        return mMovieList.size();
    }

    @Nullable
    @Override
    public Movie getItem(int position) {
        return mMovieList.get(position);
    }

}

