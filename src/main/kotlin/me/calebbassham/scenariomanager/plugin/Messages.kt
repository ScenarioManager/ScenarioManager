package me.calebbassham.scenariomanager.plugin

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

internal object Messages {

    private val HIGHLIGHT = "&2"
    private val MAIN = "&7"
    private val PREFIX = "&8[&aScenarios&8]$MAIN"

    val ENABLED_SCENARIOS = "$PREFIX ${HIGHLIGHT}Enabled: $MAIN%s"
    val DISABLED_SCENARIOS = "$PREFIX Disabled: $MAIN%s"
    const val NOT_A_SCENARIO = "%s is not a scenario."
    val SCENARIO_ENABLED = "$PREFIX $HIGHLIGHT%s ${MAIN}has been &aenabled."
    val SCENARIO_DISABLED = "$PREFIX $HIGHLIGHT%s ${MAIN}has been &cdisabled."
    val DESCRIBE_SCENARIO = "$PREFIX $HIGHLIGHT%s: $MAIN%s"
}

internal fun CommandSender.sendMessage(message: String, vararg replacements: Any) {
    this.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(message, *replacements)))
}