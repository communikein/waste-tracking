package com.example.xyzreader.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.xyzreader.data.BlockChainRepository;
import com.example.xyzreader.data.model.Block;

import java.util.List;

import javax.inject.Inject;

public class BlockChainListViewModel extends ViewModel {

    private final BlockChainRepository mRepository;

    private final LiveData<List<Block>> mData;

    @Inject
    public BlockChainListViewModel(BlockChainRepository repository) {
        this.mRepository = repository;

        mData = mRepository.getBlockChainAsObserver();
    }


    public LiveData<List<Block>> getBlockChain() {
        return mData;
    }

}
