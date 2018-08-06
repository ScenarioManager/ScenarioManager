package me.calebbassham.scenariomanager.api.skript.expression

import ch.njol.skript.expressions.base.PropertyExpression
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.calebbassham.scenariomanager.api.Scenario
import me.calebbassham.scenariomanager.plugin.ScenarioManagerPlugin
import org.bukkit.event.Event

class ExprScenario : PropertyExpression<String, Scenario>() {

    @Suppress("UNCHECKED_CAST")
    override fun init(exprs: Array<out Expression<*>>, p1: Int, p2: Kleenean?, p3: SkriptParser.ParseResult?): Boolean {
        expr = exprs[0] as Expression<out String>
        return true
    }

    override fun get(e: Event, source: Array<out String>): Array<Scenario> {
        return get(source, fun(name: String) = ScenarioManagerPlugin.scenarioManager?.getScenario(name))
    }

    override fun toString(e: Event?, debug: Boolean) = "The scenario ${expr.toString(e, debug)}"

    override fun getReturnType() = Scenario::class.java

}