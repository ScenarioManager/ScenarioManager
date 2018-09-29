package me.calebbassham.scenariomanager.api.uhc

import org.bukkit.Bukkit
import org.bukkit.World

class DefaultGameWorldProvider : GameWorldProvider {

    override val gameWorlds: List<World>
        get() = Bukkit.getWorlds()

}