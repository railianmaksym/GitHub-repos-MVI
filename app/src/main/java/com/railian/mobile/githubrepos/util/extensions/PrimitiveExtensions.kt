package com.railian.mobile.githubrepos.util.extensions

fun Float.round(decimals: Int = 2): Float = "%.${decimals}f".format(this).toFloat()