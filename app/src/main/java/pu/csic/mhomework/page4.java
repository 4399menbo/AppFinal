package pu.csic.mhomework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

public class page4 extends AppCompatActivity {

    private  static TextView house;
    private  static TextView shop;
    private  static TextView info;
    static String str;
    static String str1;
    static String str2;

    private Button s4Back;
    private  Button s3Start;
    private Button run;
    private  Button HomeBtn;
    private TextView R_hint2;

    private ScrollView screen4;
    private ScrollView screen5;

    //private ImageView aniImage;
    AnimationDrawable anim;

    //private int counter=0;

    private static  int mode=0;

    private double loc_x = 0;
    private double loc_y = 0;

    private ImageView img_product;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4);



        R_hint2=findViewById(R.id.HINT);
        R_hint2.setSelected(true);

        //aniImage = (ImageView)findViewById(R.id.F_aniImage);

        house = (TextView)findViewById(R.id.F_house);
        shop = (TextView)findViewById(R.id.F_shop);
        info = (TextView)findViewById(R.id.F_info);

        screen4 = (ScrollView)findViewById(R.id.screen_4);
        screen5 = (ScrollView)findViewById(R.id.screen_5);
        //獲取網路圖片
        StrictMode
                .setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().
                detectDiskWrites().
                detectNetwork().
                penaltyLog().
                build());
        StrictMode
                .setVmPolicy(new StrictMode.VmPolicy.Builder()
                        .detectLeakedSqlLiteObjects()
                        .detectLeakedClosableObjects()
                        .penaltyLog()
                        .penaltyDeath()
                        .build());
        img_product = (ImageView)findViewById(R.id.F_product_location);

        //获得布局
        RelativeLayout relativeLayout = findViewById(R.id.animat);
        //从布局中获得背景
        anim = (AnimationDrawable)relativeLayout.getBackground();

        ceImage();
        HomeBtn = (Button)findViewById(R.id.F_BackHome);
        HomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        run = (Button) findViewById(R.id.F_Run1);
        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run.setVisibility(View.INVISIBLE); // 隱藏
                F_GO();
            }
        });

        s4Back = (Button)findViewById(R.id.F_s4BACK);
        s4Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen5.setVisibility(View.INVISIBLE); // 隱藏
                screen4.setVisibility(View.VISIBLE);

                ceImage();
            }
        });

        s3Start = (Button)findViewById(R.id.F_startmap);
        s3Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(page4.this , page3.class);

                double[] RoomAndName = {loc_x,loc_y};
                Bundle objbundle = new Bundle();
                objbundle.putDoubleArray("pnumber",RoomAndName);
                intent.putExtras(objbundle);

                startActivity(intent);
                finish();
            }
        });
    }

    public void getReImage(String bitmapURL){
        try {
            URL myUrl = new URL(bitmapURL);
            URLConnection myConn = myUrl.openConnection();
            InputStream in = myConn.getInputStream();
            Bitmap bmp = BitmapFactory.decodeStream(in);
            img_product.setImageBitmap(bmp);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void ceImage(){
        anim.stop();
    }

    private void F_GO(){
        MyThread1 t1 = new MyThread1();
        t1.start();
        anim.start();
        readF();
    }

    private void readF() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");

        mode=1;

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> chirldren = dataSnapshot.getChildren();
                Iterator<DataSnapshot> it = chirldren.iterator();

                int i = 0;
                //random = (int)(Math.random()*(MAX-min+1)) + min;
                while (it.hasNext()){
                    it.next();
                    i++;
                }

                int random = (int)(Math.random()*((i-1)-0+1)) + 0;
                i=0;

                chirldren = dataSnapshot.getChildren();
                Iterator<DataSnapshot> it1 = chirldren.iterator();
                while (it1.hasNext()){
                    DataSnapshot item = it1.next();
                    if(i==random){
                        house.setText(item.child("pos").getValue(String.class));
                        shop.setText(item.child("Name").getValue(String.class));

                        getReImage(item.child("Image_").getValue(String.class));

                        loc_x = item.child("x").getValue(Double.class);
                        loc_y = item.child("y").getValue(Double.class);
                        readInFo(item.getKey());
                    }
                    i++;
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


    }

    private void readInFo(String menu) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(menu).child("menu");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> chirldren = dataSnapshot.getChildren();
                Iterator<DataSnapshot> it = chirldren.iterator();

                int i = 0;
                //random = (int)(Math.random()*(MAX-min+1)) + min;
                while (it.hasNext()){
                    it.next();
                    i++;
                }

                int random = (int)(Math.random()*((i-1)-0+1)) + 0;
                i=0;

                chirldren = dataSnapshot.getChildren();
                Iterator<DataSnapshot> it1 = chirldren.iterator();
                while (it1.hasNext()){
                    DataSnapshot item = it1.next();
                    if(i==random){
                        info.setText(item.child("name").getValue(String.class)+" "+item.child("price").getValue(Integer.class)+"元");
                    }
                    i++;
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

    }


    class MyThread1 extends Thread{
        @Override
        public void run(){
            for (int i=0;i<26;i++){
                //counter ++;

                //UpdateRun(counter);
                try {
                    Thread.sleep(95);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            aniEND();
        }
    }

    private  void aniEND(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                screen4.setVisibility(View.INVISIBLE); // 隱藏
                run.setVisibility(View.VISIBLE); //顯示
                screen5.setVisibility(View.VISIBLE);
                //counter = 0;
            }
        });
    }



    public static class Returnselect{
        public static int MODE(){
            return mode;
        }

        public static String h1() {
            str=house.getText().toString();
            str1=shop.getText().toString();
            str2=info.getText().toString();
            mode=0;
            return str+" "+str1+" "+str2;
            }

        }

    }
