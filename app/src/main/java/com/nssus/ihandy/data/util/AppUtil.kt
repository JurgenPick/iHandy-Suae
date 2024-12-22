package com.nssus.ihandy.data.util

import android.os.CountDownTimer

object AppUtil {

    fun getCountDownTimer(
        countDownTimeInSec: Long,
        onGetRemainingTime: (Long) -> Unit ={},
        onCountDownFinish: () -> Unit
    )= object : CountDownTimer(countDownTimeInSec * 1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            onGetRemainingTime(millisUntilFinished / 1000)
        }
        override fun onFinish() { // count down COMPLETE
            onCountDownFinish()
        }
    }


}