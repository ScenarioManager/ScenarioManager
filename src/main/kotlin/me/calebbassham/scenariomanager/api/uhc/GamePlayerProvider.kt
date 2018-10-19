package me.calebbassham.scenariomanager.api.uhc

import org.bukkit.entity.Player

/**
 * Provides game player information to the scenario manager.
 */
interface GamePlayerProvider {

    /**
     * All players that are alive in the game.
     * Used by scenarios to know what players to
     * give items, effects, etc... to.
     */
    val gamePlayers: List<Player>

    /**
     * Is a player playing in the game and alive?
     * @see gamePlayers
     */
    fun isGamePlayer(player: Player) = gamePlayers.contains(player)
}
