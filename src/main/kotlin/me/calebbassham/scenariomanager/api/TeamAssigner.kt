package me.calebbassham.scenariomanager.api

import me.calebbassham.scenariomanager.api.uhc.TeamProvider
import org.bukkit.entity.Player
import java.util.concurrent.CompletableFuture

interface TeamAssigner {

    /**
     * Assign teams
     * @param teams A [TeamProvider] instance that can be used to do things with teams.
     */
    fun onAssignTeams(teams: TeamProvider, players: Array<Player>): CompletableFuture<Void>

}