package me.calebbassham.scenariomanager.api

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect

/**
 * A [Scenario] can be registered to a [ScenarioManager] using [ScenarioManager.registerScenario].
 * If a scenario implements [Listener], the event handlers within the
 * scenario will be registered when a game begins if the scenario is enabled
 * and unregistered when the game ends or on disable.
 * @param plugin The [plugin][JavaPlugin] that this scenario is instantiated from.
 * @constructor Instantiates a new scenario.
 */
abstract class Scenario(internal val plugin: JavaPlugin) {

    /**
     * The name of the scenario.
     */
    abstract val name: String

    /**
     * The description of the scenario.
     */
    abstract val description: String

    /**
     * Determines if this scenario is enabled.
     */
    var isEnabled = false
        set(value) {
            if (field == value) return

            field = value

            if (!value && this is Listener) HandlerList.unregisterAll(this)
        }

    /**
     * @param player The [Player] to apply the [PotionEffects][PotionEffect] to.
     * @return An [Array] of [PotionEffect] to apply to the given [Player] at the start of the game.
     */
    open fun getStartingPotionEffects(player: Player): Array<PotionEffect>? = null

    /**
     * @param player The [Player] to give the [ItemStacks][ItemStack] to.
     * @return An [Array] of [ItemStack]
     */
    open fun getStartingItems(player: Player): Array<ItemStack>? = null

    /**
     * Called when the game is started.
     */
    open fun onGameStart() {}

    /**
     * Called when the game is stopped.
     */
    open fun onGameStop() {}
}