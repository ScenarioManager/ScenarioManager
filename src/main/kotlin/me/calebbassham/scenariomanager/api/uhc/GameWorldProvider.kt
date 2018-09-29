package me.calebbassham.scenariomanager.api.uhc

import org.bukkit.World

/**
 * Provides game world information to the scenario manager.
 */
interface GameWorldProvider {

    /**
     * All of the worlds that are used for the game.
     * Used by scenarios to know what worlds to not
     * put time bombs in and such.
     */
    val gameWorlds: List<World>

    /**
     * Is the world used in the game?
     * @see gameWorlds
     */
    fun isGameWorld(world: World) = gameWorlds.contains(world)

}