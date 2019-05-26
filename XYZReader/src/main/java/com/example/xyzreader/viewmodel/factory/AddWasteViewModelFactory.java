package com.example.xyzreader.viewmodel.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.xyzreader.data.BlockChainRepository;
import com.example.xyzreader.viewmodel.AddWasteViewModel;

import javax.inject.Inject;

public class AddWasteViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final BlockChainRepository mRepository;

    @Inject
    public AddWasteViewModelFactory(BlockChainRepository repository) {
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddWasteViewModel(mRepository);
    }

}