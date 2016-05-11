package it.gdaloiso.bikeshare;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private GoogleMap mMap;
    private MapView mapView;
    private Marker marker;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private ArrayList<Bici> listBici = new ArrayList<Bici>();
    private Bici[] bike = {
            new Bici("Agraria", 41.1114341156773, 16.8828964233398, "Numero bici: 10"),
            new Bici("Area sosta Mazzini", 41.1248835992912, 16.8558597564697, "Numero bici: 0"),
            new Bici("Area sosta Rossani", 41.1161708467507, 16.8718671798706, "Numero bici: 10"),
            new Bici("Ateneo", 41.1211981976236, 16.8694853782654, "Numero bici: 6"),
            new Bici("Camera di Commercio", 41.1241804794232, 16.8722319602966, "Numero bici: 10"),
            new Bici("Chiesa Russa", 41.1092030454473, 16.8718671798706, "Numero bici: 10"),
            new Bici("Cimitero", 41.1233157124733, 16.847620010376, "Numero bici: 10"),
            new Bici("Città vecchia", 41.12726364454, 16.8659555912018, "Numero bici: 8"),
            new Bici("Comando Polizia Municipale", 41.1058401296738, 16.9125509262085, "Numero bici: 10"),
            new Bici("Economia", 41.094129210039, 16.8545830249786, "Numero bici: 9"),
            new Bici("Garibaldi", 41.1258372440852, 16.8620181083679, "Numero bici: 9"),
            new Bici("Madonnella", 41.120357638435, 16.878776550293, "Numero bici: 10"),
            new Bici("Mercato Giovanni XXIII", 41.1071335792001, 16.8647432327271, "Numero bici: 10"),
            new Bici("Multipark", 41.1181430367223, 16.8646574020386, "Numero bici: 9"),
            new Bici("Palazzo Provincia", 41.1207698370746, 16.8805575370789, "Numero bici: 5"),
            new Bici("Park&Ride A", 41.127744492096, 16.8527698516846, "Numero bici: 9"),
            new Bici("Park&Ride C", 41.1052095637919, 16.8739485740662, "Numero bici: 10"),
            new Bici("Piazzetta dei Papi", 41.1047891831734, 16.8662238121033, "Numero bici: 10"),
            new Bici("Pineta San Francesco", 41.132156811018, 16.8286514282227, "Numero bici: 9"),
            new Bici("Policlinico", 41.111531117011, 16.8592929840088, "Numero bici: 9"),
            new Bici("Politecnico", 41.1075054412235, 16.8780040740967, "Numero bici: 19"),
            new Bici("Politecnico - Segreteria", 41.098046557094, 16.8901920318604, "Numero bici: 10"),
            new Bici("Porto", 41.1340961979804, 16.8693351745605, "Numero bici: 10"),
            new Bici("Prefettura", 41.1260312040422, 16.86851978302, "Numero bici: 10"),
            new Bici("Ravanas", 41.1178682269188, 16.8593788146973, "Numero bici: 10"),
            new Bici("Risorgimento", 41.1230813344214, 16.8605482578278, "Numero bici: 7"),
            new Bici("San Pasquale", 41.1137459417996, 16.8769097328186, "Numero bici: 8"),
            new Bici("Spiaggia Pane e Pomodoro", 41.1159930234312, 16.9047832489014, "Numero bici: 8"),
            new Bici("Stazione FF.SS.", 41.1186603226429, 16.8696570396423, "Numero bici: 17"),
            new Bici("Tribunale", 41.1234854339884, 16.8574690818787, "Numero bici: 7"),
            new Bici("Trieste", 41.1159930234312, 16.8987321853638, "Numero bici: 5"),
            new Bici("Vittorio Veneto", 41.1335467108287, 16.8443584442139, "Numero bici: 9"),
    };


    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000) // 5 seconds
            .setFastestInterval(16) // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    private static final String ALREADY_CENTERED = "ALREADY_CENTERED";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setTitle("BikeSharingBari");
        for (int i = 0; i < 32; i++) {
            listBici.add(bike[i]);
        }
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                for(int i=0;i<listBici.size();i++){
                    mMap.addMarker(new MarkerOptions()
                            .position(listBici.get(i).getPosition())
                            .title(listBici.get(i).getNome())
                            .snippet(listBici.get(i).getNumero()));
                }
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(listBici.get(2).getPosition(), 13));
            }
        });

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // your code goes here
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            String lat = String.valueOf(mLastLocation.getLatitude());
            String longi = String.valueOf(mLastLocation.getLongitude());
        }
        mMap.setMyLocationEnabled(true);
    }
    @Override
    public void onConnectionSuspended(int cause) {
        //your code goes here
    }

    public void search_near_location(double mylat,double mylon){
        ArrayList<Double> latitude = new ArrayList<Double>();
        for(int i=0;i<listBici.size();i++){
            latitude.add(listBici.get(i).getLatitude());
        }

        ArrayList<Double> longitude = new ArrayList<Double>();
        for(int i=0;i<listBici.size();i++){
            longitude.add(listBici.get(i).getLongitudine());
        }

        double near =-1.0;
        int k = 0;

        for(int i=0;i<latitude.size();i++){
            double a = Math.pow(latitude.get(i)-mylat, 2);
            double b = Math.pow(longitude.get(i)-mylon, 2);
            double r= Math.sqrt(a+b);

            if(near ==-1.0 || near > r ){
                near = r;
                k=i;
            }
        }

        LatLng Bici_near = new LatLng(latitude.get(k), longitude.get(k));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Bici_near, 16));

    }



}
