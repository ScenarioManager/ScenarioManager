package me.calebbassham.scenariomanager.api.skript.expression

import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import me.calebbassham.scenariomanager.api.Scenario
import org.bukkit.event.Event

class ExprScenarioDescription : SimpleExpression<String>() {

    private var scenario: Expression<Scenario>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(exprs: Array<out Expression<*>>, p1: Int, p2: Kleenean?, p3: SkriptParser.ParseResult?): Boolean {
        scenario = exprs[0] as Expression<Scenario>
        return true
    }

    override fun get(e: Event?): Array<String>? {
        val scenario = scenario?.getSingle(e) ?: return null
        return arrayOf(scenario.description)
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "Scenario description expression: ${scenario?.toString(e, debug)}"
    }

    override fun isSingle() = true

    override fun getReturnType() = String::class.java
}