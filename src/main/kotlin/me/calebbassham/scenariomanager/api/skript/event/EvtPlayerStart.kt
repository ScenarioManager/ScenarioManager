package me.calebbassham.scenariomanager.api.skript.event

import ch.njol.skript.lang.Literal
import ch.njol.skript.lang.SkriptEvent
import ch.njol.skript.lang.SkriptParser
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class EvtPlayerStart : SkriptEvent() {

    override fun init(exprs: Array<out Literal<*>>, p1: Int, p2: SkriptParser.ParseResult?) = true

    override fun check(p0: Event?) = true

    override fun toString(p0: Event?, p1: Boolean) = "Player start"
}

internal class PlayerStartEvent(val player: Player): Event() {

    companion object {
        val handlers = HandlerList()

        @JvmStatic
        val handlerList
            get() = handlers
    }

    override fun getHandlers() = PlayerStartEvent.handlers
}