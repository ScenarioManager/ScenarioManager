package me.calebbassham.scenariomanager.api

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

interface ScenarioEventScheduler {

    /**
     * @param event The event to schedule
     * @param ticks How many ticks before the event should run
     */
    fun scheduleEvent(event: ScenarioEvent, ticks: Long)

    /**
     * The Long in the HashMap<ScenarioEvent, Long> should be the ticks remaining until the event.
     * @return All of the scheduled events. If null is returned then /sm timers will not work.
     */
    val events: HashMap<ScenarioEvent, Long>?

}

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

                event.onTick(triggerTicks)
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