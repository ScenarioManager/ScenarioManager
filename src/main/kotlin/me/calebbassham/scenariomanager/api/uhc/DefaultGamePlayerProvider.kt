package me.calebbassham.scenariomanager.api.uhc

import org.bukkit.Bukkit
import org.bukkit.entity.Player

open class DefaultGamePlayerProvider : GamePlayerProvider {
    override val gamePlayers: List<Player>
        get() = Bukkit.getOnlinePlayers().toList()
}