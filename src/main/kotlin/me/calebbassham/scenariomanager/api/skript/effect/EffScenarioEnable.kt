package me.calebbassham.scenariomanager.api.skript.effect

import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.calebbassham.scenariomanager.api.Scenario
import org.bukkit.event.Event

class EffScenarioEnable : Effect() {

    var scenario: Expression<Scenario>? = null
    var enable = true

    @Suppress("UNCHECKED_CAST")
    override fun init(exprs: Array<out Expression<*>>, p1: Int, p2: Kleenean?, pr: SkriptParser.ParseResult): Boolean {
        scenario = exprs[0] as Expression<Scenario>
        enable = pr.expr.startsWith("enable")
        return true
    }

    override fun execute(e: Event) {
        scenario?.getArray(e)?.forEach { it.isEnabled = enable }
    }

    override fun toString(e: Event?, debug: Boolean) = (if (enable) "enable" else "disable") + " " + scenario?.toString(e, debug)
}