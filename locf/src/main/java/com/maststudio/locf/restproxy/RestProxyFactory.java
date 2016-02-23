package com.maststudio.locf.restproxy;

/**
 * Created by Hrishi on 09/09/15.
 */
public class RestProxyFactory {

    public static IRestProxy getClient() {
        return new RestProxyImpl();
    }
}
