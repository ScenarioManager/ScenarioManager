package me.calebbassham.scenariomanager.api.skript.condition

import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.calebbassham.scenariomanager.plugin.ScenarioManagerPlugin
import org.bukkit.event.Event

class CondScenarioRegistered : Condition() {

    var name: Expression<String>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, p2: Kleenean?, p3: SkriptParser.ParseResult?): Boolean {
        name = exprs[0] as Expression<String>
        isNegated = matchedPattern == 0
        return true
    }

    override fun check(e: Event) = name?.check(e, { ScenarioManagerPlugin.scenarioManager.isRegistered(it) }, isNegated) == true

    override fun toString(e: Event?, debug: Boolean) = "Scenario registered condition: ${name?.toString(e, debug)}"

}