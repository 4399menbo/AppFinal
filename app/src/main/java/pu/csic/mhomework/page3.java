package pu.csic.mhomework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class page3 extends AppCompatActivity {
    private TextView R_title1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);

        Button Endmap=findViewById(R.id.endmap);

       R_title1=findViewById(R.id.textView2);
        R_title1.setSelected(true);

        Endmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String i ;
        i=page2.Returnselect.h1();
        R_title1.setText(""+i);
    }
}