package cn.foxnickel.listentome.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/27.
 */

public class GetJsonFromServerTask extends AsyncTask<String, Void, String> {
    private final String TAG = getClass().getSimpleName();
    private Response response = null;

    @Override
    protected String doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(params[0]).build();
        String respStr = null;
        try {
            response = client.newCall(request).execute();
            respStr = response.body().string();
            Log.i(TAG, response.body().contentType().toString());
            Log.i(TAG, "doInBackground: message: " + response.message());
            Log.i(TAG, "doInBackground: code: " + response.code());
            Log.i(TAG, "doInBackground: body: " + respStr);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return respStr;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
