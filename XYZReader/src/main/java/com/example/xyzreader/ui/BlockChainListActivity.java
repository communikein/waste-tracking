package com.example.xyzreader.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.xyzreader.R;
import com.example.xyzreader.data.model.Thing;
import com.example.xyzreader.databinding.ActivityBlockChainListBinding;
import com.example.xyzreader.viewmodel.BlockChainListViewModel;
import com.example.xyzreader.viewmodel.factory.BlockChainListViewModelFactory;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class BlockChainListActivity extends AppCompatActivity
        implements BlockChainListAdapter.BlockClickCallback {

    private ActivityBlockChainListBinding mBinding;

    /* */
    @Inject
    BlockChainListViewModelFactory viewModelFactory;

    /* */
    private BlockChainListViewModel mViewModel;

    String[] mWastesIds;
    BlockChainListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_block_chain_list);
        mViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(BlockChainListViewModel.class);

        initToolbar();

        mAdapter = new BlockChainListAdapter(this);
        mAdapter.setHasStableIds(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.setAdapter(mAdapter);

        mViewModel.getBlockChain().observe(this, blockChain -> {
            if (blockChain != null) {
                mAdapter.setList(blockChain);

                mWastesIds = new String[blockChain.size()];
                for (int i=0; i<blockChain.size(); i++) {
                    JsonObject block = blockChain.get(i);

                    String id = block.getAsJsonArray("identities").get(0).getAsString();
                    mWastesIds[i] = id;
                }

            }
            else {
                mAdapter.setList(new ArrayList<>());

                mWastesIds = null;
            }

            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onListItemClick(View view, Thing block) { }


    private void initToolbar() {
        ViewCompat.setElevation(mBinding.appbar, getResources().getDimension(R.dimen.app_bar_elevation));

        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

}
