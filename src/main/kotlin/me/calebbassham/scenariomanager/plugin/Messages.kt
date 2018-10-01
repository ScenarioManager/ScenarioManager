package me.calebbassham.scenariomanager.plugin

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import java.text.MessageFormat

internal object Messages {

    private val HIGHLIGHT = "&2"
    private val MAIN = "&7"
    private val PREFIX = "&8[&aScenarios&8]$MAIN"
    private val SYMBOL = "&8"

    val ENABLED_SCENARIOS = "$PREFIX ${HIGHLIGHT}Enabled: $MAIN{0}"
    val DISABLED_SCENARIOS = "$PREFIX Disabled: $MAIN{0}"
    val NOT_A_SCENARIO = "$PREFIX {0} is not a scenario."
    val SCENARIO_ENABLED = "$PREFIX $HIGHLIGHT{0} ${MAIN}has been &aenabled."
    val SCENARIO_DISABLED = "$PREFIX $HIGHLIGHT{0} ${MAIN}has been &cdisabled."
    val DESCRIBE_SCENARIO = "$PREFIX $HIGHLIGHT{0}: $MAIN{1} &oCreated by {2}"
    val TIMER = "$PREFIX $HIGHLIGHT{0}: &a{1}"
    val NO_TIMERS = "$PREFIX There are no timers running."
    val SCENARIO_SETTING = "$MAIN{0} &8= $MAIN{1}"
    val SCENARIO_HAS_NO_SETTINGS = "$PREFIX {0} has no settings."
    val LISTED_SCENARIO_SETTING = "$MAIN{0} &o({1}) &8= $MAIN{2}"
    val NO_ENABLED_SCENARIOS_HAVE_SETTING = "$PREFIX No enabled scenarios have settings."
    val NOT_A_SETTING = "$PREFIX {0} is not a setting for {1}."
    val COULD_NOT_PARSE_SETTING_VALUE = "$PREFIX Could not parse setting value."
    val NO_INSTALLED_SCENARIOS = "$PREFIX There are not scenarios installed."
    val SPECIFY_SETTING_VALUE = "$PREFIX You must specify a value for the setting."
    val SPECIFY_SETTING = "$PREFIX You must specify a setting."
    val NO_SCENARIOS_ENABLED = "$PREFIX There are no scenarios enabled."

    val SCENARIO_MANAGER_HELP = listOf(
        "$PREFIX Scenario Manager Help",
        "$SYMBOL/{0} <${MAIN}list $SYMBOL| ${MAIN}timers $SYMBOL| ${MAIN}help$SYMBOL>",
        "$SYMBOL/{0} <${MAIN}enable $SYMBOL| ${MAIN}disable $SYMBOL| ${MAIN}describe$SYMBOL> <${MAIN}scenario$SYMBOL>",
        "$SYMBOL/{0} ${MAIN}settings $SYMBOL[<${MAIN}scenario$SYMBOL> [${MAIN}set $SYMBOL<${MAIN}setting$SYMBOL> <${MAIN}new_value>$SYMBOL]]"
    ).joinToString("\n")
}

internal fun CommandSender.sendMessage(message: String, vararg replacements: Any) {
    this.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(message, *replacements)))
}