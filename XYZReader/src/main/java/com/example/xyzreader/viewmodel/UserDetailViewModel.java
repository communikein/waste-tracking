package com.example.xyzreader.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.xyzreader.data.BlockChainRepository;
import com.example.xyzreader.data.model.Producer;

import javax.inject.Inject;

public class UserDetailViewModel extends ViewModel {

    private final BlockChainRepository mRepository;

    private final MutableLiveData<Producer> mData;

    @Inject
    public UserDetailViewModel(BlockChainRepository repository) {
        this.mRepository = repository;

        mData = new MutableLiveData<>();
        mData.postValue(new Producer("ASZ-123", "Elia Maracani", 0.5, "Verdello, Italia", 4));
    }


    public LiveData<Producer> getUser() {
        return mData;
    }

}