package pu.csic.mhomework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class page1 extends AppCompatActivity {

    Switch ChangeMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
        Button nextPageBtn = (Button)findViewById(R.id.start);
        Button next2PageBtn = (Button)findViewById(R.id.admin);


        nextPageBtn.setVisibility(View.VISIBLE);
        next2PageBtn.setVisibility(View.INVISIBLE);


        ChangeMode=findViewById(R.id.switch1);

        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(page1.this , page2.class);
                startActivity(intent);
            }
        });

        next2PageBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                Intent intent = new Intent();
                intent.setPackage(getPackageName());
                intent.setClass(page1.this , page4.class);

                startActivity(intent);
            }
        });

        ChangeMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              if(ChangeMode.isChecked()){
                  nextPageBtn.setVisibility(View.INVISIBLE);
                  next2PageBtn.setVisibility(View.VISIBLE);
                  Toast.makeText(getApplicationContext(), "1",Toast.LENGTH_LONG).show();
                  Log.d("test","1");
              }
              else {
                  nextPageBtn.setVisibility(View.VISIBLE);
                  next2PageBtn.setVisibility(View.INVISIBLE);

                  Toast.makeText(getApplicationContext(), "0",Toast.LENGTH_LONG).show();
                  Log.d("test","0");
              }
            }
        });



    }
}