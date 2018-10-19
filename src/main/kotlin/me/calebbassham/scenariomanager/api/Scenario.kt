package me.calebbassham.scenariomanager.api

import me.calebbassham.scenariomanager.api.settings.ScenarioSetting
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin


interface Scenario {

    val name: String

    val description: String

    val authors: Array<out String>?

    var isEnabled: Boolean

    val settings: List<ScenarioSetting<*>>?

    /**
     * The plugin that registered the scenario.
     * null if the plugin has not been registered.
     */
    val registeringPlugin: JavaPlugin?

    /**
     * When the scenario is started, usually at the start of the
     * game unless the scenario is enabled in the middle of the game.
     */
    fun onScenarioStart() {}

    /**
     * This method should be used to handle doing things to players when the game starts.
     * Such as applying potion effects, giving items, setting max health, etc.
     * @param player The player that is starting.
     */
    fun onPlayerStart(player: Player) {}

    /**
     * Called when the scenario is stopped, usually at the end of the game
     * unless the scenario is stopped in the middle of the game.
     *
     * Game events are automatically canceled when a scenario is stopped.
     *
     * If the scenario gives players things like items, potions effect, etc...
     * they should be removed here.
     */
    fun onScenarioStop() {}
}