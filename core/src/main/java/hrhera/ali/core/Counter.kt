package hrhera.ali.core

import android.os.CountDownTimer

class Counter(
    private val onCounterFinish: () -> Unit,
    private val onTimeChange: (Long) -> Unit,
) {
    private var timer: CountDownTimer? = null

    fun start(time: Int) {
        timer?.cancel()
        timer = null
        timer = object : CountDownTimer(time*1000L, 1000) {
            override fun onFinish() {
                this@Counter.onFinish()
            }

            override fun onTick(millisUntilFinished: Long) {
                this@Counter.onTick(millisUntilFinished)
            }
        }
        timer?.start()
    }

    private var timeLeft: Long = 0L
    fun onFinish() {
        onCounterFinish()
    }

    fun onTick(millisUntilFinished: Long) {
        timeLeft = millisUntilFinished
        onTimeChange(millisUntilFinished)
    }

    fun onPause(getTime: (Long) -> Unit) {
        timer?.cancel()
        getTime(timeLeft)
    }

    fun cancel(){
        timer?.cancel()
        timer = null
    }



}