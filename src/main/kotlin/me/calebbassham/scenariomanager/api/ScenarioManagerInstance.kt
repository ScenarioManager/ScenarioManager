@file:JvmName("ScenarioManagerInstance")

package me.calebbassham.scenariomanager.api

// trick the compiler
internal var nullableScenarioManager: SimpleScenarioManager? = null

val scenarioManager: SimpleScenarioManager
    get() = nullableScenarioManager!!