package com.collegedunia.toastlib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.collegedunia.toast.ToastGenerate
import com.collegedunia.toast.ToastModel
import com.collegedunia.toast.ToastType

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ToastGenerate.getInstance(this)
            .createToastMessage(ToastModel(ToastType.SUCCESS,"Hello", null, this.resources.getDrawable(R.drawable.ic_check_mark)))
        findViewById<Button>(R.id.btnSuccess).setOnClickListener(View.OnClickListener {
            ToastGenerate.getInstance(this)
                .createToastMessage(ToastModel(ToastType.SUCCESS,"Success", null, null))
        })

        findViewById<Button>(R.id.btnNormal).setOnClickListener(View.OnClickListener {
            ToastGenerate.getInstance(this)
                .createToastMessage(ToastModel(ToastType.NORMAL,"Normal", null, null))
        })

        findViewById<Button>(R.id.btnError).setOnClickListener(View.OnClickListener {
            ToastGenerate.getInstance(this)
                .createToastMessage(ToastModel(ToastType.ERROR,"Error", null, null))
        })

        findViewById<Button>(R.id.btnWarning).setOnClickListener(View.OnClickListener {
            ToastGenerate.getInstance(this)
                .createToastMessage(ToastModel(ToastType.WARNING,"Warning", "Warning message", null))
        })
    }
}