package me.calebbassham.scenariomanager.api

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