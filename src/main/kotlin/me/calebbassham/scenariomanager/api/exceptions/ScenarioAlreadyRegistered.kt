package me.calebbassham.scenariomanager.api.exceptions

class ScenarioAlreadyRegistered(name: String): RuntimeException("The scenario \"$name\" has already been registered.")