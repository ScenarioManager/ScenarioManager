package me.calebbassham.scenariomanager.api.skript.effect

import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import me.calebbassham.scenariomanager.api.scenarioManager
import org.bukkit.entity.Player
import org.bukkit.event.Event

class EffTriggerGameStartEvent : Effect() {

    private var players: Expression<Player>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(exprs: Array<out Expression<*>>, p1: Int, p2: Kleenean?, p3: SkriptParser.ParseResult?): Boolean {
        players = exprs[0] as Expression<Player>
        return true
    }

    override fun execute(e: Event?) {
        scenarioManager?.onGameStart(players?.getAll(e) ?: emptyArray())
    }

    override fun toString(p0: Event?, p1: Boolean) = "Trigger Game Start Event"

}