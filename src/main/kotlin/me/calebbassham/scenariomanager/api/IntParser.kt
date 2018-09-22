package me.calebbassham.scenariomanager.api

class IntParser : ScenarioSettingParser<Int> {
    override fun parse(input: String) = input.toIntOrNull() ?: throw ScenarioSettingParseException("Not an integer.")
}