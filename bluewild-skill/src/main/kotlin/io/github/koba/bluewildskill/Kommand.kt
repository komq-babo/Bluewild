package io.github.koba.bluewildskill

import io.github.monun.kommand.PluginKommand
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object Kommand {
    private lateinit var plugin: BluewildskillPlugin

    internal fun register(plugin: BluewildskillPlugin, kommand: PluginKommand) {
        Kommand.plugin = plugin

        kommand.register("christmasskill") {

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