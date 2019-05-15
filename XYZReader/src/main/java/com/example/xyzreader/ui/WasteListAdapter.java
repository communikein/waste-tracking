package com.example.xyzreader.ui;


import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xyzreader.R;
import com.example.xyzreader.data.FakeDataHelper;
import com.example.xyzreader.data.model.User;
import com.example.xyzreader.data.model.Waste;
import com.example.xyzreader.databinding.ListItemWasteBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class WasteListAdapter extends RecyclerView.Adapter<WasteListAdapter.ViewHolder> {

    private String thumbnailTransitionName, shadeTransitionName,
            titleTransitionName, byLineTransitionName;

    @Nullable
    private final WasteClickCallback mWasteClickCallback;
    public interface WasteClickCallback {
        void onListItemClick(View view, Waste waste, float score);
    }

    private ArrayList<Waste> mData;

    WasteListAdapter(@NonNull AppCompatActivity callback) {
        this.mWasteClickCallback = (WasteClickCallback) callback;
        this.mData = new ArrayList<>();

        thumbnailTransitionName = callback.getString(R.string.transition_article_image);
        shadeTransitionName = callback.getString(R.string.transition_article_shade);
        titleTransitionName = callback.getString(R.string.transition_article_title);
        byLineTransitionName = callback.getString(R.string.transition_article_byline);
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

        ViewCompat.setTransitionName(mBinding.userScoreThumbnail, thumbnailTransitionName);
        ViewCompat.setTransitionName(mBinding.wasteType, shadeTransitionName);
        ViewCompat.setTransitionName(mBinding.wasteDetails, titleTransitionName);
        ViewCompat.setTransitionName(mBinding.wasteQuantity, byLineTransitionName);

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
            mBinding.wasteType.setText(waste.getType());
            mBinding.wasteDetails.setText(waste.getParameters());
            mBinding.wasteQuantity.setText(String.valueOf(waste.getWeight()));
            mBinding.userScoreThumbnail.setImageResource(feelingResource);
        }
    }
}