package me.calebbassham.scenariomanager.api.skript.type

import ch.njol.skript.classes.ClassInfo
import ch.njol.skript.classes.Parser
import ch.njol.skript.lang.ParseContext
import me.calebbassham.scenariomanager.api.Scenario
import me.calebbassham.scenariomanager.api.scenarioManager

class ScenarioClassInfo : ClassInfo<Scenario>(Scenario::class.java, "scenario") {

    init {
        user("scenario")
    }

    override fun getParser() = ScenarioParser()
}

class ScenarioParser : Parser<Scenario>() {

    override fun getVariableNamePattern() = "%scenario%"

    override fun toString(scen: Scenario, flag: Int) = scen.name

    override fun toVariableNameString(scen: Scenario) = scen.name

    override fun parse(s: String, context: ParseContext?) = scenarioManager.getScenario(s)

}