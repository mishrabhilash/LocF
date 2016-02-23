package com.maststudio.locf.restproxy;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Hrishi on 08/09/15.
 */
public class Helper {

    private static Gson gson = new Gson();


    public static String toJson(Object req) {
        try {
            if (null == req) return "";

            return gson.toJson(req);

        } catch (Exception ex) {
            return "";
        }
    }

    public static <T> T fromJson(String jsonInString, Class<T> responseType) {
        try {

            if (jsonInString == null || !jsonInString.isEmpty()) {
                return (T) gson.fromJson(jsonInString, responseType);
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public static void postReq(HttpURLConnection urlConnection, Object req) {
        OutputStreamWriter out = null;
        try {
            out = new OutputStreamWriter(urlConnection.getOutputStream());
            String jsonInString = "";
            if (req instanceof JSONObject) {
                jsonInString = req.toString();
            } else {
                jsonInString = Helper.toJson(req);
            }

            out.write(jsonInString);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HttpURLConnection getHttpClient(String serverAPI, String methodType) {
        try {
            URL url = new URL(serverAPI);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("content-type", "application/json");
            if (methodType.equals(HTTPVerb.POST)) {
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setInstanceFollowRedirects(false);
            }
            urlConnection.setRequestMethod(methodType);
            return urlConnection;
        } catch (Exception ex) {
            Log.i("", ex.getMessage());
            return null;
        }
    }

    private static HttpURLConnection addHeaders(HttpURLConnection urlConnection, Map<String, String> headers) {
        try {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                urlConnection.setRequestProperty(key, value);
            }
        } catch (Exception ex) {
            Log.e("addHeader ", ex.getMessage());
        }
        return urlConnection;
    }

    public static <T> void execute(HttpURLConnection urlConn, final Object req, final Map<String, String> headers, final Class<T> responseType, final RestProxyCallback<T> callback) {

        class ExecuteTask extends AsyncTask<HttpURLConnection, Void, String> {

            @Override
            protected String doInBackground(HttpURLConnection... params) {
                HttpURLConnection urlConnection = params[0];
                long stopwatch = System.nanoTime();
                Log.i("restProx", "calling");
                StringBuilder sb = new StringBuilder();
                try {
                    if (headers != null && headers.size() > 0) {
                        urlConnection = addHeaders(urlConnection, headers);
                    }

                    if (urlConnection.getRequestMethod().equals(HTTPVerb.POST) && req != null) {
                        postReq(urlConnection, req);

                    }


                    int HttpResult = urlConnection.getResponseCode();
                    if (HttpResult == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(
                                urlConnection.getInputStream(), "utf-8"));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        br.close();

                        System.out.println("" + sb.toString());

                        Log.i("restProx", (System.nanoTime() - stopwatch) + "");
                        return sb.toString();
                    } else {
                        System.out.println(urlConnection.getResponseMessage());
                        callback.onFailure(HttpResult);
                    }

                } catch (Exception ex) {
                    callback.onFailure(ex);
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                callback.onProgress();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                T t1 = null;

                if (result != null) {
                    try {
                        t1 = responseType.newInstance();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    if (t1 instanceof String) {
                        t1 = (T) result;
                    } else {
                        t1 = (T) fromJson(result, responseType);
                    }
                    if (t1 != null)
                        callback.onSuccess(t1);
                    else
                        callback.onFailure(new Exception("Deserialise failed"));
                }

            }
        }
        ;
        new ExecuteTask().execute(urlConn);
    }

}
