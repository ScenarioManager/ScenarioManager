package me.calebbassham.scenariomanager.api.skript.effect

import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.util.Timespan
import ch.njol.util.Kleenean
import me.calebbassham.scenariomanager.api.Scenario
import me.calebbassham.scenariomanager.api.events.ScenarioEvent
import me.calebbassham.scenariomanager.api.scenarioManager
import me.calebbassham.scenariomanager.api.skript.event.SkriptScenarioEventTriggerEvent
import org.bukkit.Bukkit
import org.bukkit.event.Event

class EffScheduleScenarioEvent : Effect() {

    private var scenario: Expression<Scenario>? = null
    private var name: Expression<String>? = null
    private var ticks: Expression<Timespan>? = null
    private var hide: Boolean? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(exprs: Array<out Expression<*>>, p1: Int, p2: Kleenean?, parseResult: SkriptParser.ParseResult): Boolean {
        name = exprs[0] as Expression<String>
        ticks = exprs[1] as Expression<Timespan>
        hide = parseResult.expr.endsWith("and hide")
        return true
    }

    override fun execute(e: Event?) {
        val name = name?.getSingle(e) ?: return
        val ticks = ticks?.getSingle(e)?.ticks_i ?: return
        val scenario = scenario?.getSingle(e) ?: return

        scenarioManager?.eventScheduler?.scheduleEvent(scenario, object : ScenarioEvent(name, hide == true) {
            override fun run() {
                Bukkit.getPluginManager().callEvent(SkriptScenarioEventTriggerEvent(name))
            }
        }, ticks)
    }

    override fun toString(e: Event?, debug: Boolean) = "Schedule scenario event: ${name?.toString(e, debug)} ${ticks?.toString(e, debug)} hide=$hide"

}