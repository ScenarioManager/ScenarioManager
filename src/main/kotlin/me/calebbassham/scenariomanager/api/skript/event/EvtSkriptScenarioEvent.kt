package me.calebbassham.scenariomanager.api.skript.event

import ch.njol.skript.lang.Literal
import ch.njol.skript.lang.SkriptEvent
import ch.njol.skript.lang.SkriptParser
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class EvtSkriptScenarioEvent : SkriptEvent() {
    private var name: Literal<String>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(args: Array<out Literal<*>>, p1: Int, p2: SkriptParser.ParseResult?): Boolean {
        name = args[0] as Literal<String>
        return true
    }

    override fun check(e: Event): Boolean {
        if (e !is SkriptScenarioEventTriggerEvent) return false

        val name = name?.getSingle(e)
        if (name?.equals(e.name) == false) return false

        return true
    }

    override fun toString(p0: Event?, p1: Boolean) = "Skript scenario event"
}

internal class SkriptScenarioEventTriggerEvent(val name: String) : Event() {

    companion object {
        private val handlers = HandlerList()

        @JvmStatic
        private val handlerList: HandlerList
            get() = handlers
    }

    override fun getHandlers() = handlerList
}