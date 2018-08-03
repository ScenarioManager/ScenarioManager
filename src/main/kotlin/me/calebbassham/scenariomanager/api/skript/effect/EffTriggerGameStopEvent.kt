package me.calebbassham.scenariomanager.api.skript.effect

import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.calebbassham.scenariomanager.api.skript.event.GameStopEvent
import me.calebbassham.scenariomanager.plugin.ScenarioManagerPlugin
import org.bukkit.Bukkit
import org.bukkit.event.Event

class EffTriggerGameStopEvent : Effect() {

    override fun init(p0: Array<out Expression<*>>?, p1: Int, p2: Kleenean?, p3: SkriptParser.ParseResult?) = true

    override fun execute(p0: Event?) {
        ScenarioManagerPlugin.scenarioManager?.onGameStop()
    }

    override fun toString(p0: Event?, p1: Boolean) = "Trigger Game Stop Event"

}