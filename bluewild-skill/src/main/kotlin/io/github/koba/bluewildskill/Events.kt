package io.github.koba.bluewildskill

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Fireball
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class Events : Listener {

    private val cooldown = ArrayList<Player>()

    private fun getInstance(): Plugin {
        return BluewildskillPlugin.instance
    }


    @EventHandler
    fun onClick(event: PlayerInteractEvent) {
        val player = event.player
        if (player.itemInHand.type != Material.GOLDEN_SWORD) return
        if (player.itemInHand.itemMeta.displayName == "§e§l태양만세~") {
            if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
                if (!cooldown.contains(player)) {
                    player.addPotionEffect(PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 0, false, false))
                    player.sendTitle("§e§l태양만세", "", 10, 50, 10)
                    player.playSound(player.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.1f, 1.0f)
                    Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), {
                        player.addPotionEffect(
                            PotionEffect(
                                PotionEffectType.LEVITATION,
                                20 * 5,
                                255,
                                false,
                                false
                            )
                        )
                    }, 100L)

                    Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), {
                        val fireball: Fireball = player.launchProjectile(
                            Fireball::class.java
                        )
                        fireball.isVisualFire = false
                        fireball.yield = -1f
                        fireball.velocity = player.location.direction
                    }, 100L)

                    Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), {
                        val fireball: Fireball = player.launchProjectile(
                            Fireball::class.java
                        )
                        fireball.isVisualFire = false
                        fireball.yield = -1f
                        fireball.velocity = player.location.direction
                    }, 120L)

                    Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), {
                        val fireball: Fireball = player.launchProjectile(
                            Fireball::class.java
                        )
                        fireball.isVisualFire = false
                        fireball.yield = -1f
                        fireball.velocity = player.location.direction
                    }, 140L)

                    Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), {
                        player.addPotionEffect(PotionEffect(PotionEffectType.JUMP, 20 * 3, 255, false, false))
                    }, 200L)

                    Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), {
                        cooldown.remove(player)
                    }, 400)

                    cooldown.add(player)
                } else {
                    player.sendMessage("§c쿨타임을 기다려 주세요.")
                }
            }
        }
    }

    @EventHandler
    fun onPlace(event: BlockPlaceEvent) {
        val player = event.player
        if (cooldown.contains(player)) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        val player = event.player
        if (cooldown.contains(player)) {
            event.isCancelled = true
        }
    }
}