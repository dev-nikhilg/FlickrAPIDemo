package com.example.nik.flickrapidemo.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nik.flickrapidemo.R;
import com.example.nik.flickrapidemo.Utils.CommonFunctionsUtil;

public class MainActivity extends AppCompatActivity {

    /**
     * viewmodel
     */
    private MainActivityViewModel viewModel;

    /**
     * layout items
     */
    // Toolbar items
    private ImageView searchIcon;
    private ImageView resetSearchIcon;
    private ImageView backIcon;
    private TextView titleText;
    private EditText searchEditText;

    // local variables
    private String lastSearchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init viewmodel
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.init(getApplicationContext());

        setupLayout();
        setupClickListeners();
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
        lastSearchString = "";
    }

    private void setupClickListeners() {
        searchIcon.setOnClickListener(v -> startSearchMode());

        resetSearchIcon.setOnClickListener(v -> searchEditText.setText(""));

        backIcon.setOnClickListener(v -> endSearchMode());

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                handled = true;
                String inputString = v.getText().toString();
                if (CommonFunctionsUtil.isValidString(inputString)) {
                    lastSearchString = inputString;
                    endSearchMode();
                }
            }
            return handled;
        });
    }

    private void startSearchMode() {
        searchIcon.setVisibility(View.GONE);
        titleText.setVisibility(View.GONE);

        resetSearchIcon.setVisibility(View.VISIBLE);
        backIcon.setVisibility(View.VISIBLE);
        searchEditText.setVisibility(View.VISIBLE);
        searchEditText.setText(lastSearchString);
        searchEditText.requestFocus();
        searchEditText.setSelection(searchEditText.getText().length());
        CommonFunctionsUtil.showKeyboard(this, searchEditText);
    }

    private void endSearchMode() {
        resetSearchIcon.setVisibility(View.GONE);
        backIcon.setVisibility(View.GONE);
        searchEditText.setVisibility(View.GONE);
        searchEditText.clearFocus();
        CommonFunctionsUtil.hideKeyboard(this);

        searchIcon.setVisibility(View.VISIBLE);
        titleText.setVisibility(View.VISIBLE);
        titleText.setText(lastSearchString);
    }
}
