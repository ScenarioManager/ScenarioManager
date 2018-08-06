package me.calebbassham.scenariomanager.api.skript.effect

import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.calebbassham.scenariomanager.plugin.ScenarioManagerPlugin
import org.bukkit.entity.Player
import org.bukkit.event.Event

class EffTriggerPlayerStartEvent : Effect() {

    private var player: Expression<Player>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(exprs: Array<out Expression<*>>, p1: Int, p2: Kleenean?, p3: SkriptParser.ParseResult?): Boolean {
        player = exprs[0] as Expression<Player>
        return true
    }

    override fun execute(e: Event) {
        val player = player?.getSingle(e) ?: return
        ScenarioManagerPlugin.scenarioManager?.onPlayerStart(player)
    }

    override fun toString(e: Event?, debug: Boolean) = "Trigger player start event: ${player?.toString(e, debug)}"

}