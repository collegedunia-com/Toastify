package com.collegedunia.toastlib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.collegedunia.toast.ToastGenerate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ToastGenerate(this)
            .getInstance(this)
            .createToastMessage("Hello", 2)
        findViewById<Button>(R.id.btnSuccess).setOnClickListener(View.OnClickListener {
            ToastGenerate(this)
                .getInstance(this)
                .createToastMessage("Hello", 2)
        })
    }
}