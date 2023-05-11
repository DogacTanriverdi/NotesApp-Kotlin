package com.dogactnrvrdi.notesapp.util

import android.content.Context
import android.widget.Toast

class Util {

    fun toastLong(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun toastShort(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}