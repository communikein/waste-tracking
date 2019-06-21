package com.communikein.wastetrackingproducer.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.communikein.wastetrackingproducer.data.BlockChainRepository;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * This class provides the single source of truth for the UI to interface with the data used by the
 * application, being it the DB or the Web APIs.
 * Each Activity will have its own ViewModel.
 */
public class BlockChainListViewModel extends ViewModel {

    private final BlockChainRepository mRepository;

    private final LiveData<ArrayList<JsonObject>> mData;

    @Inject
    public BlockChainListViewModel(BlockChainRepository repository) {
        this.mRepository = repository;

        mData = mRepository.getBlockChainAsObserver();
    }


    public LiveData<ArrayList<JsonObject>> getBlockChain() {
        return mData;
    }

}
