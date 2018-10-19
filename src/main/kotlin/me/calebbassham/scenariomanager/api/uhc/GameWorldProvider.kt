package me.calebbassham.scenariomanager.api.uhc

import org.bukkit.Location
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

    /**
     * The radius of the world or null if it is not a game world.
     * @param world The [World] to get the radius for.
     */
    fun getMapRadius(world: World): Int?

    /**
     * The center of the map or null if it is not a game world; usually it is (0,0).
     * Only x and z should be used from the [Location] returned.
     * @param world The [World] to get the map center for
     */
    fun getMapCenter(world: World): Location?

}