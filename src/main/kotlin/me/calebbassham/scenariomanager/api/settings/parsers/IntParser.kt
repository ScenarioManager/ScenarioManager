package me.calebbassham.scenariomanager.api.settings.parsers

import me.calebbassham.scenariomanager.api.exceptions.ScenarioSettingParseException
import me.calebbassham.scenariomanager.api.settings.ScenarioSettingParser

class IntParser : ScenarioSettingParser<Int> {
    override fun parse(input: String) = input.toIntOrNull() ?: throw ScenarioSettingParseException("Not an integer.")
}