package me.calebbassham.scenariomanager.api

import org.bukkit.Bukkit
import org.bukkit.ChatColor
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
abstract class Scenario(val name: String) {

    lateinit var plugin: JavaPlugin
        internal set

    lateinit var scenarioManager: ScenarioManager
        internal set

    /**
     * The description of the scenario.
     */
    open val description: String = plugin.description.description

    /**
     * Determines if this scenario is enabled.
     */
    var isEnabled = false
        set(value) {
            if (field == value) return

            field = value

            if (!value && this is Listener) {
                HandlerList.unregisterAll(this)
                removeScheduledEvents()
            }

            if (value && this is Listener && scenarioManager.gameProvider.isGameRunning()) {
                Bukkit.getPluginManager().registerEvents(this, plugin)
            }
        }

    open val settings: List<ScenarioSetting<*>>? = null

    /**
     * When the scenario is started, usually at the start of the
     * game unless the scenario is enabled in the middle of the game.
     */
    open fun onScenarioStart() {}

    /**
     * This method should be used to handle doing things to players when the game starts.
     * Such as applying potion effects, giving items, setting max health, etc.
     * @param player The player that is starting.
     */
    open fun onPlayerStart(player: Player) {}

    /**
     * Called when the scenario is stopped, usually at the end of the game
     * unless the scenario is stopped in the middle of the game.
     *
     * Game events are automatically canceled when a scenario is stopped.
     *
     * If the scenario gives players things like items, potions effect, etc...
     * they should be removed here.
     */
    open fun onScenarioStop() {}

    protected fun removeScheduledEvents() {
        scenarioManager.eventScheduler.removeScheduledEvents(this)
    }

    /**
     * Shortcut to schedule a scenario event.
     * @see ScenarioEventScheduler.scheduleEvent
     */
    protected fun scheduleEvent(event: ScenarioEvent, ticks: Long, fromStartOfGame: Boolean = false) {
        scenarioManager.eventScheduler.scheduleEvent(this, event, ticks, fromStartOfGame)
    }

    /**
     * The prefix to use when sending messages.
     */
    internal val prefix = ChatColor.translateAlternateColorCodes('&', "&8[&a$name&8]&7")

    /**
     * Broadcast a message with the scenario prefix.
     * @param msg The message to send to the player.
     */
    protected fun broadcast(msg: String) {
        Bukkit.getOnlinePlayers().forEach { it.sendMessage("$prefix $msg") }
    }

    /**
     * Send a message to a player with the scenario prefix.
     * @param player The player to send the message to.
     * @param msg The message to send to the player.
     */
    protected fun sendMessage(player: Player, msg: String) {
        player.sendMessage("$prefix $msg")
    }

}