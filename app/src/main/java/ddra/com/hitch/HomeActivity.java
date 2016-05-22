package ddra.com.hitch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class HomeActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener {
    int i = 0;
    GoogleMap mMap; // Might be null if Google Play services APK is not available.
    LatLng  recentMarker= null;
    HashMap<String, String> mMarkerMap = new HashMap<>();
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpMapIfNeeded();
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);

        Button but = (Button) findViewById(R.id.button_id);
        but.setBackgroundColor(Color.parseColor("#21AC26"));
        but.setText("Invasive Species");
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, InfoPanel.class);
                startActivity(intent);
            }
        });
    }

    public void onClicker() {
        Intent intent = new Intent(HomeActivity.this, InfoPanel.class);
        HomeActivity.this.startActivity(intent);
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
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
        if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    int cool = 1;
    private void setUpMap() {
for (cool = 1; cool<2; cool++) {
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            private double  pyth (LatLng a, Location b){
//                ((m.getPosition^2)-recentMarker^2) = int c;
                double delta_x = b.getLongitude()-a.longitude;
                double delta_y = b.getLatitude()-a.latitude;
                return delta_x * delta_x + delta_y * delta_y;
            }
            @Override
            public void onMyLocationChange(Location location) {

                if (recentMarker == null || pyth(recentMarker, location) > 10) {

                    Marker m =  mMap.addMarker(new MarkerOptions().draggable(true).position(new LatLng(location.getLatitude(), location.getLongitude())));
                    recentMarker =  m.getPosition();
                }
                cool++;









            }
        });}
       ;};
   ;



    @Override
    public void onMapLongClick(LatLng point) {
        Marker marker = mMap.addMarker(new MarkerOptions()
                .draggable(true)
                .position(point)
                .title(""));
        mMarkerMap.put(marker.getId(), null);
        Toast.makeText(getApplicationContext(),  m_Text + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (mMarkerMap.get(marker.getId())!= null){
            Toast.makeText(getApplicationContext(), mMarkerMap.get(marker.getId()), Toast.LENGTH_SHORT).show();
            return false;
        }
             else {
            openMenu(marker);
        }
        return true;
    }
    private String m_Text = "";

    public void openMenu(final Marker marker) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView text = new TextView(this);
        text.setText("Species"); // Fix this!
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                if (mMarkerMap.containsKey(marker.getId())) {
                    mMarkerMap.put(marker.getId(), m_Text);
                }
                PebbleMsg(m_Text);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void PebbleMsg(String s) {
        final Intent i = new Intent("com.getpebble.action.SEND_NOTIFICATION");

        final Map data = new HashMap();
        data.put("title", "Hitch");
        data.put("body", s + " was marked!");
        final JSONObject jsonData = new JSONObject(data);
        final String notificationData = new JSONArray().put(jsonData).toString();

        i.putExtra("messageType", "PEBBLE_ALERT");
        i.putExtra("sender", "PebbleKit Android");
        i.putExtra("notificationData", notificationData);
        sendBroadcast(i);
    }
}
