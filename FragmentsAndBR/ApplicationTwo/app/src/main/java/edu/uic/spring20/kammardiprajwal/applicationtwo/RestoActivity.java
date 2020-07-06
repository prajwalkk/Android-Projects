package edu.uic.spring20.kammardiprajwal.applicationtwo;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.uic.spring20.kammardiprajwal.applicationtwo.ListDisplayFragment.ListSelectionListener;
import edu.uic.spring20.kammardiprajwal.applicationtwo.Model.MyBroadcastReceiver;

public class RestoActivity extends AppCompatActivity implements ListSelectionListener {


    private static final String TAG = "RestoActivity";
    private static final String PERMISSION_CUSTOM = "edu.uic.cs478.sp2020.project3";
    private static final String INTENT_NEEDED_RESTO = "edu.spring.2020.Project3.RESTO";
    private static final String INTENT_NEEDED_ATTR = "edu.spring.2020.Project3.ATTR";

    //Variables
    public static String[] mRestoArray = null;
    public static String[] mRestoLinkArray = null;

    //Fragments
    private final ListDisplayFragment RestoListFragment = new ListDisplayFragment();
    //Receiver
    MyBroadcastReceiver receiver = new MyBroadcastReceiver();
    Integer selectedIndex = -1;
    private WebpageFragment RestoWebFragment = null;
    //OBJECTS
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    //FrameLayouts for adjusting the layout
    private FrameLayout mListLayout, mWebViewLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: Activity Created");
        super.onCreate(savedInstanceState);
        IntentFilter filter_1 = new IntentFilter(INTENT_NEEDED_RESTO);
        IntentFilter filter_2 = new IntentFilter(INTENT_NEEDED_ATTR);
        registerReceiver(receiver, filter_1, PERMISSION_CUSTOM, null);
        registerReceiver(receiver, filter_2, PERMISSION_CUSTOM, null);

        mRestoArray = getResources().getStringArray(R.array.Restaurants);
        mRestoLinkArray = getResources().getStringArray(R.array.RestaurantLinks);


        fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (savedInstanceState == null) {
                addRestoListFragment();
            } else {
                selectedIndex = savedInstanceState.getInt("selectedIndex");
                addRestoListFragment();
                Log.i(TAG, "onCreate: Saved found in portrait mode" + savedInstanceState.getInt("selectedIndex"));
                if (savedInstanceState.getInt("selectedIndex") >= 0) {
                    Log.i(TAG, "onCreate: Creating webView for saved ID " + savedInstanceState.getInt("selectedIndex"));
                    addWebviewFragement(savedInstanceState.getInt("selectedIndex"));
                }
            }
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            if (savedInstanceState == null) {
                addRestoListFragment();
            } else {
                Log.i(TAG, "onCreate: LandSaved" + fragmentManager.getBackStackEntryCount());
                selectedIndex = savedInstanceState.getInt("selectedIndex");
                addRestoListFragment();
                if (selectedIndex >= 0) {
                    addWebviewFragement(savedInstanceState.getInt("selectedIndex"));
                    setLayout();
                }
            }
            fragmentManager.addOnBackStackChangedListener(
                    new FragmentManager.OnBackStackChangedListener() {
                        @Override
                        public void onBackStackChanged() {
                            setLayout();
                        }
                    }
            );
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        menu.findItem(R.id.restoMenuItem).setEnabled(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.overflow_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.restoMenuItem:
                Toast.makeText(RestoActivity.this, "You are already in that activity", Toast.LENGTH_LONG).show();
                break;
            case R.id.attrMenuItem:
                Toast.makeText(RestoActivity.this, "Switching", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RestoActivity.this, AttrActivity.class);
                finish();
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (selectedIndex >= 0) {
            outState.putInt("selectedIndex", selectedIndex);
            Log.i(TAG, "onSaveInstanceState: " + selectedIndex);
        } else {
            outState.putInt("selectedIndex", -1);
            Log.i(TAG, "onSaveInstanceState: " + selectedIndex);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState: ");
        selectedIndex = savedInstanceState.getInt("selectedIndex");
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart: ");
        super.onStart();
        if (selectedIndex >= 0) {
            if (RestoWebFragment.getShownIndex() != selectedIndex) {
                RestoWebFragment.showWebpageAtIndex(selectedIndex);
            }
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                RestoListFragment.setSelection(selectedIndex);
                RestoListFragment.getListView().setItemChecked(selectedIndex, true);
            }
        }
    }

    private void addRestoListFragment() {
        Log.i(TAG, "addRestoListFragment: Added");
        fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putStringArray("listData", mRestoArray);
        RestoListFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainer, RestoListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onListSelection(int index) {
        if (selectedIndex != index) {
            selectedIndex = index;
            addWebviewFragement(index);
        }
    }

    private void addWebviewFragement(int index) {
        Log.i(TAG, "addWebviewFragement: " + index);
        fragmentTransaction = fragmentManager.beginTransaction();
        RestoWebFragment = new WebpageFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("webpageData", mRestoLinkArray);
        RestoWebFragment.setArguments(bundle);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentTransaction.replace(R.id.fragmentContainer2, RestoWebFragment);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            fragmentTransaction.replace(R.id.fragmentContainer, RestoWebFragment);
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
        Log.i(TAG, "addWebviewFragement: after adding webview Backstack: " + fragmentManager.getBackStackEntryCount());
        if (RestoWebFragment.getShownIndex() != index) {
            RestoWebFragment.showWebpageAtIndex(index);
        }
    }

    private void setLayout() {
        mListLayout = (FrameLayout) findViewById(R.id.fragmentContainer);
        mWebViewLayout = (FrameLayout) findViewById(R.id.fragmentContainer2);
        if (!RestoWebFragment.isAdded()) {
            mListLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mWebViewLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            mListLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            mWebViewLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2f));
        }
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop: " + fragmentManager.getBackStackEntryCount());
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Log.i(TAG, "onStop: " + fragmentManager.getBackStackEntryCount());
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed: ");
        super.onBackPressed();
        if (selectedIndex >= 0) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                RestoListFragment.getListView().setItemChecked(selectedIndex, false);
            selectedIndex = -1;
            Log.i(TAG, "onBackPressed: " + selectedIndex);
        }
    }
}
