package com.maststudio.locf.restproxy;


import java.util.Map;

/**
 * Created by Hrishi on 08/09/15.
 */
public interface IRestProxy{

    <T> void doPost(String serverAPI, Object req, Class<T> responseType, final RestProxyCallback<T> callback);

    <T> void doPost(String serverAPI, Object req, Map<String, String> headers, Class<T> responseType, RestProxyCallback<T> callback);

    <T> void doGet(String serverAPI, Class<T> responseType, final RestProxyCallback<T> callback);

    <T> void doGet(String serverAPI, Map<String, String> headers, Class<T> responseType, final RestProxyCallback<T> callback);

    <T> void doPostSecret(String serverAPI, Object req, Class<T> responseType, final RestProxyCallback<T> callback);

    <T> void doPostSecret(String serverAPI, Object req, Map<String, String> headers, Class<T> responseType, RestProxyCallback<T> callback);

    <T> void doGetSecret(String serverAPI, Class<T> responseType, final RestProxyCallback<T> callback);

    <T> void doGetSecret(String serverAPI, Map<String, String> headers, Class<T> responseType, final RestProxyCallback<T> callback);

}
