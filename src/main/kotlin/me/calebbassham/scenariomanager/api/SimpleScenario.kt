package me.calebbassham.scenariomanager.api

import me.calebbassham.scenariomanager.ScenarioManagerUtils
import me.calebbassham.scenariomanager.api.events.ScenarioEvent
import me.calebbassham.scenariomanager.api.settings.ScenarioSetting
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

/**
 * A [Scenario] can be registered to a [ScenarioManager] using [ScenarioManager.registerScenario].
 * If a scenario implements [Listener], the event handlers within the
 * scenario will be registered when a game begins if the scenario is enabled
 * and unregistered when the game ends or on disable.
 * @param name The name of the scenario.
 * @param plugin The [plugin][JavaPlugin] that this scenario is instantiated from.
 * @constructor Instantiates a new scenario.
 */
open class SimpleScenario : Scenario {

    /**
     * The scenario manager this scenario is registered to.
     * Not initialized until the scenario is registered.
     */
    lateinit var scenarioManager: ScenarioManager

    /**
     * The plugin that registered this scenario.
     * Not initialized until the scenario is registered.
     */
    lateinit var plugin: JavaPlugin

    override val registeringPlugin: JavaPlugin
        get() = plugin

    override val name: String
        get() = ScenarioManagerUtils.humanizePascalCase(plugin.description.name)

    override val description: String
        get() = plugin.description.description

    override val authors: Array<out String>?
        get() = plugin.description.authors.toTypedArray()

    override val settings: List<ScenarioSetting<*>>?
        get() = null

    override var isEnabled = false
        set(value) {

            if(value && scenarioManager.gameProvider.isGameRunning() && this is Listener) {
                Bukkit.getPluginManager().registerEvents(this, plugin)
            }

            if (!value) {
                removeScheduledEvents()

                if(this is Listener) {
                    HandlerList.unregisterAll(this)
                }
            }

            field = value
        }

    /**
     * Shortcut to get the prefix of this scenario.
     * @see ScenarioManager.getPrefix
     */
    val prefix: String
        get() = scenarioManager.getPrefix(this)

    /**
     * Shortcut to schedule a scenario event.
     * @see ScenarioEventScheduler.scheduleEvent
     */
    protected fun scheduleEvent(event: ScenarioEvent, ticks: Long, fromStartOfGame: Boolean = false) {
        scenarioManager.eventScheduler.scheduleEvent(this, event, ticks, fromStartOfGame)
    }

    /**
     * Broadcast a message with the scenario prefix.
     * @param msg The message to send to the player.
     */
    protected fun broadcast(msg: String) {
        Bukkit.getOnlinePlayers().forEach { it.sendMessage("$prefix$msg") }
    }

    /**
     * Send a message to a player with the scenario prefix.
     * @param player The player to send the message to.
     * @param msg The message to send to the player.
     */
    protected fun sendMessage(player: Player, msg: String) {
        player.sendMessage("$prefix$msg")
    }

    /**
     * Shortcut to remove all vents scheduled by this scenario.
     * @see ScenarioEventScheduler.removeScheduledEvents
     */
    protected fun removeScheduledEvents() {
        scenarioManager.eventScheduler.removeScheduledEvents(this)
    }
}