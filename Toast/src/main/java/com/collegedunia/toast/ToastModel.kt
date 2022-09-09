package com.collegedunia.toast

import android.graphics.drawable.Drawable

data class ToastModel(val type: ToastType, val title: String?, val message: String?, val leftImage: Drawable?, val length: ToastLength? = ToastLength.SHORT)
