package com.example.restcalls;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

public class AsyncTaskForRestCall extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... strings) {
        try {
            return Okhttp3Helper.getSyncroniousOkhttpResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("TAG", s);
    }
}
