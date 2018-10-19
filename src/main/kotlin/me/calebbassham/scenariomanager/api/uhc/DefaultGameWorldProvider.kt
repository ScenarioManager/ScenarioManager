package me.calebbassham.scenariomanager.api.uhc

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World

open class DefaultGameWorldProvider : GameWorldProvider {

    override val gameWorlds: List<World>
        get() = Bukkit.getWorlds()

    override fun getMapRadius(world: World): Int? {
        if (!gameWorlds.contains(world)) return null

        val diameter = world.worldBorder.size.toInt()
        val radius = diameter / 2

        if (radius > 10000) return 1500

        return radius
    }

    override fun getMapCenter(world: World): Location? {
        if (!gameWorlds.contains(world)) return null

        return Location(world, 0.0, 0.0, 0.0)
    }
}