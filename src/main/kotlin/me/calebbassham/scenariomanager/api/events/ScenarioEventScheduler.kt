package me.calebbassham.scenariomanager.api.events

import me.calebbassham.scenariomanager.api.Scenario

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
