package me.calebbassham.scenariomanager.api

import me.calebbassham.scenariomanager.ScenarioManagerUtils

interface ScenarioSetting<T> {
    val name: String
    val description: String

    var value: T

    fun displayValue(): String
}

open class SimpleScenarioSetting<T>(override val name: String, override val description: String, initialValue: T): ScenarioSetting<T> {
    override var value = initialValue

    override fun displayValue() = value.toString()
}

class TimeSpanScenarioSetting(name: String, description: String, initialValue: Long) : SimpleScenarioSetting<TimeSpan>(name, description, TimeSpan(initialValue)) {

    override fun displayValue() = ScenarioManagerUtils.formatTicks(value.ticks)

}