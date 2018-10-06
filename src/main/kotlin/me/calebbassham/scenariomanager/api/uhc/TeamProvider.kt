package me.calebbassham.scenariomanager.api.uhc

import org.bukkit.OfflinePlayer
import org.bukkit.scoreboard.Team

interface TeamProvider {

    /**
     * Return a team with [name] or null if the team could not be created.
     */
    fun registerTeam(name: String): Team?

    /**
     * Does whatever is necessary to create a team; for instance, this could unregister any team with the same name
     * before trying to create a new team with name [name].
     */
    fun mustRegisterTeam(name: String): Team

    /**
     * Returns the team with the name [name] or null if no team exists with the name [name]
     */
    fun getTeam(name: String): Team?

    /**
     * Returns the team of [player] or null if [player] is not on a team.
     */
    fun getPlayerTeam(player: OfflinePlayer): Team?

}