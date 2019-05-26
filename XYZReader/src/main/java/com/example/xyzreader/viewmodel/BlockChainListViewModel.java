package com.example.xyzreader.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.xyzreader.data.BlockChainRepository;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import javax.inject.Inject;

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
