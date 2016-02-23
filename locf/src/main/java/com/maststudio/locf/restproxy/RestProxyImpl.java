package com.maststudio.locf.restproxy;

import android.util.Log;

import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Created by Hrishi on 08/09/15.
 */
public class RestProxyImpl implements IRestProxy {
    private static Gson gson = new Gson();

    @Override
    public <T1> void doPost(String serverAPI, Object req, Class<T1> responseType, RestProxyCallback<T1> callback) {
        String methodType = HTTPVerb.POST;
        try {
            HttpURLConnection urlConnection = Helper.getHttpClient(serverAPI, HTTPVerb.POST);

            Helper.execute(urlConnection, req, null, responseType, callback);

        } catch (Exception ex) {
            Log.i("doPost", ex.getMessage());
        }

    }

    public <T1> void doPost(String serverAPI, Object req, Map<String, String> headers, Class<T1> responseType, final RestProxyCallback<T1> callback) {
        String methodType = HTTPVerb.POST;
        try {
            HttpURLConnection urlConnection = Helper.getHttpClient(serverAPI, HTTPVerb.POST);

            Helper.execute(urlConnection, req, headers, responseType, callback);

        } catch (Exception ex) {
            Log.i("doPost", ex.getMessage());
        }
    }

    @Override
    public <T1> void doGet(String serverAPI, Class<T1> responseType, RestProxyCallback<T1> callback) {
        try {
            HttpURLConnection urlConnection = Helper.getHttpClient(serverAPI, HTTPVerb.GET);
            Helper.execute(urlConnection, null, null, responseType, callback);
        } catch (Exception ex) {
        }
    }

    @Override
    public <T1> void doGet(String serverAPI, Map<String, String> headers, Class<T1> responseType, RestProxyCallback<T1> callback) {
        try {
            HttpURLConnection urlConnection = Helper.getHttpClient(serverAPI, HTTPVerb.GET);
            Helper.execute(urlConnection, null, headers, responseType, callback);
        } catch (Exception ex) {
        }
    }

    @Override
    public <T> void doPostSecret(String serverAPI, Object req, Class<T> responseType, RestProxyCallback<T> callback) {

    }

    @Override
    public <T> void doPostSecret(String serverAPI, Object req, Map<String, String> headers, Class<T> responseType, RestProxyCallback<T> callback) {
        String methodType = HTTPVerb.POST;
        headers.put("secret","");
        try {
            HttpURLConnection urlConnection = Helper.getHttpClient(serverAPI, HTTPVerb.POST);

            Helper.execute(urlConnection, req, headers, responseType, callback);

        } catch (Exception ex) {
            Log.i("doPost", ex.getMessage());
        }
    }

    @Override
    public <T> void doGetSecret(String serverAPI, Class<T> responseType, RestProxyCallback<T> callback) {

    }

    @Override
    public <T> void doGetSecret(String serverAPI, Map<String, String> headers, Class<T> responseType, RestProxyCallback<T> callback) {

    }
}
