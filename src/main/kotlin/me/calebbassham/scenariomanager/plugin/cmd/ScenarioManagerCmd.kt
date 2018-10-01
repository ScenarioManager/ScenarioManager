package me.calebbassham.scenariomanager.plugin.cmd

import me.calebbassham.scenariomanager.ScenarioManagerUtils
import me.calebbassham.scenariomanager.ScenarioManagerUtils.format
import me.calebbassham.scenariomanager.api.Scenario
import me.calebbassham.scenariomanager.api.exceptions.ScenarioSettingParseException
import me.calebbassham.scenariomanager.api.scenarioManager
import me.calebbassham.scenariomanager.api.settings.ScenarioSetting
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
        val success: Boolean = if (args.isEmpty()) {
            listEnabledScenarioDescriptions(sender)
        } else if (args.size == 1 && args[0].equals("list", ignoreCase = true)) {
            list(sender)
        } else if (args.size == 1 && args[0].equals("timers", ignoreCase = true)) {
            timers(sender)
        } else if (args.size >= 2 && args[0].equals("enable", ignoreCase = true)) {
            enable(sender, args.drop(1).joinToString(" "))
        } else if (args.size >= 2 && args[0].equals("disable", ignoreCase = true)) {
            disable(sender, args.drop(1).joinToString(" "))
        } else if (args.size >= 2 && args[0].equals("describe", ignoreCase = true)) {
            describe(sender, args.drop(1).joinToString(" "))
        } else if (args.size >= 1 && args[0].equals("settings", true)) {
            scenarioSettings(sender, args.drop(1))
        } else {
            false
        }

        if (!success) {
            helpMessage(sender, label)
        }

        return true
    }

    private fun helpMessage(sender: CommandSender, alias: String) {
        sender.sendMessage(Messages.SCENARIO_MANAGER_HELP, alias)
    }

    private fun listEnabledScenarioDescriptions(sender: CommandSender): Boolean {
        val scenarios = scenarioManager.scenarios.filter { it.isEnabled }

        if (scenarios.isEmpty()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.NO_SCENARIOS_ENABLED))
            return true
        }

        for (scenario in scenarios) {
            val authors = scenario.authors
            val authorsText = if (authors == null || authors.isEmpty()) "anonymous" else authors.toList().format()
            sender.sendMessage(Messages.DESCRIBE_SCENARIO, scenario.name, scenario.description, authorsText)
        }

        return true
    }

    private fun list(sender: CommandSender): Boolean {
        val enabled = scenarioManager.scenarios.filter { it.isEnabled }.map { it.name }.format()
        val disabled = scenarioManager.scenarios.filterNot { it.isEnabled }.map { it.name }.format()

        if (enabled.isEmpty() && disabled.isEmpty()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.NO_INSTALLED_SCENARIOS))
        } else {
            if (!enabled.isEmpty()) sender.sendMessage(Messages.ENABLED_SCENARIOS, enabled)
            if (!disabled.isEmpty()) sender.sendMessage(Messages.DISABLED_SCENARIOS, disabled)
        }

        return true
    }

    private fun enable(sender: CommandSender, scenarioName: String): Boolean {
        val scenario = scenarioManager.getScenario(scenarioName)

        if (scenario == null) {
            sender.sendMessage(Messages.NOT_A_SCENARIO, scenarioName)
            return true
        }

        scenario.isEnabled = true
        sender.sendMessage(Messages.SCENARIO_ENABLED, scenario.name)

        return true
    }

    private fun disable(sender: CommandSender, scenarioName: String): Boolean {
        val scenario = scenarioManager.getScenario(scenarioName)

        if (scenario == null) {
            sender.sendMessage(Messages.NOT_A_SCENARIO, scenarioName)
            return true
        }

        scenario.isEnabled = false
        sender.sendMessage(Messages.SCENARIO_DISABLED, scenario.name)

        return true
    }

    private fun describe(sender: CommandSender, scenarioName: String): Boolean {
        val scenario = scenarioManager.getScenario(scenarioName)

        if (scenario == null) {
            sender.sendMessage(Messages.NOT_A_SCENARIO, scenarioName)
            return true
        }

        val authors = scenario.authors
        val authorsText = if (authors == null || authors.isEmpty()) "anonymous" else authors.toList().format()

        sender.sendMessage(Messages.DESCRIBE_SCENARIO, scenario.name, scenario.description, authorsText)

        return true
    }

    private fun timers(sender: CommandSender): Boolean {
        val events = scenarioManager.eventScheduler.events

        if (events.isEmpty())
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.NO_TIMERS))
        else
            events.toList()
                .filterNot { it.first.hide }
                .sortedBy { it.second }
                .forEach { (event, ticks) -> sender.sendMessage(Messages.TIMER, event.name, ScenarioManagerUtils.formatTicks(ticks)) }

        return true
    }

    @Suppress("UNCHECKED_CAST")
    private fun scenarioSettings(sender: CommandSender, args: List<String>): Boolean {
        if (args.isEmpty()) {
            listScenarioSettings(sender)
            return true
        }

        val scenarioNameArgs = ArrayList<String>()

        for (i in 0 until args.size) {
            val arg: String = args[i]
            if (arg.equals("set", true)) break
            scenarioNameArgs.add(arg)
        }

        val scenarioName = scenarioNameArgs.joinToString(" ")
        val scenario = scenarioManager.getScenario(scenarioName)

        if (scenario == null) {
            sender.sendMessage(Messages.NOT_A_SCENARIO, scenarioName)
            return true
        }

        // Below this could be better :|
        var remainingArgs = args.drop(scenarioNameArgs.size)

        if (remainingArgs.isEmpty()) {
            listSettingsForScenario(sender, scenario)
            return true
        }

        if (remainingArgs.size == 3 && !remainingArgs.first().equals("set", true)) {
            return false
        } else {
            remainingArgs = remainingArgs.drop(1)
        }

        if (remainingArgs.isEmpty()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.SPECIFY_SETTING))
            return true
        }

        val settingName = remainingArgs[0]
        val setting = scenario.settings?.firstOrNull { it.name.equals(settingName, true) } as? ScenarioSetting<Any>?
        if (setting == null) {
            sender.sendMessage(Messages.NOT_A_SETTING, settingName, scenario.name)
            return true
        }

        remainingArgs = remainingArgs.drop(1)

        if (remainingArgs.isEmpty()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.SPECIFY_SETTING_VALUE))
            return true
        } else if (remainingArgs.size > 1) {
            return false
        }

        val settingValueStr = remainingArgs.first()
        val settingValue: Any

        try {
            settingValue = scenarioManager.scenarioSettingParsers[setting.value::class.java]?.parse(settingValueStr) ?: throw ScenarioSettingParseException("no parser for ${setting.value::class.java.name}")
        } catch (e: ScenarioSettingParseException) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.COULD_NOT_PARSE_SETTING_VALUE))
            log.log(Level.SEVERE, e.toString())
            return true
        }

        setting.value = settingValue
        sender.sendMessage(Messages.SCENARIO_SETTING, setting.name, setting.displayValue())
        return true
    }

    private fun listSettingsForScenario(sender: CommandSender, scenario: Scenario) {
        val settings = scenario.settings

        if (settings == null) {
            sender.sendMessage(Messages.SCENARIO_HAS_NO_SETTINGS, scenario.name)
            return
        }

        sender.sendMessage(scenarioManager.getPrefix(scenario))
        for (setting in settings) {
            sender.sendMessage(Messages.LISTED_SCENARIO_SETTING, setting.name, setting.description, setting.displayValue())
        }
    }

    private fun listScenarioSettings(sender: CommandSender) {
        val enabled = scenarioManager.scenarios
            .filter { it.isEnabled }
            .filter { it.settings != null }

        if (enabled.isEmpty()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.NO_ENABLED_SCENARIOS_HAVE_SETTING))
            return
        }

        for (i in 0 until enabled.size) {
            val scen = enabled[i]
            val settings = scen.settings!! // filtered above

            sender.sendMessage(scenarioManager.getPrefix(scen))

            for (setting in settings) {
                sender.sendMessage(Messages.LISTED_SCENARIO_SETTING, setting.name, setting.description, setting.displayValue())
            }

            if (i != enabled.lastIndex) {
                sender.sendMessage("")
            }
        }
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String> {
        if (args.size == 1) {
            return listOf("list", "enable", "disable", "timers", "settings", "describe", "help").minimizeTabCompletions(args.last())
        }

        if (args.size >= 2) {
            if (args[0].equals("settings", true)) {

                val scenarioNameArgs = ArrayList<String>()
                for (i in 1 until args.size - 1) {
                    val arg = args[i]
                    if (arg.equals("set", true)) break
                    scenarioNameArgs.add(arg)
                }

                val scenarioName = scenarioNameArgs.joinToString(" ")
                val scenario = scenarioManager.getScenario(scenarioName)

                if (scenario == null) {
                    return scenarioManager.scenarios.filter { it.isEnabled }.filter { it.settings != null }.tabCompleteScenarioName(args)
                } else {
                    if (args.size == scenarioNameArgs.size + 2) return listOf("set")
                    if (args.size == scenarioNameArgs.size + 3) return scenario.settings?.map { it.name } ?: emptyList()
                }
            }
        }

        if (args.size >= 2) {

            if (args[0].equals("enable", true)) {
                return scenarioManager.scenarios.filterNot { it.isEnabled }.tabCompleteScenarioName(args)
            }

            if (args[0].equals("disable", true)) {
                return scenarioManager.scenarios.tabCompleteScenarioName(args)
            }

            if (args[0].equals("describe", true)) {
                return scenarioManager.scenarios.tabCompleteScenarioName(args)
            }

        }

        return emptyList()
    }

    private fun List<String>.minimizeTabCompletions(arg: String): List<String> = this.filter { it.toLowerCase().startsWith(arg.toLowerCase()) }

    private fun Collection<Scenario>.tabCompleteScenarioName(args: Array<out String>, argsToDrop: Int = 1): List<String> = this
        .filter { it.name.startsWith(args.drop(argsToDrop)
        .joinToString(" ")) }
        .map { it.name.split(" ") }
        .map { it.slice(args.size - 1 - argsToDrop..it.lastIndex) }
        .map { it.joinToString(" ") }.minimizeTabCompletions(args.last())

}