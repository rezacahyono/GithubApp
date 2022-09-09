package com.rchyn.githubapp.util


import android.view.View
import kotlin.math.ln
import kotlin.math.pow


fun Int.prettyNumber(): String {
    if (this < 1000) return "" + this
    val exp = (ln(this.toDouble()) / ln(1000.0)).toInt()
    return String.format("%.1f%c", this / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun String.addAtSign() = if (this.isNotBlank()) "@".plus(this) else ""

fun String.addHttps() = if (this.contains("https://")) this else "https://".plus(this)
