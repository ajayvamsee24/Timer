package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
            if(!stateCheck) {
                viewModel.startCounter();
            }else{
                viewModel.stopCounter();
            }
        });

        viewModel.seconds().observe(this, integer -> {
            tvCounter.setText(integer+"");
            stateCheck=true;
        });

        viewModel.finished().observe(this, aBoolean -> tvCounter.setText("FINISHED"));
    }
}