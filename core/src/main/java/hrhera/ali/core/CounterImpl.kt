package hrhera.ali.core

import android.os.CountDownTimer

class CounterImpl(
    private val onCounterFinish: () -> Unit,
    private val onTimeChange: (Long) -> Unit,
) :Counter{
    private var timer: CountDownTimer? = null

    override fun start(time: Int) {
        timer?.cancel()
        timer = null
        timer = object : CountDownTimer(time*1000L, 1000) {
            override fun onFinish() {
                this@CounterImpl.onFinish()
            }

            override fun onTick(millisUntilFinished: Long) {
                this@CounterImpl.onTick(millisUntilFinished)
            }
        }
        timer?.start()
    }

    private var timeLeft: Long = 0L
    override fun onFinish() {
        onCounterFinish()
    }

    override fun onTick(millisUntilFinished: Long) {
        timeLeft = millisUntilFinished
        onTimeChange(millisUntilFinished)
    }

    override fun onPause(getTime: (Long) -> Unit) {
        timer?.cancel()
        getTime(timeLeft)
    }

    override fun cancel(){
        timer?.cancel()
        timer = null
    }



}