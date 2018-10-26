package me.calebbassham.scenariomanager.api.settings.onlineplayer

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import java.util.*

class OnlinePlayer(val uniqueId: UUID) {

    constructor(player: Player): this(player.uniqueId)


    val offlinePlayer: OfflinePlayer get() = Bukkit.getOfflinePlayer(uniqueId)

    val player: Player? get() = Bukkit.getPlayer(uniqueId)

}