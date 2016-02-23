package com.abhilashmishra.locf.locationfetchexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.maststudio.locf.LocationFetchAction;
import com.maststudio.locf.LocationFetchCallback;
import com.maststudio.locf.LocationFetchSettings;
import com.maststudio.locf.NGLocation;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        LocationFetchSettings.setApiKey("AIzaSyDkzb0hjdH46rYBoOkTJuAizqmaURY_IyI");
        LocationFetchAction.getLocation(this, new LocationFetchCallback() {
            @Override
            public void onSuccess(final NGLocation ngLocation) {
                ((TextView) findViewById(R.id.tv1)).setText(ngLocation.getAccuracy()+"");
                ((TextView) findViewById(R.id.tv2)).setText(ngLocation.getLattitude() + "");
                ((TextView) findViewById(R.id.tv3)).setText(ngLocation.getLongitude() + "");


            }

            @Override
            public void onFailure(int errorCode) {
                Log.d("", "");
            }

            @Override
            public void onException(Exception ex) {
                Log.d("", "");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
