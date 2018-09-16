package me.calebbassham.scenariomanager.api

/**
 * @param name The name of the event
 * @param hide Should this event be hidden from players in places such as a timers command or an action bar timer?
 */
abstract class ScenarioEvent(val name: String, val hide: Boolean = false) : Runnable {

    open fun onTick(ticksRemaining: Long) {}

}