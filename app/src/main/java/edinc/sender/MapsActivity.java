package edinc.sender;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lat;
    double lng;
    String LNG;
    String LAT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void LocationStart() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(MapsActivity.this, "Coordenadas GPS actualizadas: " +
                        "lat--> " + location.getLatitude() + " long--> " + location.getLongitude(), Toast.LENGTH_LONG).show();
                refresh(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Toast.makeText(MapsActivity.this, "Cambios en proveedor " + provider + " Estado-->" + status, Toast.LENGTH_LONG).show();


            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(MapsActivity.this, "Proveedor habilitado " + provider, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(MapsActivity.this, "Proveedor deshabilitado " + provider, Toast.LENGTH_LONG).show();

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);

    }

    private void refresh(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            LNG = Double.toString(lng);
            LAT = Double.toString(lat);
            newmarker(lat, lng);
            //tvUbicacion.setText("" + location.getLatitude() + "" + location.getLongitude());
            //Fecha();
            //Send objSend = new Send();
            //objSend.execute("");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocationStart();
    }

    private Marker marcador;

    private void newmarker(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate nuevapos = CameraUpdateFactory.newLatLngZoom(coordenadas, 13);
        if (marcador != null) marcador.remove();
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Posición Actual")
        );
        mMap.animateCamera((nuevapos));
    }
}



