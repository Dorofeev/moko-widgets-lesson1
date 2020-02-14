package org.example.mpp

import android.content.Intent
import android.net.Uri
import dev.icerock.moko.widgets.screen.Screen

actual fun Screen<*>.openUrl(url: String) {
    val context = requireContext()
    val openIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    if (openIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(openIntent)
    }
}