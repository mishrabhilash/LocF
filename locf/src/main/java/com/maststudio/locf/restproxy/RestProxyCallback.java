package com.maststudio.locf.restproxy;

/**
 * Created by Hrishi on 08/09/15.
 */
public interface RestProxyCallback<T> {

    void onSuccess(T response);

    void onFailure(Exception ex);

    void onFailure(int errorCode);

    void onProgress();
}
