package me.calebbassham.scenariomanager.api.skript.event

import ch.njol.skript.lang.Literal
import ch.njol.skript.lang.SkriptEvent
import ch.njol.skript.lang.SkriptParser
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class EvtGameStop : SkriptEvent() {
    override fun init(p0: Array<out Literal<*>>?, p1: Int, p2: SkriptParser.ParseResult?) = true

    override fun check(p0: Event?) = true

    override fun toString(p0: Event?, p1: Boolean) = "Game stop event"
}

internal class GameStopEvent: Event() {

    companion object {
        private val handlers = HandlerList()

        @JvmStatic
        val handlerList: HandlerList
            get() = handlers
    }

    override fun getHandlers() = GameStopEvent.handlers

}