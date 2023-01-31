package io.github.koba.bluewildskill

import io.github.monun.kommand.kommand
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class BluewildskillPlugin: JavaPlugin() {

    companion object {
        lateinit var instance : BluewildskillPlugin
    }

    init {
        instance = this
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(Events(),this)
        setupCommands()
    }

    private fun setupCommands() = kommand {
        Kommand.register(this@BluewildskillPlugin, this)
    }
}