package com.collegedunia.toast

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast


class ToastGenerate(val context: Context) {
    private lateinit var instance: ToastGenerate

    fun getInstance(context: Context): ToastGenerate {
        if(!this::instance.isInitialized){
            instance = ToastGenerate(context)
        }
        return instance
    }

    fun createToastMessage(message: String, type: Number): Unit {
        val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val toastLayout: View = layoutInflater.inflate(R.layout.layout_custom_toast, null) as View
        val messageTextView: TextView = toastLayout.findViewById(R.id.message)
        val root: LinearLayout = toastLayout.findViewById(R.id.root)
        val shell: LinearLayout = toastLayout.findViewById(R.id.shell)

        root.setPadding(50, 0, 50, 50);

        val scale = context.resources.displayMetrics.density


        when(type) {
            1 -> createFailToast(toastLayout, messageTextView, message)
            2 -> createSuccessToast(toastLayout,shell, messageTextView, message)
            3 -> createInfoToast(toastLayout, messageTextView, message)
        }
    }

    private fun createInfoToast(
        toastLayout: View,
        messageTextView: TextView,
        message: String
    ) {
        TODO("Not yet implemented")
    }

    private fun createSuccessToast(
        toastLayout: View,
        shell: View,
        messageTextView: TextView,
        message: String
    ) {
        shell.background = context.resources.getDrawable(R.drawable.bg_success)
        messageTextView.text = message
        showToast(toastLayout)
    }

    private fun createFailToast(
        toastLayout: View,
        messageTextView: TextView,
        message: String
    ) {
        TODO("Not yet implemented")
    }

    private fun showToast(toastLayout: View) {
        val toast: Toast = Toast(context)
        toast.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM,0,0)
        toast.duration = Toast.LENGTH_LONG
        toast.view = toastLayout
        toast.show()
    }
}