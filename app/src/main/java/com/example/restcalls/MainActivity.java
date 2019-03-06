package com.example.restcalls;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.restcalls.model.users.Result;
import com.example.restcalls.model.users.UserResponse;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyRecyclerViewAdapter myRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);





        Thread restCallThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = HttpUrlConnectionHelper.getJsonUsingHttpURLConn();
                    Gson gson = new Gson();
                    UserResponse userResponse = gson.fromJson(json,UserResponse.class);
                    Log.d("TAG", json);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        restCallThread.start();


        //Make sync. okhttp request
        AsyncTaskForRestCall asyncTaskForRestCall = new AsyncTaskForRestCall();
        asyncTaskForRestCall.execute();

        //make async okhttp request
        Okhttp3Helper.getAsyncOkHttpResponse();

        RetrofitHelper retrofitHelper = new RetrofitHelper();
        //Start Retrofit in a sync way to get our pojo response
        retrofitHelper.getRandomUsers("3").enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Result result = response.body().getResults().get(0);
                ArrayList results = new ArrayList();
                results.add(result);

                myRecyclerViewAdapter = new MyRecyclerViewAdapter(results);
                recyclerView.setAdapter(myRecyclerViewAdapter);
                String email = response.body().getResults().get(0).getEmail();



                Log.d("TAG_RETROFIT", "onResponse: " + email);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        //register to eventbus
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //unregister to event bus
        EventBus.getDefault().unregister(this);
    }
    //Subscribe to the posting event on EentBus that is passing a User Response
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userResponseEvent(UserResponse userResponse){
        Log.d("TAG_EVENT_RECEIVED", userResponse.getResults().get(0).getEmail());
    }
}
