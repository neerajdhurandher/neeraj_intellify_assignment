package com.example.neeraj_intellify_assignment.restApiHelper;

import com.example.neeraj_intellify_assignment.models.For_data_collect;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface RestApiHelp {

    String BASE_URL = "https://randomuser.me/api/";
    String result = "?results=30";



    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    @GET(result)
    Call<For_data_collect> data_from_api();

}
