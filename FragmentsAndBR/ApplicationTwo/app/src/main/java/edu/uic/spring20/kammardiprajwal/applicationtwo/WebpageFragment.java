package edu.uic.spring20.kammardiprajwal.applicationtwo;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WebpageFragment extends Fragment {

    private static final String TAG = "WebpageFragment";


    private WebView mWebView = null;
    private int currIndex = -1;
    private int mWebArrayLength;
    private String[] mWebarray;

    int getShownIndex() {
        return currIndex;
    }


    public void showWebpageAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= mWebArrayLength) {
            return;
        }
        currIndex = newIndex;
        // TODO Set mWebView.
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(mWebarray[currIndex]);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.i(TAG, "onAttach: " + getClass().getSimpleName());
        super.onAttach(context);
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
        return inflater.inflate(R.layout.fragment_webview, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            mWebView = getActivity().findViewById(R.id.websiteView);
            mWebarray = getArguments().getStringArray("webpageData");
            mWebArrayLength = mWebarray.length; //TODO;
        } else {
            mWebView.restoreState(savedInstanceState.getBundle("webViewState"));
            mWebarray = getArguments().getStringArray("webpageData");
            mWebArrayLength = mWebarray.length; //TODO;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mWebView.saveState(outState);
        Bundle bundle = new Bundle();
        mWebView.saveState(bundle);
        outState.putBundle("webViewState", bundle);
    }
}
