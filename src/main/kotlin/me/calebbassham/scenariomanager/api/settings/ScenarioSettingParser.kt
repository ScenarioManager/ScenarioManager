package me.calebbassham.scenariomanager.api.settings

interface ScenarioSettingParser<out T> {

    /**
     * @throws ScenarioSettingParseException
     */
    fun parse(input: String): T

}