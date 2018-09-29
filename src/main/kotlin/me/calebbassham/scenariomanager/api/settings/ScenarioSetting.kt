package me.calebbassham.scenariomanager.api.settings

interface ScenarioSetting<T> {
    val name: String
    val description: String

    var value: T

    fun displayValue(): String
}