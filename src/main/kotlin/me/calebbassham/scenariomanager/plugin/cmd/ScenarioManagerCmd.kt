package me.calebbassham.scenariomanager.plugin.cmd

import co.aikar.commands.BaseCommand
import co.aikar.commands.InvalidCommandArgument
import co.aikar.commands.annotation.*
import me.calebbassham.scenariomanager.ScenarioManagerUtils
import me.calebbassham.scenariomanager.api.Scenario
import me.calebbassham.scenariomanager.api.ScenarioSettingParseException
import me.calebbassham.scenariomanager.api.scenarioManager
import me.calebbassham.scenariomanager.plugin.Messages
import me.calebbassham.scenariomanager.plugin.log
import me.calebbassham.scenariomanager.plugin.sendMessage
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import java.util.logging.Level

@CommandAlias("scenariomanager|scen|sm")
class ScenarioManagerCmd : BaseCommand() {

    @Subcommand("list")
    @Default
    fun list(sender: CommandSender) {
        val enabled = scenarioManager?.enabledScenarios?.joinToString(", ") { it.name } ?: return
        val disabled = scenarioManager?.registeredScenarios?.filterNot { it.isEnabled }?.joinToString(", ") { it.name }
                ?: return

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

    @Subcommand("timers")
    fun timers(sender: CommandSender) {
        val events = scenarioManager?.eventScheduler?.events
                ?: throw InvalidCommandArgument(Messages.TIMERS_UNSUPPORTED, false)

        if (events.isEmpty())
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.NO_TIMERS))
        else
            events.toList()
                    .filterNot { it.first.hide }
                    .sortedBy { it.second }
                    .forEach { (event, ticks) -> sender.sendMessage(Messages.TIMER, event.name, ScenarioManagerUtils.formatTicks(ticks)) }
    }

    @Subcommand("settings")
    inner class ScenarioManagerSettingCmd {

        @Subcommand("list")
        @CatchUnknown
        @Default
        fun list(sender: CommandSender) {
            val enabled = scenarioManager?.enabledScenarios
                ?.filter { it.settings != null }
                ?: throw InvalidCommandArgument("Could not get enabled scenarios.", false)

            if (enabled.isEmpty()) {
                sender.sendMessage(Messages.NO_ENABLED_SCENARIOS_HAVE_SETTING)
                return
            }

            for (i in 0 until enabled.size) {
                val scen = enabled[i]
                val settings = scen.settings!! // filtered above

                sender.sendMessage(scen.prefix)

                for (setting in settings) {
                    sender.sendMessage(Messages.SCENARIO_SETTING, setting.name, setting.displayValue())
                }

                if (i != enabled.lastIndex) {
                    sender.sendMessage("")
                }
            }
        }

        @Subcommand("set")
        fun set(sender: CommandSender, scenario: Scenario, scenarioSetting: String, strValue: String) {
            val setting = scenario.settings?.firstOrNull { it.name.equals(scenarioSetting.replace("_", " "), ignoreCase = true) }

            if (setting == null) {
                sender.sendMessage(Messages.NOT_A_SCENARIO)
                return
            }

            val value: Any

            try {
                value = scenarioManager?.scenarioSettingParsers?.get(setting::class.java)?.parse(strValue) ?: throw ScenarioSettingParseException("no parser for ${setting::class.java.name}")
            } catch (e: ScenarioSettingParseException) {
                sender.sendMessage(Messages.COULD_NOT_PARSE_SETTING_VALUE)
                log.log(Level.SEVERE, e.toString())
                return
            }

            setting.value = value
            sender.sendMessage(Messages.SCENARIO_SETTING, setting.name, setting.displayValue())
        }

    }

}