package me.calebbassham.scenariomanager.api.uhc

/**
 * Provided game information to the scenario manager.
 */
interface GameProvider {

    /**
     * Used by skript scenarios to know when to not
     * react to events.
     */
    fun isGameRunning(): Boolean

}