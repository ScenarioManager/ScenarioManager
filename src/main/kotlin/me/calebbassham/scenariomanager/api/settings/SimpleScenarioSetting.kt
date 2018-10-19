package me.calebbassham.scenariomanager.api.settings

open class SimpleScenarioSetting<T>(override val name: String, override val description: String, initialValue: T): ScenarioSetting<T> {
    override var value = initialValue

    override fun displayValue() = value.toString()
}
