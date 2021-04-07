package com.example.neeraj_intellify_assignment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.neeraj_intellify_assignment.models.For_data_collect;
import com.example.neeraj_intellify_assignment.repository.mainActivity_repo;

public class mainActivity_vm extends AndroidViewModel {


    public MutableLiveData<For_data_collect> forMutableLiveData;


    public mainActivity_vm(@NonNull Application application) {
        super(application);
        mainActivity_repo mainActivityRepo = new mainActivity_repo();
        forMutableLiveData = mainActivityRepo.forMutableLiveData;

    }
}
