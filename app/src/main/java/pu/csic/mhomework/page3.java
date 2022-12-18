package pu.csic.mhomework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class page3 extends AppCompatActivity {
    private TextView R_title1;

    private int page2mode;
    private int page4mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page2mode=0;
        page4mode=0;
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

        page2mode=page2.Returnselect.MODE();
        page4mode=page4.Returnselect.MODE();
        if(page2mode==1 ) {
            String i;
            i = page2.Returnselect.h1();
            R_title1.setText(""+i);


        }
        else if(page4mode==1){
            String i;
            i = page4.Returnselect.h1();
            R_title1.setText(""+i);

        }

    }
}