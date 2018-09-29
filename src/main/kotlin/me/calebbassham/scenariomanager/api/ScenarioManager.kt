package me.calebbassham.scenariomanager.api

import me.calebbassham.scenariomanager.api.uhc.GamePlayerProvider
import me.calebbassham.scenariomanager.api.events.ScenarioEventScheduler
import me.calebbassham.scenariomanager.api.uhc.GameProvider
import me.calebbassham.scenariomanager.api.uhc.GameWorldProvider
import org.bukkit.plugin.java.JavaPlugin

interface ScenarioManager {
    var eventScheduler: ScenarioEventScheduler

    var gamePlayerProvider: GamePlayerProvider
    var gameWorldProvider: GameWorldProvider
    var gameProvider: GameProvider

    val scenarios: Set<Scenario>

    fun register(scenario: Scenario, plugin: JavaPlugin)

    fun getPrefix(scenario: Scenario): String
}