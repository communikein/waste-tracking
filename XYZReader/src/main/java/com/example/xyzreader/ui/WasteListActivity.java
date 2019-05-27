package com.example.xyzreader.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.xyzreader.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import dagger.android.AndroidInjection;

import com.example.xyzreader.data.model.Waste;
import com.example.xyzreader.databinding.ActivityWasteListBinding;
import com.example.xyzreader.viewmodel.WastesListViewModel;
import com.example.xyzreader.viewmodel.factory.WasteListViewModelFactory;

import javax.inject.Inject;

/**
 * An activity representing a list of Articles. This activity has different presentations for
 * handset and tablet-size devices. On handsets, the activity presents a list of items, which when
 * touched, lead to a {@link WasteDetailsActivity} representing item details. On tablets, the
 * activity presents a grid of items as cards.
 */
public class WasteListActivity extends AppCompatActivity
        implements WasteListAdapter.WasteClickCallback, SwipeRefreshLayout.OnRefreshListener {

    private ActivityWasteListBinding mBinding;

    /* */
    @Inject
    WasteListViewModelFactory viewModelFactory;

    /* */
    private WastesListViewModel mViewModel;

    WasteListAdapter mAdapter;

    String[] mWastesIds;
    private boolean mIsRefreshing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_waste_list);
        mViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(WastesListViewModel.class);

        initToolbar();

        mAdapter = new WasteListAdapter(this);
        mAdapter.setHasStableIds(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);

        mWastesIds = null;
        mViewModel.getWastes().observe(this, wastes -> {
            if (wastes != null) {
                mAdapter.setList(new ArrayList<>(wastes));

                mWastesIds = new String[wastes.size()];
                for (int i=0; i<wastes.size(); i++)
                    mWastesIds[i] = wastes.get(i).getId();
            }
            else {
                mAdapter.setList(new ArrayList<>());

                mWastesIds = null;
            }

            mAdapter.notifyDataSetChanged();
        });

        if (savedInstanceState == null) {
            refresh();
        }

        mViewModel.isLoading().observe(this, loadingState -> {
            if (loadingState != null) {
                mIsRefreshing = loadingState;
                updateRefreshingUI();
            }
        });

        // Use default locale format
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        mBinding.detailsDate.setText(outputFormat.format(new Date()));

        mBinding.highscore.setOnClickListener(v -> {
            float score = (float) Math.random() * 10;
            onListItemClick(v, null, score);
        });

        mBinding.producer.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserDetailsActivity.class);
            ActivityCompat.startActivity(this, intent, null);
        });
    }

    @Override
    public void onListItemClick(View view, @Nullable Waste waste, float score) {
        Intent intent = new Intent(this, WasteDetailsActivity.class);
        if (waste != null)
            intent.putExtra(WasteDetailsActivity.ARG_WASTE, waste);
        intent.putExtra(WasteDetailsActivity.ARG_SCORE, score);

        //Start the Intent
        ActivityCompat.startActivity(this, intent, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent intent;
        switch (id) {
            case R.id.developer:
                intent = new Intent(this, BlockChainListActivity.class);
                ActivityCompat.startActivity(this, intent, null);

                return true;
            case R.id.add_waste:
                intent = new Intent(this, AddWasteActivity.class);
                ActivityCompat.startActivity(this, intent, null);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void initToolbar() {
        ViewCompat.setElevation(mBinding.appbar, getResources().getDimension(R.dimen.app_bar_elevation));

        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    private void refresh() {
        mViewModel.refreshWastes();
    }

    private void updateRefreshingUI() {
        mBinding.swipeRefreshLayout.setRefreshing(mIsRefreshing);
    }

    @Override
    public void onRefresh() {
        refresh();
    }
}
