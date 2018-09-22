package com.example.nik.flickrapidemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Toolbar items
    private ImageView searchIcon;
    private ImageView resetSearchIcon;
    private ImageView backIcon;
    private TextView titleText;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupLayout();
    }

    private void setupLayout() {
        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get references to toolbar layout items
        searchIcon = findViewById(R.id.searchIcon);
        resetSearchIcon = findViewById(R.id.resetSearchIcon);
        backIcon = findViewById(R.id.backIcon);
        titleText = findViewById(R.id.titleText);
        searchEditText = findViewById(R.id.searchEditText);

        // init toolbar items
        resetSearchIcon.setVisibility(View.GONE);
        backIcon.setVisibility(View.GONE);
        searchEditText.setVisibility(View.GONE);
        titleText.setText(getString(R.string.app_name));
    }
}
