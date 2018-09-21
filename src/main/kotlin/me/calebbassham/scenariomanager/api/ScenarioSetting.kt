package me.calebbassham.scenariomanager.api

interface ScenarioSetting<T> {
    val name: String
    val description: String

    var value: T

    fun displayValue(): String
}

class SimpleScenarioSetting<T>(override val name: String, override val description: String, initialValue: T): ScenarioSetting<T> {
    override var value = initialValue

    override fun displayValue() = value.toString()
}