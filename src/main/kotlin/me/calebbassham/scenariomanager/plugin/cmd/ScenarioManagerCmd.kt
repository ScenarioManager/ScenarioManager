package me.calebbassham.scenariomanager.plugin.cmd

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import me.calebbassham.scenariomanager.api.Scenario
import me.calebbassham.scenariomanager.plugin.Messages
import me.calebbassham.scenariomanager.plugin.ScenarioManagerPlugin
import me.calebbassham.scenariomanager.plugin.sendMessage
import org.bukkit.command.CommandSender

@CommandAlias("scenariomanager|scen|sm")
class ScenarioManagerCmd : BaseCommand() {

    @Subcommand("list")
    @Default
    fun list(sender: CommandSender) {
        val enabled = ScenarioManagerPlugin.scenarioManager.enabledScenarios.joinToString(", ") { it.name }
        val disabled = ScenarioManagerPlugin.scenarioManager.registeredScenarios.filterNot { it.isEnabled }.joinToString(", ") { it.name }

        if (!enabled.isEmpty()) sender.sendMessage(Messages.ENABLED_SCENARIOS, enabled)
        if (!disabled.isEmpty()) sender.sendMessage(Messages.DISABLED_SCENARIOS, disabled)
    }

    @Subcommand("enable")
    @CommandCompletion("@disabledScenarios")
    fun enable(sender: CommandSender, scenario: Scenario) {
        scenario.isEnabled = true
        sender.sendMessage(Messages.SCENARIO_ENABLED, scenario.name)
    }

    @Subcommand("disable")
    @CommandCompletion("@enabledScenarios")
    fun disable(sender: CommandSender, scenario: Scenario) {
        scenario.isEnabled = false
        sender.sendMessage(Messages.SCENARIO_DISABLED, scenario.name)
    }

    @Subcommand("describe|info")
    @CommandCompletion("@scenarios")
    fun describe(sender: CommandSender, scenario: Scenario) {
        sender.sendMessage(Messages.DESCRIBE_SCENARIO, scenario.name, scenario.description)
    }

}