package me.calebbassham.scenariomanager.plugin.cmd

import me.calebbassham.scenariomanager.ScenarioManagerUtils
import me.calebbassham.scenariomanager.ScenarioManagerUtils.format
import me.calebbassham.scenariomanager.api.ScenarioSetting
import me.calebbassham.scenariomanager.api.ScenarioSettingParseException
import me.calebbassham.scenariomanager.api.scenarioManager
import me.calebbassham.scenariomanager.plugin.Messages
import me.calebbassham.scenariomanager.plugin.log
import me.calebbassham.scenariomanager.plugin.sendMessage
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.util.logging.Level

class ScenarioManagerCmd : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size == 1 && args[0].equals("list", ignoreCase = true)) {
            list(sender)
        }

        if (args.size == 1 && args[0].equals("timers", ignoreCase = true)) {
            timers(sender)
        }

        if (args.size == 2 && args[0].equals("enable", ignoreCase = true)) {
            enable(sender, args[1])
        }

        if (args.size == 2 && args[0].equals("disable", ignoreCase = true)) {
            disable(sender, args[1])
        }

        if (args.size == 2 && args[0].equals("describe", ignoreCase = true)) {
            describe(sender, args[1])
        }

        if (args.size == 2 && args[0].equals("settings", ignoreCase = true) && args[1].equals("list", true)) {
            listScenarioSettings(sender)
        }

        if (args.size == 5 && args[0].equals("settings", ignoreCase = true) && args[1].equals("set", true)) {
            setScenarioSetting(sender, args[2], args[3], args[4])
        } else if (args.size == 4 && args[0].equals("settings", ignoreCase = true) && args[1].equals("set", true)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.SPECIFY_SETTING_VALUE))
        } else if (args.size == 3 && args[0].equals("settings", ignoreCase = true) && args[1].equals("set", true)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.SPECIFY_SCENARIO))
        }

        return true
    }

    private fun list(sender: CommandSender) {
        val enabled = scenarioManager?.enabledScenarios?.joinToString(", ") { it.name } ?: return
        val disabled = scenarioManager?.registeredScenarios?.filterNot { it.isEnabled }?.joinToString(", ") { it.name }
            ?: return

        if (enabled.isEmpty() && disabled.isEmpty()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.NO_INSTALLED_SCENARIOS))
        } else {
            if (!enabled.isEmpty()) sender.sendMessage(Messages.ENABLED_SCENARIOS, enabled)
            if (!disabled.isEmpty()) sender.sendMessage(Messages.DISABLED_SCENARIOS, disabled)
        }
    }

    private fun enable(sender: CommandSender, scenarioName: String) {
        val scenario = scenarioManager?.getScenario(scenarioName)

        if (scenario == null) {
            sender.sendMessage(Messages.NOT_A_SCENARIO, scenarioName)
            return
        }

        scenario.isEnabled = true
        sender.sendMessage(Messages.SCENARIO_ENABLED, scenario.name)
    }

    private fun disable(sender: CommandSender, scenarioName: String) {
        val scenario = scenarioManager?.getScenario(scenarioName)

        if (scenario == null) {
            sender.sendMessage(Messages.NOT_A_SCENARIO, scenarioName)
            return
        }

        scenario.isEnabled = false
        sender.sendMessage(Messages.SCENARIO_DISABLED, scenario.name)
    }

    private fun describe(sender: CommandSender, scenarioName: String) {
        val scenario = scenarioManager?.getScenario(scenarioName)

        if (scenario == null) {
            sender.sendMessage(Messages.NOT_A_SCENARIO, scenarioName)
            return
        }

        sender.sendMessage(Messages.DESCRIBE_SCENARIO, scenario.name, scenario.description, scenario.authors.format())
    }

    private fun timers(sender: CommandSender) {
        val events = scenarioManager?.eventScheduler?.events

        if (events == null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.TIMERS_UNSUPPORTED))
            return
        }

        if (events.isEmpty())
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.NO_TIMERS))
        else
            events.toList()
                .filterNot { it.first.hide }
                .sortedBy { it.second }
                .forEach { (event, ticks) -> sender.sendMessage(Messages.TIMER, event.name, ScenarioManagerUtils.formatTicks(ticks)) }
    }

    private fun listScenarioSettings(sender: CommandSender) {
        val enabled = scenarioManager?.enabledScenarios
            ?.filter { it.settings != null }

        if (enabled == null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.COULD_NOT_GET_SCENARIOS))
            return
        }

        if (enabled.isEmpty()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.NO_ENABLED_SCENARIOS_HAVE_SETTING))
            return
        }

        for (i in 0 until enabled.size) {
            val scen = enabled[i]
            val settings = scen.settings!! // filtered above

            sender.sendMessage(scen.prefix)

            for (setting in settings) {
                sender.sendMessage(Messages.LISTED_SCENARIO_SETTING, setting.name, setting.description, setting.displayValue())
            }

            if (i != enabled.lastIndex) {
                sender.sendMessage("")
            }
        }
    }

    private fun setScenarioSetting(sender: CommandSender, scenarioName: String, settingName: String, strValue: String) {
        val scenario = scenarioManager?.getScenario(scenarioName)

        if (scenario == null) {
            sender.sendMessage(Messages.NOT_A_SCENARIO, scenarioName)
            return
        }

        val setting = scenario.settings?.firstOrNull { it.name.equals(settingName.replace("_", " "), ignoreCase = true) } as? ScenarioSetting<Any>

        if (setting == null) {
            sender.sendMessage(Messages.NOT_A_SCENARIO)
            return
        }

        val value: Any

        try {
            value = scenarioManager?.scenarioSettingParsers?.get(setting.value::class.java)?.parse(strValue) ?: throw ScenarioSettingParseException("no parser for ${setting.value::class.java.name}")
        } catch (e: ScenarioSettingParseException) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.COULD_NOT_PARSE_SETTING_VALUE))
            log.log(Level.SEVERE, e.toString())
            return
        }

        setting.value = value
        sender.sendMessage(Messages.SCENARIO_SETTING, setting.name, setting.displayValue())
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String> {
        if (args.size == 1) {
            return listOf("list", "enable", "disable", "timers", "settings", "describe").minimizeTabCompletions(args.last())
        }

        if (args.size == 2) {
            if (args[0].equals("enable", true)) {
                return scenarioManager?.registeredScenarios?.filterNot { it.isEnabled }?.map { it.name }?.minimizeTabCompletions(args.last())
                    ?: emptyList()
            }

            if (args[0].equals("disable", true)) {
                return scenarioManager?.enabledScenarios?.map { it.name }?.minimizeTabCompletions(args.last())
                    ?: emptyList()
            }

            if (args[0].equals("describe", true)) {
                return scenarioManager?.registeredScenarios?.map { it.name }?.minimizeTabCompletions(args.last())
                    ?: emptyList()
            }

            if (args[0].equals("settings", true)) {
                return listOf("list", "set").minimizeTabCompletions(args.last())
            }
        }

        if (args.size == 3) {
            if (args[0].equals("settings", true)) {
                if (args[1].equals("set", true)) {
                    return scenarioManager?.enabledScenarios?.filter {
                        it.settings?.isNotEmpty() ?: false
                    }?.map { it.name }
                        ?.minimizeTabCompletions(args.last())
                        ?: emptyList()
                }
            }
        }

        if (args.size == 4) {
            if (args[0].equals("settings", true)) {
                if (args[1].equals("set", true)) {
                    return scenarioManager?.getScenario(args[2])
                        ?.let {
                            it.settings
                                ?.map { it.name }
                                ?.map { it.replace(" ", "_") }
                        }?.minimizeTabCompletions(args.last())
                        ?: emptyList()
                }
            }
        }

        return emptyList()
    }

    private fun List<String>.minimizeTabCompletions(arg: String): List<String> = this.filter { it.toLowerCase().startsWith(arg.toLowerCase()) }

}