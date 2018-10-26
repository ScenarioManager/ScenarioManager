package me.calebbassham.scenariomanager.api.settings.parsers

import me.calebbassham.scenariomanager.api.settings.ScenarioSettingParser
import me.calebbassham.scenariomanager.api.settings.onlineplayer.OnlinePlayer
import org.bukkit.Bukkit

class OnlinePlayerParser : ScenarioSettingParser<OnlinePlayer> {
    override fun parse(input: String) = OnlinePlayer(Bukkit.getPlayer(input))
}

class OnlinePlayerArrayParser : ScenarioSettingParser<Array<OnlinePlayer>> {
    override fun parse(input: String): Array<OnlinePlayer> =
        input.split(",").map { OnlinePlayer(Bukkit.getPlayer(input)) }.toTypedArray()
}