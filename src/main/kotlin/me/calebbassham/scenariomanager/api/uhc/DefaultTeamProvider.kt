package me.calebbassham.scenariomanager.api.uhc

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.scoreboard.Team

class DefaultTeamProvider : TeamProvider {

    private val scoreboard
        get() = Bukkit.getScoreboardManager().mainScoreboard

    override fun registerTeam(name: String): Team? =
        try {
            scoreboard.registerNewTeam(name)
        } catch (e: IllegalArgumentException) {
            null
        }


    override fun mustRegisterTeam(name: String): Team {
        scoreboard.getTeam(name)?.unregister()
        return scoreboard.registerNewTeam(name)
    }

    override fun getTeam(name: String) = scoreboard.getTeam(name)

    override fun getPlayerTeam(player: OfflinePlayer) = scoreboard.getTeam(player.name)
}