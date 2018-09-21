package me.calebbassham.scenariomanager.api

interface ScenarioSettingParser<out T> {

    /**
     * @throws ScenarioSettingParseException
     */
    fun parse(input: String): T

}