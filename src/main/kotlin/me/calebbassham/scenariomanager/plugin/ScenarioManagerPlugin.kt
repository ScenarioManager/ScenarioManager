package me.calebbassham.scenariomanager.plugin

import ch.njol.skript.Skript
import ch.njol.skript.SkriptAddon
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.registrations.Classes
import ch.njol.skript.registrations.EventValues
import ch.njol.skript.util.Getter
import me.calebbassham.scenariomanager.api.Scenario
import me.calebbassham.scenariomanager.api.ScenarioManager
import me.calebbassham.scenariomanager.api.scenarioManager
import me.calebbassham.scenariomanager.api.skript.condition.CondScenario
import me.calebbassham.scenariomanager.api.skript.condition.CondScenarioRegistered
import me.calebbassham.scenariomanager.api.skript.effect.*
import me.calebbassham.scenariomanager.api.skript.event.*
import me.calebbassham.scenariomanager.api.skript.expression.ExprScenario
import me.calebbassham.scenariomanager.api.skript.expression.ExprScenarioDescription
import me.calebbassham.scenariomanager.api.skript.expression.ExprScenarioName
import me.calebbassham.scenariomanager.api.skript.type.ScenarioClassInfo
import me.calebbassham.scenariomanager.plugin.cmd.ScenarioManagerCmd
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

internal lateinit var log: Logger

class ScenarioManagerPlugin : JavaPlugin() {

    companion object {
        internal lateinit var instance: ScenarioManagerPlugin
    }

    internal var skriptAddon: SkriptAddon? = null

    override fun onEnable() {

        if (!::log.isInitialized) {
            log = this.logger
        }

        scenarioManager = ScenarioManager(this)

        instance = this

        if (Bukkit.getPluginManager().getPlugin("Skript") != null) {
            skript()
        }

        Bukkit.getPluginCommand("scenariomanager").apply {
            val cmd = ScenarioManagerCmd()
            executor = cmd
            tabCompleter = cmd
        }

    }

    private fun skript() {
        skriptAddon = Skript.registerAddon(this)

        Classes.registerClass(ScenarioClassInfo())

        Skript.registerCondition(CondScenario::class.java, "[the ][scenario ]%scenario% is enabled", "[the ][scenario ]%scenario% is disabled")
        Skript.registerCondition(CondScenarioRegistered::class.java, "[the ][scenario ]%string% is registered", "[the ][scenario ]%string% is not registered")

        Skript.registerEvent("game start", EvtGameStart::class.java, GameStartEvent::class.java, "[sm ]game start")
        Skript.registerEvent("game stop", EvtGameStop::class.java, GameStopEvent::class.java, "[sm ]game stop")

        Skript.registerEvent("player start", EvtPlayerStart::class.java, PlayerStartEvent::class.java, "player start")
        EventValues.registerEventValue(PlayerStartEvent::class.java, Player::class.java, object : Getter<Player, PlayerStartEvent>() {
            override fun get(e: PlayerStartEvent) = e.player
        }, 0)

        Skript.registerEvent("scenario event", EvtSkriptScenarioEvent::class.java, SkriptScenarioEventTriggerEvent::class.java, "scenario event %string%")

        Skript.registerEffect(EffScenarioEnable::class.java, "enable %scenarios%", "disable %scenarios%")
        Skript.registerEffect(EffRegisterScenario::class.java, "register scenario with name %string% and description %string%")
        Skript.registerEffect(EffTriggerGameStartEvent::class.java, "trigger game start event with %players%")
        Skript.registerEffect(EffTriggerGameStopEvent::class.java, "trigger game stop event")
        Skript.registerEffect(EffTriggerPlayerStartEvent::class.java, "trigger player start event with %player%")
        Skript.registerEffect(EffScheduleScenarioEvent::class.java, "schedule scenario event for %scenario% [with name ]%string% to run in %timespan%[ and hide]")

        Skript.registerExpression(ExprScenario::class.java, Scenario::class.java, ExpressionType.COMBINED, "[the] scenario [with name ]%string%")
        Skript.registerExpression(ExprScenarioDescription::class.java, String::class.java, ExpressionType.COMBINED, "[the] description of %scenario%", "%scenario%'s description")
        Skript.registerExpression(ExprScenarioName::class.java, String::class.java, ExpressionType.COMBINED, "[the] name of %scenario%", "%scenario%'s name")
    }

}