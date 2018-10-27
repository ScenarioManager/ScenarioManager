@file:JvmName("ScenarioManagerUtils")
package me.calebbassham.scenariomanager

import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import java.util.*



object ScenarioManagerUtils {

    /**
     * Format ticks to a human readable form.
     * Examples: 600 ticks = 30s    1200 ticks = 1m 1220    ticks = 1m1s
     */
    @JvmStatic
    fun formatTicks(ticks: Long): String {
        var t = ticks

        val ticksPerSecond = 20
        val ticksPerMinute = ticksPerSecond * 60
        val ticksPerHour = ticksPerMinute * 60

        val hours = t / ticksPerHour
        t %= ticksPerHour

        val minutes = t / ticksPerMinute
        t %= ticksPerMinute

        val seconds = t / ticksPerSecond

        val sb = StringBuilder()

        if (hours > 0) {
            sb.append(hours.toString() + "h")
        }

        if (minutes > 0) {
            sb.append(minutes.toString() + "m")
        }

        if (seconds > 0 || sb.isEmpty()) {
            sb.append(seconds.toString() + "s")
        }

        return sb.toString()
    }

    /**
     * A util method to drop items at a location without them flying.
     */
    @JvmStatic
    fun dropItemstack(itemStack: ItemStack, location: Location) {

        val dropAt = Location(location.world, location.blockX + 0.5, location.blockY + 0.5, location.blockZ + 0.5)

        location.world.dropItem(dropAt, itemStack).apply {
            velocity = Vector(0, 0, 0)
        }
    }

    internal fun humanizePascalCase(str: String): String {
        if (str.isEmpty()) {
            return ""
        }

        val c = str.toCharArray()
        val list = ArrayList<String>()
        var tokenStart = 0
        var currentType = Character.getType(c[tokenStart])
        for (pos in tokenStart + 1 until c.size) {
            val type = Character.getType(c[pos])
            if (type == currentType) {
                continue
            }
            if (type == Character.LOWERCASE_LETTER.toInt()
                && currentType == Character.UPPERCASE_LETTER.toInt()) {
                val newTokenStart = pos - 1
                if (newTokenStart != tokenStart) {
                    list.add(String(c, tokenStart, newTokenStart - tokenStart))
                    tokenStart = newTokenStart
                }
            } else {
                list.add(String(c, tokenStart, pos - tokenStart))
                tokenStart = pos
            }
            currentType = type
        }
        list.add(String(c, tokenStart, c.size - tokenStart))

        return list.joinToString(" ")
    }

    internal fun <E> formatList(collection: Collection<E>): String {
        val list = collection.toList()

        val sb = StringBuilder()

        for (i in 0 until list.size) {
            sb.append(list[i])

            if (i == list.size - 1) {
                continue
            } else if (i == list.size - 2) {
                sb.append(" & ")
            } else {
                sb.append(", ")
            }
        }

        return sb.toString()
    }

}