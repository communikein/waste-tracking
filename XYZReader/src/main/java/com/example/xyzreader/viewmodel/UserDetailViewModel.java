package com.example.xyzreader.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.xyzreader.data.BlockChainRepository;
import com.example.xyzreader.data.model.User;

import javax.inject.Inject;

public class UserDetailViewModel extends ViewModel {

    private final BlockChainRepository mRepository;

    private final MutableLiveData<User> mData;

    @Inject
    public UserDetailViewModel(BlockChainRepository repository) {
        this.mRepository = repository;

        mData = new MutableLiveData<>();
        mData.postValue(new User("ASZ-123", "Prova 1", 0.5));
    }


    public LiveData<User> getUser() {
        return mData;
    }

}