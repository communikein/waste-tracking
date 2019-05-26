package com.example.xyzreader.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xyzreader.R;
import com.example.xyzreader.data.model.Thing;
import com.example.xyzreader.databinding.ListItemBlockBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class BlockChainListAdapter extends RecyclerView.Adapter<BlockChainListAdapter.ViewHolder> {


    @Nullable
    private final BlockClickCallback mBlockClickCallback;
    public interface BlockClickCallback {
        void onListItemClick(View view, Thing block);
    }

    private ArrayList<JsonObject> mData;

    BlockChainListAdapter(@NonNull AppCompatActivity callback) {
        this.mBlockClickCallback = (BlockClickCallback) callback;
        this.mData = new ArrayList<>();
    }

    public void setList(ArrayList<JsonObject> blocks) {
        mData = blocks;
    }

    public ArrayList<JsonObject> getList() {
        return mData;
    }

    @Override
    @NonNull
    public BlockChainListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBlockBinding mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_block, parent, false);
        mBinding.setCallback(mBlockClickCallback);

        return new BlockChainListAdapter.ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BlockChainListAdapter.ViewHolder holder, int position) {
        Thing block = new Gson().fromJson(mData.get(position), Thing.class);
        holder.mBinding.setBlock(block);
        holder.bindToData();
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ListItemBlockBinding mBinding;

        ViewHolder(ListItemBlockBinding binding) {
            super(binding.getRoot());

            this.mBinding = binding;
        }

        void bindToData() {
            Thing block = mBinding.getBlock();
            mBinding.blockJson.setText(block.getData().toString());
        }
    }
}