package com.communikein.wastetrackingproducer.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.communikein.wastetrackingproducer.data.BlockChainRepository;
import com.communikein.wastetrackingproducer.data.model.Waste;

import java.util.List;

import javax.inject.Inject;

public class WastesListViewModel extends ViewModel {

    private final BlockChainRepository mRepository;

    private final LiveData<List<Waste>> mData;
    private final LiveData<Boolean> mLoading;

    @Inject
    public WastesListViewModel(BlockChainRepository repository) {
        this.mRepository = repository;

        mData = mRepository.getWastesAsObserver();
        mLoading = mRepository.getLoadingState();
    }


    public LiveData<List<Waste>> getWastes() {
        return mData;
    }

    public LiveData<Boolean> isLoading() {
        return mLoading;
    }

    public void refreshWastes() {
        mRepository.fetchWastes();
    }

}
