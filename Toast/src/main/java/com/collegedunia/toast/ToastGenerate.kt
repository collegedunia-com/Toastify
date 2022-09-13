package com.collegedunia.toast

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import java.util.*
import kotlin.concurrent.schedule


class ToastGenerate constructor(private val context: Context) {
    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ToastGenerate? = null

        fun getInstance(context: Context): ToastGenerate =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ToastGenerate(context).also { INSTANCE = it }
            }
    }
    private var listen = MutableLiveData<ArrayList<ToastModel>>()
    private val layoutInflater: LayoutInflater
    private val toastLayout: View
    private val root: LinearLayout
    private var toastShown = MutableLiveData<Boolean>()

    init {
        listen.value = arrayListOf();
        toastShown.value = false

        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        toastLayout = layoutInflater.inflate(R.layout.layout_custom_toast, null) as View
        root = toastLayout.findViewById(R.id.root)

        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )


        root.layoutParams = lp

        root.setPadding(50, 0, 50, 0);

        MediatorLiveData<Pair<ArrayList<ToastModel>?, Boolean?>>().apply {
            addSource(listen) { value = it to value?.second }
            addSource(toastShown) { value = value?.first to it }
        }.observeForever { pair ->
            Log.d("DebugSSSS", pair.first!!.size.toString() + " " + pair.second)

            if(pair.second!=null && !pair.second!!) {
                if (pair.first != null) {
                    if (pair.first!!.isNotEmpty()) {
                        val item: ToastModel = pair.first!![pair.first!!.size - 1]
                        when (item.type) {
                            ToastType.ERROR -> createErrorToast(toastLayout, item)
                            ToastType.SUCCESS -> createSuccessToast(toastLayout, item)
                            ToastType.WARNING -> createWarningToast(toastLayout, item)
                            ToastType.NORMAL -> createNormalToast(toastLayout, item)
                        }
                        toastShown.value = true
                    }
                }
            }
        }


    }

    fun createToastMessage(toast: ToastModel): Unit {
        val list: ArrayList<ToastModel>? = listen.value
        val newList: ArrayList<ToastModel> = arrayListOf<ToastModel>();

        if (list != null && list.isNotEmpty()) {
            newList.addAll(list)
            if(toast != list[list.size-1]){
                newList.add(toast)
                listen.value = newList
            }
        }else{
            newList.add(toast)
            listen.value = newList
        }

    }

    private fun createNormalToast(
        toastLayout: View,
        item: ToastModel
    ) {
        val shell: View = createToast(toastLayout, item)
        shell.background = context.resources.getDrawable(R.drawable.bg_normal)
        showToast(toastLayout, item.length)
        cancel(item)
    }

    private fun createSuccessToast(
        toastLayout: View,
        item: ToastModel
    ) {
        val shell: View = createToast(toastLayout, item)
        shell.background = context.resources.getDrawable(R.drawable.bg_success)
        showToast(toastLayout, item.length)
        cancel(item)
    }

    private fun createErrorToast(
        toastLayout: View,
        item: ToastModel
    ) {
        val shell: View = createToast(toastLayout, item)
        shell.background = context.resources.getDrawable(R.drawable.bg_error)
        showToast(toastLayout, item.length)
        cancel(item)
    }

    private fun createWarningToast(
        toastLayout: View,
        item: ToastModel
    ) {
        val shell: View = createToast(toastLayout, item)
        shell.background = context.resources.getDrawable(R.drawable.bg_warning)
        showToast(toastLayout, item.length)
        cancel(item)
    }

    private fun createToast(
        toastLayout: View,
        item: ToastModel
    ): View {
        val shell: View = toastLayout.findViewById(R.id.shell)
        val imgLeft: ImageView = toastLayout.findViewById(R.id.imgLeft)
        val title: TextView = toastLayout.findViewById(R.id.title)
        val description: TextView = toastLayout.findViewById(R.id.description)
        if (item.leftImage == null) {
            imgLeft.visibility = GONE
        } else {
            imgLeft.visibility = VISIBLE
            imgLeft.setImageDrawable(item.leftImage)
        }

        if (item.title == null) {
            title.visibility = GONE
        } else {
            title.visibility = VISIBLE
            title.text = item.title
        }
        if (item.message == null) {
            description.visibility = GONE
        } else {
            description.visibility = VISIBLE
            description.text = item.message
        }
        return shell
    }

    private fun cancel(item: ToastModel){
        val time: Long = if(item.length==ToastLength.LONG){
            3500
        }else {
            2000
        }
        Timer().schedule(time){
            val list: ArrayList<ToastModel>? = listen.value
            list?.remove(item)
            listen.postValue(list)
            toastShown.postValue(false)
        }
    }

    private fun showToast(toastLayout: View, length: ToastLength?) {
        var toast: Toast = Toast(context)
        toast.duration = if(length==ToastLength.LONG){
            Toast.LENGTH_LONG
        }else{
            Toast.LENGTH_SHORT
        }
        toast.view = toastLayout
        toast.show()
    }
}