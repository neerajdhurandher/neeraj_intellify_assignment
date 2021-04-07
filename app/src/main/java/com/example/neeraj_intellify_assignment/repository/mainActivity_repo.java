package com.example.neeraj_intellify_assignment.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.neeraj_intellify_assignment.models.For_data_collect;
import com.example.neeraj_intellify_assignment.models.Results;
import com.example.neeraj_intellify_assignment.restApiHelper.RestApiHelp;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mainActivity_repo {

    public mainActivity_repo() {

        getdatafromApi();

    }

    private void getdatafromApi() {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                restApiHelp = RestApiHelp.retrofit.create(RestApiHelp.class);

                Call<For_data_collect> data_call = restApiHelp.data_from_api();

                data_call.enqueue(new Callback<For_data_collect>() {
                    @Override
                    public void onResponse(Call<For_data_collect> call, Response<For_data_collect> response) {

                        For_data_collect for_data_collect = response.body();

                        forMutableLiveData.setValue(for_data_collect);


                    }

                    @Override
                    public void onFailure(Call<For_data_collect> call, Throwable t) {

                        Log.d("service_check","on Failure "+t);
                    }
                });




            }
        });
    }

    private final Executor executor = Executors.newSingleThreadExecutor();
    private RestApiHelp restApiHelp;
    public MutableLiveData<For_data_collect> forMutableLiveData = new MutableLiveData<>();

}
