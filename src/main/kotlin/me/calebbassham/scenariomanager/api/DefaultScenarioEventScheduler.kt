package me.calebbassham.scenariomanager.api

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

internal class DefaultScenarioEventScheduler(private val plugin: JavaPlugin) : ScenarioEventScheduler {

    override val events = HashMap<ScenarioEvent, Long>()
    private var timer: Timer? = null

    override fun scheduleEvent(event: ScenarioEvent, ticks: Long) {
        events[event] = ticks
    }

    private inner class Timer : BukkitRunnable() {

        override fun run() {

            for ((event, triggerTicks) in events) {
                if (triggerTicks <= 0) {
                    events.remove(event)
                    event.run()
                } else{
                    events[event] = triggerTicks - 1
                }
            }
        }

    }

    /**
     * Start the timer that handlers triggering events.
     * Should be called at the start of a game.
     * @throws TimerRunning If the timer is running.
     */
    fun startTimer() {
        if (timer != null) throw TimerRunning()
        timer = Timer()
        timer?.runTaskTimer(plugin, 0, 1)
    }

    /**
     * Stop the timer that handlers triggering events.
     * Should be called at the end of a game.
     * @throws TimerNotRunning If the timer is not running.
     */
    fun stopTimer() {
        if (timer == null) throw TimerNotRunning()
        timer?.cancel()
        timer = null
    }

}