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
import com.example.xyzreader.data.FakeDataHelper;
import com.example.xyzreader.data.model.Waste;
import com.example.xyzreader.databinding.ListItemWasteBinding;

import java.util.ArrayList;

public class WasteListAdapter extends RecyclerView.Adapter<WasteListAdapter.ViewHolder> {

    @Nullable
    private final WasteClickCallback mWasteClickCallback;
    public interface WasteClickCallback {
        void onListItemClick(View view, Waste waste, float score);
    }

    private ArrayList<Waste> mData;

    WasteListAdapter(@NonNull AppCompatActivity callback) {
        this.mWasteClickCallback = (WasteClickCallback) callback;
        this.mData = new ArrayList<>();
    }

    public void setList(ArrayList<Waste> wastes) {
        mData = wastes;
    }

    public ArrayList<Waste> getList() {
        return mData;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemWasteBinding mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_waste, parent, false);
        mBinding.setCallback(mWasteClickCallback);

        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Waste waste = mData.get(position);
        float score = (float) Math.random() * 10;

        holder.mBinding.setWaste(waste);
        holder.mBinding.setScore(score);

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

        ListItemWasteBinding mBinding;

        ViewHolder(ListItemWasteBinding binding) {
            super(binding.getRoot());

            this.mBinding = binding;
        }

        void bindToData() {
            int feelingResource = FakeDataHelper.pickFeelingResource(mBinding.getScore());

            Waste waste = mBinding.getWaste();
            mBinding.wasteType.setText(waste.printType(mBinding.getRoot().getContext()));
            mBinding.wasteDetails.setText(waste.getParameters());
            mBinding.wasteQuantity.setText(String.valueOf(waste.getWeight()));
            mBinding.userScoreThumbnail.setImageResource(feelingResource);
        }
    }
}