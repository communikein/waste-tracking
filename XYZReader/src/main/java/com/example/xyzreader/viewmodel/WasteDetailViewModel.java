package com.example.xyzreader.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.xyzreader.data.BlockChainRepository;
import com.example.xyzreader.data.model.Waste;

import java.util.List;

import javax.inject.Inject;

public class WasteDetailViewModel extends ViewModel {

    private final BlockChainRepository mRepository;

    private final LiveData<List<Waste>> mData;
    private final LiveData<Boolean> mLoading;

    private int mSelectedWastePosition;

    @Inject
    public WasteDetailViewModel(BlockChainRepository repository) {
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


    public Waste getWaste(List<Waste> articles, String id) {
        for (Waste article : articles) {
            if (article.getId().equals(id))
                return article;
        }

        return null;
    }

    public Waste getSelectedWaste() {
        if (mData.getValue() != null)
            return mData.getValue().get(mSelectedWastePosition);
        return null;
    }
}
