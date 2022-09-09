package com.collegedunia.toast

import android.graphics.drawable.Drawable
import java.util.*

data class ToastModel(val type: ToastType, val title: String?, val message: String?, val leftImage: Drawable?, val length: ToastLength? = ToastLength.SHORT) {
    override fun equals(other: Any?): Boolean{
        return when (other) {
            is ToastModel -> {
                this.type == other.type &&
                        this.title == other.title &&
                        this.message == other.message
            }
            else -> false
        }
    }

}
