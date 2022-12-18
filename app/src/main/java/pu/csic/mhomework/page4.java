package pu.csic.mhomework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
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

import java.util.Iterator;

public class page4 extends AppCompatActivity {

    private  TextView house;
    private  TextView shop;
    private  TextView info;

    private Button s4Back;
    private  Button s3Start;
    private Button run;
    private  Button HomeBtn;

    private ScrollView screen4;
    private ScrollView screen5;

    //private ImageView aniImage;
    AnimationDrawable anim;

    //private int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4);

        //aniImage = (ImageView)findViewById(R.id.F_aniImage);

        house = (TextView)findViewById(R.id.F_house);
        shop = (TextView)findViewById(R.id.F_shop);
        info = (TextView)findViewById(R.id.F_info);

        screen4 = (ScrollView)findViewById(R.id.screen_4);
        screen5 = (ScrollView)findViewById(R.id.screen_5);

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
                startActivity(intent);
                finish();
            }
        });
    }

    private  void ceImage(){
        anim.stop();
        //String uri = "@drawable/eag1" ; //圖片路徑和名稱
        //int imageResource = getResources().getIdentifier(uri, null, getPackageName()); //取得圖片Resource位子
        //aniImage.setImageResource(imageResource);
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
                    Thread.sleep(50);
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
    /*
    private void UpdateRun(final int counter) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String uri = "@drawable/eag"+counter ; //圖片路徑和名稱

                int imageResource = getResources().getIdentifier(uri, null, getPackageName()); //取得圖片Resource位子
                aniImage.setImageResource(imageResource);

            }
        });
    }

     */
}