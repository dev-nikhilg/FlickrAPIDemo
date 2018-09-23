package com.example.nik.flickrapidemo.Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nik.flickrapidemo.MyApplication;
import com.example.nik.flickrapidemo.R;
import com.example.nik.flickrapidemo.Utils.CommonFunctionsUtil;
import com.example.nik.flickrapidemo.Utils.Constants;
import com.example.nik.flickrapidemo.data.ImageResponseDto;
import com.example.nik.flickrapidemo.data.NetworkResponse;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewCallbackInterface {

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

    // Recycler view items
    private RecyclerView recyclerView;
    private int RECYCLER_VIEW_NUM_COLUMNS = 3;
    private ImagesAdapter adapter;

    private ProgressBar progressBar;

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
        setupRecyclerView();
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

        viewModel.getTitle().observe(this, s -> titleText.setText(s));

        viewModel.getLastSearchQuery().observe(this, s -> searchEditText.setText(s));
    }

    private void setupRecyclerView() {
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, RECYCLER_VIEW_NUM_COLUMNS));
        adapter = new ImagesAdapter(new ArrayList<>(), ((MyApplication) getApplication()).getImageUtils(), this);
        recyclerView.setAdapter(adapter);

        viewModel.getImages().observe(this, new Observer<NetworkResponse<ImageResponseDto>>() {
            @Override
            public void onChanged(@Nullable NetworkResponse<ImageResponseDto> response) {
                switch (response.status) {
                    case Constants.NETWORK_CALL_INPROGRESS:
                        if (response.data == null || response.data.getImageList().size() <= 0) {
                            adapter.resetList();
                            progressBar.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                        return;
                    case Constants.NETWORK_CALL_ERROR:
                        Toast.makeText(MainActivity.this, response.message, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        return;
                    case Constants.NETWORK_CALL_SUCCESS:
                        adapter.addItemsToList(response.data.getImageList());
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        return;
                }
            }
        });

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
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
                    startSearch(inputString);
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
    }

    private void startSearch(String searchQuery) {
        endSearchMode();
        viewModel.searchImages(searchQuery);
    }

    @Override
    public void loadMore() {
        viewModel.serachMoreImages();
    }
}
