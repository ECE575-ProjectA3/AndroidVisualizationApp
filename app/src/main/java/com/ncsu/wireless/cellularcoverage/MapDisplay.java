package com.ncsu.wireless.cellularcoverage;

import android.app.Dialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MapDisplay extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private String var_carrier;
    private String var_parameter;
    private String url;
    private JSONArray jsonarray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_display);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            var_carrier = extras.getString("var_carrier");
            var_parameter = extras.getString("var_parameter");
            url = extras.getString("url");
        }

        //get details from server using httprequest
        try {
            getDataFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setUpMapIfNeeded();
    }

    private void getDataFromServer() throws IOException {

        //String url = "http://ece575a3.ddns.net:8080/request?carrier=testRequest&type=signalStrength";

        HttpClient httpClient = new DefaultHttpClient();

        try{
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();

            if(httpEntity != null) {


                InputStream inputStream = httpEntity.getContent();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                String dataVar = bufferedReader.readLine();
                while (dataVar != null) {
                    stringBuilder.append(dataVar + " \n");
                    dataVar = bufferedReader.readLine();
                }
                bufferedReader.close();

                jsonarray = new JSONArray(stringBuilder.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            centerMapOnMyLocation();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {

        Double latitude;
        Double longitude;
        Integer dataValue;
        String dateTime;
        String dataType;
        JSONObject jsonResponse;
        for (int i = 0; i < jsonarray.length(); i++) {
            try {
                jsonResponse = jsonarray.getJSONObject(i);
                latitude = jsonResponse.getDouble("latitude");
                longitude = jsonResponse.getDouble("longitude");
                dataValue = jsonResponse.getInt("dataValue");
                dataType = jsonResponse.getString("dataType");
                dateTime = jsonResponse.getString("dateTime");
                Float color;
                if (dataType.equals("signalStrength")) {
                    if (dataValue == 3) {
                        color = Float.parseFloat("120.0");
                    } else if (dataValue == 2) {
                        color = Float.parseFloat("60.0");
                    } else if (dataValue == 1) {
                        color = Float.parseFloat("0.0");
                    } else {
                        color = Float.parseFloat("300.0");
                    }
                    if (dataValue > 0 && dataValue <= 3) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(var_carrier + ", " +
                                var_parameter + ", " + dateTime).icon(BitmapDescriptorFactory.defaultMarker(color)));
                    }
                } else if (dataType.equals("downloadSpeed")) {
                    if (dataValue > 6) {
                        color = Float.parseFloat("120.0");
                    } else if (dataValue <= 6 && dataValue > 2) {
                        color = Float.parseFloat("60.0");
                    } else {
                        color = Float.parseFloat("0.0");
                    }
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(var_carrier + ", " +
                            var_parameter + ", " + dateTime).icon(BitmapDescriptorFactory.defaultMarker(color)));
                } else if (dataType.equals("uploadSpeed")) {
                    if (dataValue > 3) {
                        color = Float.parseFloat("120.0");
                    } else if (dataValue <= 3 && dataValue > 1) {
                        color = Float.parseFloat("60.0");
                    } else {
                        color = Float.parseFloat("0.0");
                    }
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(var_carrier + ", " +
                            var_parameter + ", " + dateTime).icon(BitmapDescriptorFactory.defaultMarker(color)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonarray.length() == 0) {
            Context context = getApplicationContext();
            CharSequence text = "No Data to display for selected carrier and/or selected dates";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            toast.show();
        }
        /*
        public static final float HUE_AZURE Constant Value: 210.0
        public static final float HUE_BLUE Constant Value: 240.0
        public static final float HUE_CYAN Constant Value: 180.0
        public static final float HUE_GREEN Constant Value: 120.0
        public static final float HUE_MAGENTA Constant Value: 300.0
        public static final float HUE_ORANGE Constant Value: 30.0
        public static final float HUE_RED Constant Value: 0.0
        public static final float HUE_ROSE Constant Value: 330.0
        public static final float HUE_VIOLET Constant Value: 270.0
        public static final float HUE_YELLOW Constant Value: 60.0
        */
    }

    private void centerMapOnMyLocation() {

        mMap.setMyLocationEnabled(true);

        /////----------------------------------Zooming camera to position user-----------------

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(15)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
/////----------------------------------Zooming camera to position user-----------------
    }

}