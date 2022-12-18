package pu.csic.mhomework;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class StartActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        progressBar = findViewById(R.id.progress_bar);
        start();
    }

    private void start() {
        MyAsyncTask task =new MyAsyncTask(this);
        task.execute(10);
        intent=new Intent(StartActivity.this,page1.class);
    }


    private  class MyAsyncTask extends AsyncTask<Integer,Integer,String> {

        private WeakReference<StartActivity> activityWeakReference;

        MyAsyncTask(StartActivity activity){
            activityWeakReference =new WeakReference<StartActivity>(activity);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            StartActivity activity=activityWeakReference.get();
            if (activity==null || activity.isFinishing()){

                return;
            }
            activity.progressBar.setVisibility(View.VISIBLE);

        }
        @Override
        protected String doInBackground(Integer... integers) {

            for(int i=0; i< integers[0]; i++){
                publishProgress((i*100)/integers[0]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            startActivity(intent);
            return "Finish!";

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            StartActivity activity=activityWeakReference.get();
            if (activity==null || activity.isFinishing()){
                return;
            }
            activity.progressBar.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            StartActivity activity=activityWeakReference.get();
            if (activity==null || activity.isFinishing()){
                return;
            }

            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            activity.progressBar.setProgress(0);
            activity.progressBar.setVisibility(View.INVISIBLE);

        }


    }
}