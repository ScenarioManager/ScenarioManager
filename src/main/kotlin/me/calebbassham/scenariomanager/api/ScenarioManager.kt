package me.calebbassham.scenariomanager.api

import me.calebbassham.scenariomanager.api.events.ScenarioEventScheduler
import me.calebbassham.scenariomanager.api.uhc.GamePlayerProvider
import me.calebbassham.scenariomanager.api.uhc.GameProvider
import me.calebbassham.scenariomanager.api.uhc.GameWorldProvider
import me.calebbassham.scenariomanager.api.uhc.TeamProvider
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.Scoreboard

interface ScenarioManager {
    var eventScheduler: ScenarioEventScheduler

    var gamePlayerProvider: GamePlayerProvider
    var teamProvider: TeamProvider
    var gameWorldProvider: GameWorldProvider
    var gameProvider: GameProvider

    val scenarios: Set<Scenario>

    fun register(scenario: Scenario, plugin: JavaPlugin)

    fun getPrefix(scenario: Scenario): String

    /**
     * The scoreboard to use with team scenarios.
     */
    val scoreboard: Scoreboard
}