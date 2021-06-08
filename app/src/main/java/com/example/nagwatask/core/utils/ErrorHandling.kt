package com.example.nagwatask.core.utils

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar



fun showErrorSnackBar(view: View, msg: String) {
    if (msg.isNotBlank()) {
        Snackbar.make(
            view,
            msg,
            Snackbar.LENGTH_SHORT
        ).setBackgroundTint(Color.RED).show()
    }
}