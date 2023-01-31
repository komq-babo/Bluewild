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
        kommand {
            register("christmasskill") {

                requires { isPlayer && isOp }

                executes {

                    val item = ItemStack(Material.GOLDEN_SWORD)
                    val meta = item.itemMeta

                    meta.setDisplayName("§e§l태양만세~")
                    meta.lore = mutableListOf("산타할아버지가 사용하시던 마법의 검")
                    item.itemMeta = meta

                    player.inventory.addItem(item)

                }
            }
        }
    }
}