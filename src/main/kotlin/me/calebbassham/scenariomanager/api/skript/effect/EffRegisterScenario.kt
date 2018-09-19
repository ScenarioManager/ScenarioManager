package me.calebbassham.scenariomanager.api.skript.effect

import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.calebbassham.scenariomanager.api.Scenario
import me.calebbassham.scenariomanager.api.ScenarioAlreadyRegistered
import me.calebbassham.scenariomanager.api.ScenarioNameConflict
import me.calebbassham.scenariomanager.api.scenarioManager
import me.calebbassham.scenariomanager.plugin.ScenarioManagerPlugin
import me.calebbassham.scenariomanager.plugin.log
import org.bukkit.event.Event

class EffRegisterScenario : Effect() {

    var name: Expression<String>? = null
    var description: Expression<String>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(exprs: Array<out Expression<*>>, p1: Int, p2: Kleenean?, p3: SkriptParser.ParseResult?): Boolean {
        name = exprs[0] as Expression<String>
        description = exprs[1] as Expression<String>
        return true
    }

    override fun execute(e: Event) {
        val name = this.name?.getSingle(e) ?: return
        val description = this.description?.getSingle(e) ?: return

        val scenario = object : Scenario(name) {
            override val description = description
        }

        try {
            scenarioManager?.registerScenario(scenario, ScenarioManagerPlugin.instance)
        } catch (e: ScenarioAlreadyRegistered) {
            log.info("Tried to register ${scenario.name} but it is already registered.")
        } catch (e: ScenarioNameConflict) {
            log.info("Tried to register ${scenario.name} but it is already registered.")
        }
    }

    override fun toString(e: Event?, debug: Boolean) = "Register scenario: ${name?.toString(e, debug)}  ${description?.toString(e, debug)}"

}