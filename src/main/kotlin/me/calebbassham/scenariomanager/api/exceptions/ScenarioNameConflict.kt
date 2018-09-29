package me.calebbassham.scenariomanager.api.exceptions

class ScenarioNameConflict(name: String): RuntimeException("Another scenario is already registered with the name \"$name\"")