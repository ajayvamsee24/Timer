package com.example.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private var stateCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        btnClick.setOnClickListener {
            if (!stateCheck) {
                viewModel.startCounter()
            } else {
                // stop counter
                viewModel.stopCounter()
            }
        }

        viewModel.seconds().observe(this, Observer {
            textView1.text = it.toString()
            stateCheck = true
            Log.d("AAA", "Times that call $it")
        })

        viewModel.finished().observe(this, Observer {
            textView1.text = "FINISHED"
            stateCheck = false
            Log.d("AAA", "Finished$it")
        })
    }
}