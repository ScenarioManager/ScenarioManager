package me.calebbassham.scenariomanager.api

class ScenarioNameConflict(name: String): RuntimeException("Another scenario is already registered with the name \"$name\"")

class ScenarioAlreadyRegistered(name: String): RuntimeException("The scenario \"$name\" has already been registered.")