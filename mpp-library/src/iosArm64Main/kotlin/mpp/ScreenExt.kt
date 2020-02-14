package org.example.mpp

import dev.icerock.moko.widgets.screen.Screen
import platform.UIKit.UIApplication
import platform.Foundation.NSURL

actual fun Screen<*>.openUrl(url: String) {
    UIApplication.sharedApplication.openURL(NSURL.URLWithString(url)!!)
}