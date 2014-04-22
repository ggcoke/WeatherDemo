package com.ggcoke.weatherdemo.util;

import android.os.AsyncTask;

public class MyAsynTask extends AsyncTask<String, Integer, String> {
    private static final String LOG_TAG = MyAsynTask.class.getSimpleName();
    private MyAsynTaskCallback mCallback;
    private int mFlag;

    public MyAsynTask(MyAsynTaskCallback callback, int flag) {
        this.mCallback = callback;
        this.mFlag = flag;
    }

    @Override
    protected String doInBackground(String... params) {
        return getData(params[0]);
    }

    @Override
    protected void onPostExecute(final String result) {

        if (result != null && result.length() > 0) {
            mCallback.success(result, mFlag);
        } else {
            mCallback.failed(result, mFlag);
        }
        super.onPostExecute(result);
    }

    private String getData(String request) {
        String response = "";
        switch (mFlag) {
            default:
                response = MyConstants.ERROR_INVALIDED_REQUEST;
                break;
        }
        return response;
    }
}
