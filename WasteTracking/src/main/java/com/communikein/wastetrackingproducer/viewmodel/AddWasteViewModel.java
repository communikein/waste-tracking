package com.communikein.wastetrackingproducer.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.communikein.wastetrackingproducer.data.BlockChainRepository;
import com.communikein.wastetrackingproducer.data.model.Waste;

import javax.inject.Inject;

/**
 * This class provides the single source of truth for the UI to interface with the data used by the
 * application, being it the DB or the Web APIs.
 * Each Activity will have its own ViewModel.
 */
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
