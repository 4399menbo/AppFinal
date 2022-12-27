package pu.csic.mhomework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class page3 extends AppCompatActivity {
    private TextView R_title1;

    private int page2mode;
    private int page4mode;


    private double loc_x;//x位置
    private double loc_y;//y位置

    private String cur_x;//x位置
    private String cur_y;//y位置

    private LocationManager locationManager;
    private String commadStr;

    public static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page2mode = 0;
        page4mode = 0;
        setContentView(R.layout.activity_page3);


        //獲取座標
        Bundle objgetbundle = this.getIntent().getExtras();
        double[] array = objgetbundle.getDoubleArray("pnumber");
        loc_x = array[0];
        loc_y = array[1];
        //

        commadStr = LocationManager.NETWORK_PROVIDER;

        Button Endmap = findViewById(R.id.endmap);

        R_title1 = findViewById(R.id.textView2);
        R_title1.setSelected(true);

        Endmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageButton imageView2_ = findViewById(R.id.imageView2);
        imageView2_.setOnClickListener(btnPrevListener);

        page2mode = page2.Returnselect.MODE();
        page4mode = page4.Returnselect.MODE();
        if (page2mode == 1) {
            String i;
            i = page2.Returnselect.h1();
            R_title1.setText("" + i);
        } else if (page4mode == 1) {
            String i;
            i = page4.Returnselect.h1();
            R_title1.setText("" + i);
        }
    }

    private ImageButton.OnClickListener btnPrevListener=new ImageButton.OnClickListener(){
        public void onClick(View v){
            getPos();
        }
    };

    void getPos() {

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(page3.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_ACCESS_COARSE_LOCATION);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(commadStr, 1000, 0, locationListener);

        Location location = locationManager.getLastKnownLocation(commadStr);
        if(location!=null){
            //R_title1.setText(location.getLongitude()+","+location.getLatitude());
            newMap();
        }
        else {

        }


    }

    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            cur_x = String.valueOf(location.getLongitude());
            cur_y = String.valueOf(location.getLatitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    };

    void  newMap(){
        Uri url = Uri.parse("https:www.google.com/maps/dir/?api=1&origin=" + cur_x + "," + cur_y + "&destination=" + loc_x + "," + loc_y);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, url);
        startActivity(mapIntent);

    }
}