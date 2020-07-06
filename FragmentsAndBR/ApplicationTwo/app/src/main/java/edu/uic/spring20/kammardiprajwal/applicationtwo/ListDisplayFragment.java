package edu.uic.spring20.kammardiprajwal.applicationtwo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class ListDisplayFragment extends ListFragment {
    private static final String TAG = "ListDisplayFragment";
    private ListSelectionListener mListener = null;


    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Log.i(TAG, "onListItemClick: " + getClass().getSimpleName());
        getListView().setItemChecked(position, true);
        mListener.onListSelection(position);
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        Log.i(TAG, "onAttach: " + getClass().getSimpleName());
        super.onAttach(activity);
        try {
            mListener = (ListSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "Must Implment OnSelected Listener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: " + getClass().getSimpleName());
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: " + getClass().getSimpleName());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated: " + getClass().getSimpleName());
        super.onActivityCreated(savedInstanceState);


        //Set the list adapyer TODO
        setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.fragment_list,  getArguments().getStringArray("listData")));
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);


    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart: " + getClass().getSimpleName());
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume: " + getClass().getSimpleName());
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause: " + getClass().getSimpleName());
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroyView: " + getClass().getSimpleName());
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop: " + getClass().getSimpleName());
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: " + getClass().getSimpleName());
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach: " + getClass().getSimpleName());
        super.onDetach();
    }

    // Helps in notiying the activity that the list is being selected
    public interface ListSelectionListener {
        public void onListSelection(int index);
    }
}
