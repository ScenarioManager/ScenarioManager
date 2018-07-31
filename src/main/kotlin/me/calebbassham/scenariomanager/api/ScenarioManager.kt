package me.calebbassham.scenariomanager.api

import org.bukkit.Bukkit
import org.bukkit.event.Listener

class ScenarioManager {

    private val scenarios = HashMap<String, Scenario>()

    /**
     * @return All [scenarios][Scenario] that are registered to this scenario manager.
     */
    val registeredScenarios: Array<Scenario>
            get() = scenarios.values.toTypedArray()

    /**
     * All [scenarios][Scenario] that are currently enabled.
     */
    val enabledScenarios: Array<Scenario>
            get() = scenarios.values.filter { it.isEnabled }.toTypedArray()

    /**
     * @param scenario The [Scenario] to register to this [ScenarioManager]
     * @throws ScenarioNameConflict When another scenario is already registered with the same name.
     * @throws ScenarioAlreadyRegistered When the same scenario is already registered.
     */
    fun registerScenario(scenario: Scenario) {
        val existing = scenarios[scenario.name]
        if (existing != null) {
            if (existing.javaClass.isInstance(scenario)) {
                throw ScenarioAlreadyRegistered(scenario.name)
            } else {
                throw ScenarioNameConflict(scenario.name)
            }
        }

        scenarios[scenario.name] = scenario
    }

    /**
     * @param name The name of the scenario to check.
     * @return Is the scenario registered?
     */
    fun isRegistered(name: String): Boolean = getScenario(name) != null

    /**
     * @param clazz The class of the scenario to check.
     * @return Is the scenario registered?
     */
    fun <T : Scenario> isRegistered(clazz: Class<T>): Boolean = getScenario(clazz) != null

    /**
     * @param name The name of the scenario to get, case sensitive.
     * @return The [Scenario] or null if no scenario with the given name is registered.
     */
    fun getScenario(name: String): Scenario? = scenarios[name]

    fun <T : Scenario> getScenario(clazz: Class<T>): Scenario? = scenarios.values.firstOrNull { it.javaClass == clazz }

    /**
     * Will trigger the [Scenario.onGameStart] method for all enabled scenarios
     * and register their event handlers.
     */
    fun onGameStart() {
        enabledScenarios.forEach {
            it.onGameStart()

            if (it is Listener) Bukkit.getPluginManager().registerEvents(it, it.plugin)
        }
    }

    /**
     * Will trigger the [Scenario.onGameStop] method for all enabled scenarios.
     */
    fun onGameStop() {
        enabledScenarios.forEach { it.onGameStop() }
    }
}