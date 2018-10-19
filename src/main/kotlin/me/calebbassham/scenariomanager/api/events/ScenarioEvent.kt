package me.calebbassham.scenariomanager.api.events

import me.calebbassham.scenariomanager.api.Scenario

/**
 * @param name The name of the event
 * @param hide Should this event be hidden from players in places such as a timers command or an action bar timer?
 */
abstract class ScenarioEvent(val name: String, val hide: Boolean = false) : Runnable {

    lateinit var scenario: Scenario

    open fun onTick(ticksRemaining: Long) {}

}