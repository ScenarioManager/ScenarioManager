package me.calebbassham.scenariomanager.api

import org.bukkit.Bukkit
import org.bukkit.World

/**
 * Provides game world information to the scenario manager.
 */
abstract class GameWorldProvider {

    /**
     * All of the worlds that are used for the game.
     * Used by scenarios to know what worlds to not
     * put time bombs in and such.
     */
    abstract val gameWorlds: List<World>

    /**
     * Is the world used in the game?
     * @see gameWorlds
     */
    open fun isGameWorld(world: World) = gameWorlds.contains(world)

}

class DefaultGameWorldProvider : GameWorldProvider() {

    override val gameWorlds: List<World>
        get() = Bukkit.getWorlds()

}