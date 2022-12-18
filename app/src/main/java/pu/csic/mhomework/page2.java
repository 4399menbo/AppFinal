package pu.csic.mhomework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class page2 extends AppCompatActivity {

    private TextView R_title;
    private TextView R_shop;
    private TextView R_menu;
    private TextView R_smenu;
    private Button R_run;
    private Button backHome;

    ImageView image;

    //private int counter=0;
    //private  ImageView aniImage;

    private ScrollView screen1;
    private ScrollView screen2;

    private Button S2Back;
    private Button S3start;

    AnimationDrawable anim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        //获得布局
        RelativeLayout relativeLayout = findViewById(R.id.animat2);
        //从布局中获得背景
        anim = (AnimationDrawable)relativeLayout.getBackground();

        R_title = (TextView)findViewById(R.id.house);

        R_shop = (TextView)findViewById(R.id.shop);

        R_menu = (TextView) findViewById(R.id.info);

        R_smenu = (TextView) findViewById(R.id.sinfo);

        R_run = (Button) findViewById(R.id.Run1);

        image = (ImageView)findViewById(R.id.product_location) ;

        //aniImage = (ImageView)findViewById(R.id.aniImage) ;

        screen1 = (ScrollView)findViewById(R.id.screen_1);
        screen2 = (ScrollView)findViewById(R.id.screen_2);

        backHome = (Button) findViewById(R.id.BackHome);

        S2Back = (Button)findViewById(R.id.s2BACK);
        S3start = (Button)findViewById(R.id.startmap);

        ceImage();
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        S2Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen2.setVisibility(View.INVISIBLE); // 隱藏
                screen1.setVisibility(View.VISIBLE);

                ceImage();
            }
        });
        S3start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(page2.this , page3.class);
                startActivity(intent);
                finish();
            }
        });
        clickGo();
    }


    private  void ceImage(){
        anim.stop();
        //String uri = "@drawable/eag1" ; //圖片路徑和名稱
        //int imageResource = getResources().getIdentifier(uri, null, getPackageName()); //取得圖片Resource位子
        //aniImage.setImageResource(imageResource);
    }
    private void  clickGo(){
        R_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = "";

                R_run.setVisibility(View.INVISIBLE); // 隱藏

                //動畫
                MyThread t1 = new MyThread();
                t1.start();
                anim.start();


                //抽到的大樓
                int house = (int)(Math.random()*(2-0+1)) + 0;


                try {
                    InputStream is = getAssets().open("menu"+house+".txt");
                    int size = is.available();
                    byte[] buffer = new byte[size];

                    is.read(buffer);
                    is.close();

                    str = new String(buffer);

                    SelectShop(str);


                } catch (IOException e) {
                    e.printStackTrace();
                }

                //R_menu.setText(str);

            }
        });
    }

    private void SelectShop(String str){

        //資料導入
        ArrayList<String> sites = new ArrayList<String>();
        sites.clear();

        for (String retval: str.split("\n")){
            sites.add(retval);
        }

        //random = (int)(Math.random()*(MAX-min+1)) + min;
        //隨機餐點
        int index = 1;
        while (sites.get(index).charAt(0) == '/'){
            index = (int)(Math.random()*((sites.size()-1)-1+1)) + 1;
        }


        //餐點分割
        str = sites.get(index).trim();

        //餐點、價格
        String[] FoodInfo= str.split(" ");


        //店名ID
        int ShopID = index;

        while (sites.get(ShopID).charAt(0) != '/'){
            ShopID--;
        }

        String strShop =  sites.get(ShopID).trim();
        //String strShop =  sites.get(index - Integer.parseInt(FoodInfo[2])-1).trim();
        strShop = strShop.substring(1);

        //店家名和圖名
        //圖片路徑和名稱
        String[] ImageEnglish = strShop.split(" ");;

        String uri = "@drawable/" +ImageEnglish[1];// + FoodInfo[2];

        //取得圖片Resource位子

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());

        image.setImageResource(imageResource);

        //地點 座標
        String[] title= sites.get(0).trim().split(" ");

        R_title.setText(title[0]);


        R_shop.setText(ImageEnglish[0]);

        R_menu.setText(FoodInfo[0] + " "+FoodInfo[1]);
        if(FoodInfo.length>2){
            R_smenu.setText(FoodInfo[2]+"");
            R_smenu.setVisibility(View.VISIBLE); //顯示
        }
        else {
            R_smenu.setVisibility(View.GONE); // 隱藏
        }
    }




    class MyThread extends Thread{
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
                screen1.setVisibility(View.INVISIBLE); // 隱藏
                R_run.setVisibility(View.VISIBLE); //顯示
                screen2.setVisibility(View.VISIBLE);
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