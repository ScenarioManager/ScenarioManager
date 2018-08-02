package me.calebbassham.scenariomanager.api.skript.event

import ch.njol.skript.lang.Literal
import ch.njol.skript.lang.SkriptEvent
import ch.njol.skript.lang.SkriptParser
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class EvtGameStart : SkriptEvent() {

    override fun init(p0: Array<out Literal<*>>, p1: Int, p2: SkriptParser.ParseResult?): Boolean {
        return true
    }

    override fun check(e: Event) = true

    override fun toString(p0: Event?, p1: Boolean) = "Game start trigger"
}

internal class GameStartEvent : Event() {

    companion object {
        private val handlers = HandlerList()

        @JvmStatic
        val handlerList: HandlerList
            get() = handlers
    }

    override fun getHandlers(): HandlerList = GameStartEvent.handlers

}