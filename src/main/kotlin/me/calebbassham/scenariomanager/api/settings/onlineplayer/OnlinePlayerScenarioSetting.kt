package me.calebbassham.scenariomanager.api.settings.onlineplayer

import me.calebbassham.scenariomanager.api.settings.SimpleScenarioSetting
import org.bukkit.entity.Player

class OnlinePlayerScenarioSetting(name: String, description: String, player: Player) : SimpleScenarioSetting<OnlinePlayer>(name, description, OnlinePlayer(player))