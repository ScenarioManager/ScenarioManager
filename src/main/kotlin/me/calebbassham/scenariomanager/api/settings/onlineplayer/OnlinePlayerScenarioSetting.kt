package me.calebbassham.scenariomanager.api.settings.onlineplayer

import me.calebbassham.scenariomanager.ScenarioManagerUtils
import me.calebbassham.scenariomanager.api.settings.SimpleScenarioSetting
import org.bukkit.entity.Player

class OnlinePlayerScenarioSetting(name: String, description: String, player: Player) : SimpleScenarioSetting<OnlinePlayer>(name, description, OnlinePlayer(player)) {
    override fun displayValue() = value.player?.displayName ?: value.offlinePlayer.name
}

class OnlinePlayerArrayScenarioSetting(name: String, description: String, players: Array<Player>) : SimpleScenarioSetting<Array<out OnlinePlayer>>(name, description, players.map { OnlinePlayer(it) }.toTypedArray()) {
    override fun displayValue() = ScenarioManagerUtils.formatList(value.map { it.player?.displayName ?: it.offlinePlayer.name})
}