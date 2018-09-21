package me.calebbassham.scenariomanager

import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * Provides game player information to the scenario manager.
 */
abstract class GamePlayerProvider {

    /**
     * All players that are alive in the game.
     * Used by scenarios to know what players to
     * give items, effects, etc... to.
     */
    abstract val gamePlayers: List<Player>

    /**
     * Is a player playing in the game and alive?
     * @see gamePlayers
     */
    open fun isGamePlayer(player: Player) = gamePlayers.contains(player)

}

class DefaultGamePlayerProvider : GamePlayerProvider() {

    override val gamePlayers: List<Player>
        get() = Bukkit.getOnlinePlayers().toList()
}