@file:JvmName("ScenarioManagerUtils")
package me.calebbassham.scenariomanager

object ScenarioManagerUtils {

    @JvmStatic
    fun formatTicks(ticks: Long): String {
        var t = ticks

        val ticksPerSecond = 20
        val ticksPerMinute = ticksPerSecond * 60
        val ticksPerHour = ticksPerMinute * 60

        val hours = t / ticksPerHour
        t %= ticksPerHour

        val minutes = t / ticksPerMinute
        t %= ticksPerMinute

        val seconds = t / ticksPerSecond

        val sb = StringBuilder()

        if (hours > 0) {
            sb.append(hours.toString() + "h")
        }

        if (minutes > 0) {
            sb.append(minutes.toString() + "m")
        }

        if (seconds > 0 || sb.isEmpty()) {
            sb.append(seconds.toString() + "s")
        }

        return sb.toString()
    }
}