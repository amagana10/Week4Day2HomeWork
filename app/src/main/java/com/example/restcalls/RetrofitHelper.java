package com.example.restcalls;

import com.example.restcalls.model.users.UserResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.example.restcalls.UrlConstants.*;

public class RetrofitHelper {
    //create retrofit client
    private Retrofit getRetrofitClient(){
        return new  Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    //build the retrofit service
    private GetRandomUsers  getUserResponse(){
        return getRetrofitClient().create(GetRandomUsers.class);
    }
    //get the response
    public Call<UserResponse> getRandomUsers(String numOfResponses){
        return getUserResponse().getRandomUsers("5");
    }

    public interface GetRandomUsers{
        @GET(PATH)
        Call<UserResponse> getRandomUsers(@Query(QUERy_RESULTS) String numOfResponse);
    }
}
