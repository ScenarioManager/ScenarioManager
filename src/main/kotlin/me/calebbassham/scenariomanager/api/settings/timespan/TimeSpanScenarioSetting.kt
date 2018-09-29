package me.calebbassham.scenariomanager.api.settings.timespan

import me.calebbassham.scenariomanager.ScenarioManagerUtils
import me.calebbassham.scenariomanager.api.settings.SimpleScenarioSetting

class TimeSpanScenarioSetting(name: String, description: String, initialValue: Long) : SimpleScenarioSetting<TimeSpan>(name, description, TimeSpan(initialValue)) {

    override fun displayValue() = ScenarioManagerUtils.formatTicks(value.ticks)

}