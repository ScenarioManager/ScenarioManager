package me.calebbassham.scenariomanager.api

import me.calebbassham.scenariomanager.api.events.DefaultScenarioEventScheduler
import me.calebbassham.scenariomanager.api.events.ScenarioEventScheduler
import me.calebbassham.scenariomanager.api.exceptions.ScenarioSettingParseException
import me.calebbassham.scenariomanager.api.settings.ScenarioSettingParser
import me.calebbassham.scenariomanager.api.settings.parsers.IntParser
import me.calebbassham.scenariomanager.api.settings.parsers.TimeSpanParser
import me.calebbassham.scenariomanager.api.settings.timespan.TimeSpan
import me.calebbassham.scenariomanager.api.skript.event.GameStartEvent
import me.calebbassham.scenariomanager.api.skript.event.GameStopEvent
import me.calebbassham.scenariomanager.api.skript.event.PlayerStartEvent
import me.calebbassham.scenariomanager.api.uhc.*
import me.calebbassham.scenariomanager.plugin.ScenarioManagerPlugin
import me.calebbassham.scenariomanager.plugin.log
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

open class SimpleScenarioManager(plugin: JavaPlugin) : ScenarioManager {

    override var eventScheduler: ScenarioEventScheduler = DefaultScenarioEventScheduler(plugin)

    override var gamePlayerProvider: GamePlayerProvider = DefaultGamePlayerProvider()

    override var gameWorldProvider: GameWorldProvider = DefaultGameWorldProvider()

    override var gameProvider: GameProvider = DefaultGameProvider()

    private val internalScenarios = HashSet<Scenario>()

    override val scenarios
        get() = internalScenarios.toSet()

    override fun register(scenario: Scenario, plugin: JavaPlugin) {

//        val existing = getScenario(scenario.name)
//        if (existing != null) {
//            if (existing.javaClass.isInstance(scenario)) {
//                throw ScenarioAlreadyRegistered(scenario.name)
//            } else {
//                throw ScenarioNameConflict(scenario.name)
//            }
//        }

        if(scenario is SimpleScenario) {
            scenario.scenarioManager = this
            scenario.plugin = plugin
        }

        internalScenarios.add(scenario)
    }

    open val scenarioSettingParsers = HashMap<Class<*>, ScenarioSettingParser<*>>().apply {
        put(TimeSpan::class.java, TimeSpanParser())
        put(Int::class.java, IntParser())
        put(java.lang.Integer::class.java, IntParser())
    }

    inline fun <reified T> parseScenarioSetting(input: String): T = scenarioSettingParsers[T::class.java]?.parse(input) as T ?: throw ScenarioSettingParseException("no parser for class ${T::class.java.name}")

    override fun getPrefix(scenario: Scenario) = ChatColor.translateAlternateColorCodes('&', "&8[&a${scenario.name}&8]&7 ")

    /**
     * @param name The name of the scenario to get, case sensitive.
     * @return The [Scenario] or null if no scenario with the given name is registered.
     */
    fun getScenario(name: String): Scenario? = scenarios.firstOrNull { it.name.equals(name, true) }

    fun <T : Scenario> getScenario(clazz: Class<T>): Scenario? = scenarios.firstOrNull { it.javaClass == clazz }

    /**
     * Will trigger the [Scenario.onScenarioStart] method for all enabled scenarios
     * and register their event handlers. This should also be where you schedule [ScenarioEvent].
     *
     * @param players The players that are playing the game. [Scenario.onPlayerStart] will be called with all of the players.
     */
    fun onGameStart(players: Array<Player>) {
        log.info("started with ${players.joinToString(", ")}")

        (eventScheduler as? DefaultScenarioEventScheduler)?.startTimer()

        scenarios.filter { it.isEnabled }.forEach { scenario ->
            scenario.onScenarioStart()
            players.forEach { player ->
                scenario.onPlayerStart(player)
                Bukkit.getPluginManager().callEvent(PlayerStartEvent(player))
            }

            if (scenario is Listener) Bukkit.getPluginManager().registerEvents(scenario, scenario.registeringPlugin)
            if (ScenarioManagerPlugin.instance.skriptAddon != null) {
                Bukkit.getPluginManager().callEvent(GameStartEvent())
            }
        }
    }

    /**
     * Will trigger the [Scenario.onScenarioStop] method for all enabled scenarios.
     */
    fun onGameStop() {
        scenarios.filter { it.isEnabled }.forEach { it.onScenarioStop() }

        (eventScheduler as? DefaultScenarioEventScheduler)?.stopTimer()

        if (ScenarioManagerPlugin.instance.skriptAddon != null) {
            Bukkit.getPluginManager().callEvent(GameStopEvent())
        }
    }

    fun onPlayerStart(player: Player) {
        scenarios.filter { it.isEnabled }.forEach { scenario ->
            scenario.onPlayerStart(player)
        }
        Bukkit.getPluginManager().callEvent(PlayerStartEvent(player))
    }
}