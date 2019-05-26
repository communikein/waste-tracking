package com.example.xyzreader.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.example.xyzreader.data.BlockChainRepository;
import com.example.xyzreader.data.model.Waste;

import javax.inject.Inject;

public class AddWasteViewModel extends ViewModel {

    private final BlockChainRepository mRepository;

    @Inject
    public AddWasteViewModel(BlockChainRepository repository) {
        this.mRepository = repository;
    }


    public void addWaste(Waste waste){
        mRepository.addWaste(waste);
    }

}
