package com.example.neeraj_intellify_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.neeraj_intellify_assignment.models.For_data_collect;
import com.example.neeraj_intellify_assignment.viewmodel.mainActivity_vm;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

//    List<ModelShowPost> postList;

    AdapterUser adapterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));





        Observer<For_data_collect> observer = new Observer<For_data_collect>() {
            @Override
            public void onChanged(For_data_collect for_data_collect) {

//                for (int i = 0; i<for_data_collect.getResults().size();i++){
//
//                    Log.d("service_check", for_data_collect.getResults().get(i).getName().getFirst());
//
//                }


                adapterUser =  new AdapterUser(getApplicationContext(),for_data_collect.getResults());
                recyclerView.setAdapter(adapterUser);
            }
        };


        mainActivity_vm  mainActivityVm = new ViewModelProvider(MainActivity.this).get(mainActivity_vm.class);

        mainActivityVm.forMutableLiveData.observe(MainActivity.this,observer);


    }
}