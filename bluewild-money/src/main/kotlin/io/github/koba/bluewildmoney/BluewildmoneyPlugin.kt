package io.github.koba.bluewildmoney

import io.github.monun.kommand.kommand
import io.github.monun.tap.fake.FakeEntityServer
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.DisplaySlot
import java.io.File


class BluewildmoneyPlugin : JavaPlugin() {

    companion object {
        lateinit var instance: BluewildmoneyPlugin
        lateinit var fakeServer: FakeEntityServer
    }

    init {
        instance = this
    }

    override fun onEnable() {
        val cfile = File(dataFolder, "config.yml")

        if (cfile.length() === 0L) {
            config.options().copyDefaults(true)
            saveConfig()
        }

        server.pluginManager.registerEvents(Events(), this)
        server.scheduler.scheduleSyncRepeatingTask(this, {
            for (player in Bukkit.getOnlinePlayers()) {
                createBoard(player)
            }
        }, 0L, 1L)

        fakeServer = FakeEntityServer.create(this)
        server.scheduler.runTaskTimer(this, fakeServer::update, 0L, 1L)

        setupCommands()
    }

    private fun createBoard(player: Player) {
        val money = config.getInt("playerdata.${player.name}.money")
        val level = config.getInt("playerdata.${player.name}.level")
        val manager = Bukkit.getScoreboardManager()
        val board = manager.newScoreboard
        val obj = board.registerNewObjective(
            "Bluewild-server",
            "dummy",
            ChatColor.translateAlternateColorCodes('&', "&3&l- &b&l파&3&l란&b&l야&3&l생 &b&l서&3&l버 &b&l-")
        )
        obj.displaySlot = DisplaySlot.SIDEBAR
        val score = obj.getScore("§8§l------- §7§l내정보 §8§l-------")
        score.score = 10
        val score2 = obj.getScore("")
        score2.score = 9
        val score3 = obj.getScore("§e§l닉네임 §7§l: §c§l[§f§l" + player.name + "§c§l]")
        score3.score = 8
        val score4 = obj.getScore("§a§l보유 금액 §7§l: §b§l[ §6§l$money§b§l ]")
        score4.score = 7
        val score5 = obj.getScore("§a§l현재 레벨 §7§l: §b§l[ §a§l$level§b§l ]")
        score5.score = 6
        player.scoreboard = board
    }

    private fun setupCommands() = kommand {
        Kommand.register(this@BluewildmoneyPlugin, this)
    }
}