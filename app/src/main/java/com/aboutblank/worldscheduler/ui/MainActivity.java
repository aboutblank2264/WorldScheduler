package com.aboutblank.worldscheduler.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.WorldApplication;
import com.aboutblank.worldscheduler.ui.screens.ClockListFragment;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String LOG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Keyboard.hideKeyboard(this);

        initializeToolbars();

        Log.d(LOG, "Setting application FragmentManager");
        MainFragmentManager manager = ((WorldApplication) getApplication()).setMainActivity(this);

        manager.changeFragmentView(new ClockListFragment());
    }

    private void initializeToolbars() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onError(String name, Throwable throwable) {
        if (throwable != null) {
            Log.e(name, "", throwable);
            Toast.makeText(this, throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        } else {
            Log.e(name, "Unknown error, this message should never happen.");
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show();
        }
    }
}
