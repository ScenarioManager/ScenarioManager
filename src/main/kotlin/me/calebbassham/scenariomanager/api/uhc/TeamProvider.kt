package me.calebbassham.scenariomanager.api.uhc

import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import org.bukkit.scoreboard.Team

interface TeamProvider {

    /**
     * Return a team with [name] or null if the team could not be created.
     * @param name The name of the team or null, if the name is null it will give you a random name.
     * @param color The color of the team or null, if [color] is null than the team color will be handled by the inheriting class.
     */
    fun registerTeam(name: String?, color: ChatColor?): Team?

    fun registerTeam(name: String?): Team?

    fun registerTeam(): Team?

    /**
     * Does whatever is necessary to create a team; for instance, this could unregister any team with the same name
     * before trying to create a new team with name [name].
     * @param name The name of the team or null, if the name is null it will give you a random name.
     * @param color The color of the team or null, if [color] is null than the team color will be handled by the inheriting class.
     */
    fun mustRegisterTeam(name: String?, color: ChatColor?): Team

    fun mustRegisterTeam(name: String?): Team

    fun mustRegisterTeam(): Team

    /**
     * Returns the team with the name [name] or null if no team exists with the name [name]
     */
    fun getTeam(name: String): Team?

    /**
     * Returns the team of [player] or null if [player] is not on a team.
     */
    fun getPlayerTeam(player: OfflinePlayer): Team?

}