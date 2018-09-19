package me.calebbassham.scenariomanager.api

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

interface ScenarioEventScheduler {

    /**
     * @param event The event to schedule
     * @param ticks How many ticks before the event should run
     * @param fromStartOfGame If true, event is scheduled x ticks from when the game started. If false (default), event is scheduled x ticks from now.
     */
    fun scheduleEvent(scenario: Scenario, event: ScenarioEvent, ticks: Long, fromStartOfGame: Boolean = false)

    /**
     * Remove all events scheduled by a certain scenario.
     * @param scenario The scenario whose events should be unscheduled.
     */
    fun removeScheduledEvents(scenario: Scenario)

    /**
     * The Long in the HashMap<ScenarioEvent, Long> should be the ticks remaining until the event.
     * @return All of the scheduled events.
     */
    val events: HashMap<ScenarioEvent, Long>

}

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