package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

   private MainActivityViewModel viewModel;

   private TextView tvCounter;
   private Button btnClick;
   private boolean stateCheck=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCounter=findViewById(R.id.textView);
        btnClick=findViewById(R.id.btnClickJava);

        viewModel= new ViewModelProvider(this).get(MainActivityViewModel.class);

        btnClick.setOnClickListener(v -> {
            collectLogs();
            if(!stateCheck) {
                viewModel.startCounter();
                Log.d("xxx","started Timer");
            }else{
                viewModel.stopCounter();
            }
        });

        viewModel.seconds().observe(this, integer -> {
            tvCounter.setText(integer+"");
            Log.d("xxx","Updating Timer"+integer);
            stateCheck=true;
        });

        viewModel.finished().observe(this, aBoolean ->{
            tvCounter.setText("FINISHED");
            Log.d("xxx","Timer FINISHED");
        });
    }

    private void collectLogs() {
        File appDirectory = this.getExternalCacheDir();
        File logDirectory = new File(appDirectory + File.separator + "logs" +
                File.separator + "Logs_" +
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "_" +
                Calendar.getInstance().get(Calendar.MONTH) + "_" +
                Calendar.getInstance().get(Calendar.YEAR));

        File logFile = new File(logDirectory, "logcat_" + Calendar.getInstance().get(Calendar.HOUR) + ":" +
                Calendar.getInstance().get(Calendar.MINUTE) + ":" +
                Calendar.getInstance().get(Calendar.SECOND) + ".txt");
        if (!appDirectory.exists()) {
            appDirectory.mkdirs();
        }
        if (!logDirectory.exists()) {
            logDirectory.mkdirs();
        }

        try {
            Process process = Runtime.getRuntime().exec("logcat -c");
            process = Runtime.getRuntime().exec("logcat -f" + logFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}