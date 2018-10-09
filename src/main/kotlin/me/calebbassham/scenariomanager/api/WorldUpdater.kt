package me.calebbassham.scenariomanager.api

import org.bukkit.World
import java.util.concurrent.CompletableFuture

/**
 * Any scenario that does stuff with world generation should implement this.
 */
interface WorldUpdater {

    /**
     * Change the world.
     */
    fun generateWorld(world: World): CompletableFuture<Void>

}