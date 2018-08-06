package me.calebbassham.scenariomanager.api.skript.condition

import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.calebbassham.scenariomanager.api.Scenario
import org.bukkit.event.Event

class CondScenario : Condition() {

    private var scenario: Expression<Scenario>? = null
    private var enabled: Boolean = true

    override fun check(e: Event?) = scenario?.check(e, { it.isEnabled }, isNegated) == enabled

    override fun toString(e: Event?, debug: Boolean): String = "Scenario enabled condition: ${scenario?.toString(e, debug)}"

    @Suppress("UNCHECKED_CAST")
    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean?, parseResult: SkriptParser.ParseResult?): Boolean {
        scenario = exprs[0] as Expression<Scenario>
        enabled = matchedPattern == 0
        return true
    }
}