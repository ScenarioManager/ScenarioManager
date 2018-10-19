package me.calebbassham.scenariomanager.api.uhc

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import org.bukkit.scoreboard.Team
import java.util.*

open class DefaultTeamProvider : TeamProvider {

    private val scoreboard
        get() = Bukkit.getScoreboardManager().mainScoreboard

    override fun registerTeam(name: String?, color: ChatColor?): Team? {
        if (name == null) {
            return registerTeam(UUID.randomUUID().toString().replace("-", "").slice(0..15), color)
        }

        try {
            val team = scoreboard.registerNewTeam(name)

            color?.let {
                team.prefix = color.toString()
            }

            return team
        } catch (e: IllegalArgumentException) {
            return null
        }
    }

    override fun registerTeam(name: String?) = registerTeam(name, null)

    override fun registerTeam() = registerTeam(null)

    override fun mustRegisterTeam(name: String?, color: ChatColor?): Team {
        if(name == null) {
            return mustRegisterTeam(UUID.randomUUID().toString().replace("-", "").slice(0..15), color)
        }

        scoreboard.getTeam(name)?.unregister()
        val team = scoreboard.registerNewTeam(name)

        color?.let {
            team.prefix = color.toString()
        }

        return team
    }

    override fun mustRegisterTeam(name: String?) = mustRegisterTeam(null, null)

    override fun mustRegisterTeam() = mustRegisterTeam(null)

    override fun getTeam(name: String) = scoreboard.getTeam(name)

    override fun getPlayerTeam(player: OfflinePlayer) = scoreboard.getTeam(player.name)
}