package com.maststudio.locf;

/**
 * Created by mishrabhilash on 2/23/16.
 */
public interface LocationFetchCallback {
    public void onSuccess(NGLocation ngLocation);
    public void onFailure(int errorCode);
    public void onException(Exception ex);
}
