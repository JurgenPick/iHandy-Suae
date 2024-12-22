package com.nssus.ihandy.data.extension

fun Int.padStart(): String = String.format("%02d", this)

fun Int.padStartCustom(resultDigitLength: Int = 2, padChar: String = "0"): String = toString().padStart(resultDigitLength, padChar.first())