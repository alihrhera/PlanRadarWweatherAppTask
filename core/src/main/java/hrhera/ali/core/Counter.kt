package hrhera.ali.core


interface Counter {
    fun start(time: Int)

    fun onFinish()

    fun onTick(millisUntilFinished: Long)

    fun onPause(getTime: (Long) -> Unit)

    fun cancel()



}