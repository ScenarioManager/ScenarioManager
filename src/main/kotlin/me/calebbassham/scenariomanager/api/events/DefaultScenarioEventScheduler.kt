package me.calebbassham.scenariomanager.api.events

import me.calebbassham.scenariomanager.api.Scenario
import me.calebbassham.scenariomanager.api.exceptions.TimerNotRunning
import me.calebbassham.scenariomanager.api.exceptions.TimerRunning
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

internal class DefaultScenarioEventScheduler(private val plugin: JavaPlugin) : ScenarioEventScheduler {

    override val events = HashMap<ScenarioEvent, Long>()
    private var ticks: Long = 0
    private var timer: Timer? = null

    override fun scheduleEvent(scenario: Scenario, event: ScenarioEvent, ticks: Long, fromStartOfGame: Boolean) {
        event.scenario = scenario
        events[event] = if (fromStartOfGame) ticks - this.ticks else ticks
    }

    override fun removeScheduledEvents(scenario: Scenario) {
        val iter = events.iterator()
        while(iter.hasNext()) {
            val event = iter.next().key
            if (event.scenario == scenario) {
                iter.remove()
            }
        }
    }

    private inner class Timer : BukkitRunnable() {

        override fun run() {
            for ((event, triggerTicks) in events) {
                val ticksRemaining = triggerTicks - ticks
                event.onTick(ticksRemaining)
                if (ticksRemaining <= 0) {
                    events.remove(event)
                    event.run()
                }
            }

            ticks++
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