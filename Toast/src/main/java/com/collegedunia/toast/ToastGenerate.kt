package com.collegedunia.toast

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.Gravity
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
import kotlin.collections.ArrayList
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
    private val shell: LinearLayout
    private var toastShown = MutableLiveData<Boolean>()
    private lateinit var toast: Toast
    private val imgLeft: ImageView
    private val title: TextView
    private val description: TextView

    init {
        listen.value = arrayListOf();
        toastShown.value = false

        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        toastLayout = layoutInflater.inflate(R.layout.layout_custom_toast, null) as View
        root = toastLayout.findViewById(R.id.root)
        shell = toastLayout.findViewById(R.id.shell)
        imgLeft = toastLayout.findViewById(R.id.imgLeft)
        title = toastLayout.findViewById(R.id.title)
        description = toastLayout.findViewById(R.id.description)

        root.setPadding(50, 0, 50, 50);

        MediatorLiveData<Pair<ArrayList<ToastModel>?, Boolean?>>().apply {
            addSource(listen) { value = it to value?.second }
            addSource(toastShown) { value = value?.first to it }
        }.observe(context as LifecycleOwner) { pair ->
            if(pair.second!=null && !pair.second!!) {
                if (pair.first != null) {
                    if (pair.first!!.isNotEmpty()) {
                        Log.d("ArrayList", pair.first.toString())
                        val item: ToastModel = pair.first!![pair.first!!.size - 1]
                        when (item.type) {
                            ToastType.ERROR -> createErrorToast(toastLayout, shell, item)
                            ToastType.SUCCESS -> createSuccessToast(toastLayout, shell, item)
                            ToastType.WARNING -> createWarningToast(toastLayout, shell, item)
                            ToastType.NORMAL -> createNormalToast(toastLayout, shell, item)
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
        shell: View,
        item: ToastModel
    ) {
        if(item.leftImage==null){
            imgLeft.visibility = GONE
        }else {
            imgLeft.setImageDrawable(item.leftImage)
        }

        if(item.message==null){
            description.visibility = GONE
        }else {
            description.text = item.message
        }
        title.text = item.title

        shell.background = context.resources.getDrawable(R.drawable.bg_normal)
        showToast(toastLayout)
        cancel(item)
    }

    private fun createSuccessToast(
        toastLayout: View,
        shell: View,
        item: ToastModel
    ) {
        if(item.leftImage==null){
            imgLeft.visibility = GONE
        }else {
            imgLeft.setImageDrawable(item.leftImage)
        }

        if(item.message==null){
            description.visibility = GONE
        }else {
            description.text = item.message
        }
        title.text = item.title

        shell.background = context.resources.getDrawable(R.drawable.bg_success)
        showToast(toastLayout)
        cancel(item)
    }

    private fun cancel(item: ToastModel){
        val time: Long = if(item.length==ToastLength.LONG){
            3500
        }else {
            2000
        }
        Timer().schedule(time){
            val updatedItems = listen.value as ArrayList<ToastModel>
            updatedItems.remove(item)
            toastShown.postValue(false)
        }
    }

    private fun createErrorToast(
        toastLayout: View,
        shell: View,
        item: ToastModel
    ) {
        TODO("Not yet implemented")
    }

    private fun createWarningToast(
        toastLayout: View,
        shell: View,
        item: ToastModel
    ) {
        TODO("Not yet implemented")
    }

    private fun showToast(toastLayout: View) {
        toast = Toast(context)
        toast.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM,0,0)
        toast.duration = Toast.LENGTH_LONG
        toast.view = toastLayout
        toast.show()
    }
}