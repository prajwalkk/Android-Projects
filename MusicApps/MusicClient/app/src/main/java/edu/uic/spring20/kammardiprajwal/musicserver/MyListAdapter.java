package edu.uic.spring20.kammardiprajwal.musicserver;

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

public class MyListAdapter extends ArrayAdapter {
    List<MusicModel> mMusicList;
    Context mContext;
    int mResource;

    public MyListAdapter(@NonNull Context context, int resource, @NonNull List<MusicModel> musicList) {
        super(context, resource, musicList);
        this.mContext = context;
        this.mMusicList = musicList;
        this.mResource = resource;
    }
    private static class ViewHolder{
        ImageView imageView;
        TextView musicTitle;
        TextView musicArtist;

        public ViewHolder(View view) {
            imageView = (ImageView)view.findViewById(R.id.list_image);
            musicTitle = (TextView)view.findViewById(R.id.title);
            musicArtist = (TextView)view.findViewById(R.id.year);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;
        if(view == null) {

            Log.d("ListAdaper", "No Views initially for " + getItem(position).getTitle());
            LayoutInflater lf = LayoutInflater.from(mContext);
            view = lf.inflate(mResource, null, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else {
            Log.d("ListAdaper", "reusing views for " + getItem(position).getTitle());
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.imageView.setImageBitmap(getItem(position).getImage());
        viewHolder.musicTitle.setText(getItem(position).getTitle());
        viewHolder.musicArtist.setText(getItem(position).getArtist());

        return view;

    }

    @Override
    public int getCount() {
        return mMusicList.size();
    }

    @Nullable
    @Override
    public MusicModel getItem(int position) {
        return mMusicList.get(position);
    }

}

