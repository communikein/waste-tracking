package com.example.xyzreader.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.xyzreader.R;
import com.example.xyzreader.data.contentprovider.BlockChainContract.BlockEntry;
import com.example.xyzreader.data.model.Block;
import com.example.xyzreader.databinding.ActivityBlockChainListBinding;
import com.example.xyzreader.viewmodel.BlockChainListViewModel;
import com.example.xyzreader.viewmodel.factory.BlockChainListViewModelFactory;

import org.json.JSONException;
import org.json.JSONObject;

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

        //int columnCount = getResources().getInteger(R.integer.list_column_count);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.setAdapter(mAdapter);

        mViewModel.getBlockChain().observe(this, blockChain -> {
            if (blockChain != null) {
                mAdapter.setList(new ArrayList<>(blockChain));

                mWastesIds = new String[blockChain.size()];
                for (int i=0; i<blockChain.size(); i++) {
                    JSONObject block = blockChain.get(i).getJson();
                    String id = null;

                    try {
                        if (block.has(BlockEntry.COLUMN_WASTE_ID))
                            id = block.getString(BlockEntry.COLUMN_WASTE_ID);

                        else if (block.has(BlockEntry.COLUMN_PRODUCER_ID))
                            id = block.getString(BlockEntry.COLUMN_PRODUCER_ID);

                        else if (block.has(BlockEntry.COLUMN_COLLECTOR_ID))
                            id = block.getString(BlockEntry.COLUMN_COLLECTOR_ID);

                        else if (block.has(BlockEntry.COLUMN_PREV_WASTE_ID))
                            id = block.getString(BlockEntry.COLUMN_PREV_WASTE_ID) + "-" +
                                    block.getString(BlockEntry.COLUMN_PREV_OWNER_ID) + "-" +
                                    block.getString(BlockEntry.COLUMN_NEXT_WASTE_ID) + "-" +
                                    block.getString(BlockEntry.COLUMN_NEXT_OWNER_ID);

                        else if (block.has(BlockEntry.COLUMN_TREATMENT_TYPE))
                            id = block.getString(BlockEntry.COLUMN_TREATMENT_TYPE);

                        else if (block.has(BlockEntry.COLUMN_LANDFILL_ID))
                            id = block.getString(BlockEntry.COLUMN_LANDFILL_ID);

                        else if (block.has(BlockEntry.COLUMN_POWER_ID))
                            id = block.getString(BlockEntry.COLUMN_POWER_ID);

                        else
                            id = block.getString(BlockEntry.COLUMN_RECYCLE_ID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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
    public void onListItemClick(View view, Block block) { }


    private void initToolbar() {
        ViewCompat.setElevation(mBinding.appbar, getResources().getDimension(R.dimen.app_bar_elevation));

        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

}