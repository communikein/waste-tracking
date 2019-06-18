package com.communikein.wastetrackingproducer.viewmodel.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.communikein.wastetrackingproducer.data.BlockChainRepository;
import com.communikein.wastetrackingproducer.viewmodel.BlockChainListViewModel;

import javax.inject.Inject;

public class BlockChainListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final BlockChainRepository mRepository;

    @Inject
    public BlockChainListViewModelFactory(BlockChainRepository repository) {
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new BlockChainListViewModel(mRepository);
    }

}
