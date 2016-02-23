package com.maststudio.locf;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import com.maststudio.locf.restproxy.CountryCodeMap;
import com.maststudio.locf.restproxy.IRestProxy;
import com.maststudio.locf.restproxy.RestProxyCallback;
import com.maststudio.locf.restproxy.RestProxyFactory;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by mishrabhilash on 2/23/16.
 */
public class LocationFetchAction {
    static NGLocation ngLocation = new NGLocation();
    public static void getLocation(Context context,final LocationFetchCallback cb){
        if (LocationFetchSettings.APIKEY == null) {
            cb.onException(new ApiKeyNotFoundException());
        }
        IRestProxy restProxy = RestProxyFactory.getClient();
        JSONObject jobject = new JSONObject();
        try {
            JSONObject carrierJson = new JSONObject();
            TelephonyManager tMgr = (TelephonyManager) (context).getSystemService(Context.TELEPHONY_SERVICE);
            GsmCellLocation cellLocation = (GsmCellLocation) tMgr.getCellLocation();
            carrierJson.put("cellId", cellLocation.getCid());
            carrierJson.put("locationAreaCode", cellLocation.getLac());
            carrierJson.put("mobileCountryCode", CountryCodeMap.getCode(tMgr.getSimCountryIso()));
            carrierJson.put("mobileNetworkCode", CountryCodeMap.getCode(tMgr.getNetworkCountryIso()));
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(carrierJson);
            jobject.put("homeMobileCountryCode", CountryCodeMap.getCode(tMgr.getSimCountryIso()));
            jobject.put("homeMobileNetworkCode", CountryCodeMap.getCode(tMgr.getNetworkCountryIso()));
            jobject.put("radioType", tMgr.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA ? "cdma" : "gsm");
            jobject.put("carrier", tMgr.getNetworkOperatorName());
            jobject.put("cellTowers", jsonArray);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        restProxy.doPost("https://www.googleapis.com/geolocation/v1/geolocate?key=" + LocationFetchSettings.APIKEY, jobject, String.class, new RestProxyCallback<String>() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    ngLocation.setAccuracy(object.optInt("accuracy"));
                    ngLocation.setLattitude(object.getJSONObject("location").optDouble("lat"));
                    ngLocation.setLongitude(object.getJSONObject("location").optDouble("lng"));
                    cb.onSuccess(ngLocation);
                } catch (Exception e) {
                    e.printStackTrace();
                    cb.onException(e);
                }
                Log.e("location", response);
            }

            @Override
            public void onFailure(Exception ex) {
                ex.printStackTrace();
                cb.onException(ex);
            }

            @Override
            public void onFailure(int errorCode) {
                Log.d("", "");
                cb.onFailure(errorCode);
            }

            @Override
            public void onProgress() {

            }
        });
    }
}
