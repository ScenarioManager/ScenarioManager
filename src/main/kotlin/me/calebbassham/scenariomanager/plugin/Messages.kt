package me.calebbassham.scenariomanager.plugin

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

internal object Messages {

    private val HIGHLIGHT = "&2"
    private val MAIN = "&7"
    private val PREFIX = "&8[&aScenarios&8]$MAIN"

    val ENABLED_SCENARIOS = "$PREFIX ${HIGHLIGHT}Enabled: $MAIN%s"
    val DISABLED_SCENARIOS = "$PREFIX Disabled: $MAIN%s"
    val NOT_A_SCENARIO = "$PREFIX %s is not a scenario."
    val SCENARIO_ENABLED = "$PREFIX $HIGHLIGHT%s ${MAIN}has been &aenabled."
    val SCENARIO_DISABLED = "$PREFIX $HIGHLIGHT%s ${MAIN}has been &cdisabled."
    val DESCRIBE_SCENARIO = "$PREFIX $HIGHLIGHT%s: $MAIN%s &oCreated by %s"
    val TIMERS_UNSUPPORTED = "$PREFIX This command is unsupported by the current scenario event scheduler."
    val TIMER = "$PREFIX $HIGHLIGHT%s: &a%s"
    val NO_TIMERS = "$PREFIX There are no timers running."
    val SCENARIO_SETTING = "$MAIN%s &8= $MAIN%s"
    val SCENARIO_HAS_NO_SETTINGS = "$PREFIX %s has no settings."
    val LISTED_SCENARIO_SETTING = "$MAIN%s &o(%s) &8= $MAIN%s"
    val NO_ENABLED_SCENARIOS_HAVE_SETTING = "$PREFIX No enabled scenarios have settings."
    val NOT_A_SETTING = "$PREFIX %s is not a setting for %s."
    val COULD_NOT_PARSE_SETTING_VALUE = "$PREFIX Could not parse setting value."
    val COULD_NOT_GET_SCENARIOS = "$PREFIX Could not get scenarios."
    val NO_INSTALLED_SCENARIOS = "$PREFIX There are not scenarios installed."
    val SPECIFY_SETTING_VALUE = "$PREFIX You must specify a value for the setting."
    val SPECIFY_SCENARIO = "$PREFIX You must specify a scenario."
    val NO_SCENARIOS_ENABLED = "$PREFIX There are no scenarios enabled."
}

internal fun CommandSender.sendMessage(message: String, vararg replacements: Any) {
    this.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(message, *replacements)))
}