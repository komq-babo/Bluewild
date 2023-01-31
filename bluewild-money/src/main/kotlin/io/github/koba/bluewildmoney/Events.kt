package io.github.koba.bluewildmoney

import io.github.monun.invfx.InvFX.frame
import io.github.monun.invfx.openFrame
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.entity.WitherSkeleton
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

class Events : Listener {

    private fun getInstance(): Plugin {
        return BluewildmoneyPlugin.instance
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player

        if (player.name !in getInstance().config.getStringList(player.name)) {
            getInstance().config.set("playerdata.${player.name}.money", 0)
            getInstance().config.set("playerdata.${player.name}.level", 1)
            getInstance().saveConfig()
        }
    }

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val player = event.player

        player.sendMessage("§c§l사망하여 §e§l[10원]§c§l을 잃었습니다")
        getInstance().config.set(
            "playerdata.${player.name}.money",
            getInstance().config.getInt("playerdata.${player.name}.money") - 10
        )
        getInstance().saveConfig()
    }

    @EventHandler
    fun onDamageWs(event: EntityDamageByEntityEvent) {
        if (event.damager !is Player) return
        val player = event.damager as Player
        if (event.entity is WitherSkeleton && event.damager is Player) {
            player.sendMessage("§7§l위더 스켈레톤을 때려 §e§l[4원]§7§l을 획득하였습니다")

            getInstance().config.set(
                "playerdata.${player.name}.money",
                getInstance().config.getInt("playerdata.${player.name}.money") + 4
            )
            getInstance().saveConfig()
        }
    }

    @EventHandler
    fun onKillPlayer(event: EntityDeathEvent) {
        if (event.entity.killer !is Player) return
        val player: Player = event.entity.killer!!
        if (event.entity is Player) {
            player.sendMessage("§c§l플레이어를 죽여 §e§l[10원]§c§l을 획득하였습니다")

            getInstance().config.set(
                "playerdata.${player.name}.money",
                getInstance().config.getInt("playerdata.${player.name}.money") + 10
            )
            getInstance().saveConfig()
        }

        if (event.entity.name.equals("§4§lBoss", true)) {
            player.sendMessage("§4§lBoss§6§l를 죽여 §e§l[1000원]§6§l을 획득하였습니다")

            getInstance().config.set(
                "playerdata.${player.name}.money",
                getInstance().config.getInt("playerdata.${player.name}.money") + 1000
            )
            getInstance().saveConfig()
        }
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        BluewildmoneyPlugin.fakeServer.addPlayer(event.player)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        BluewildmoneyPlugin.fakeServer.removePlayer(event.player)
    }

    @EventHandler
    fun onEntityClick(event: PlayerInteractAtEntityEvent) {

        if (event.rightClicked.name == "§e§lspecial shop") {
            specialshop(event.player)
        }

        if (event.rightClicked.name == "§6§l정육점") {
            butchershop(event.player)
        }

        if (event.rightClicked.name == "§9§l엔§1§l더§9§l드§1§l래§9§l곤") {
            dragonshop(event.player)
        }

        if (event.rightClicked.name == "§c§l지옥 상점") {
            nethershop(event.player)
        }

        if (event.rightClicked.name == "§7§l광부") {
            minershop(event.player)
        }
    }


    private fun specialshop(player: Player) {
        val invFrame = frame(3, Component.text("§e§lspecial shop")) {
            onOpen { openEvent ->
                openEvent.player.sendMessage("§a§l반가워! 스페셜상점에 온걸 환영해!")
            }

            for (x: Int in 0..8 step 1) {
                slot(x, 0) {
                    item = ItemStack(Material.YELLOW_STAINED_GLASS_PANE)
                    onClick { clickEvent ->
                        clickEvent.isCancelled = true
                    }
                }
            }

            slot(0, 1) {
                item = ItemStack(Material.YELLOW_STAINED_GLASS_PANE)
                onClick { clickEvent ->
                    clickEvent.isCancelled = true
                }
            }

            slot(1, 1) {
                item = ItemStack(Material.YELLOW_STAINED_GLASS_PANE)
                onClick { clickEvent ->
                    clickEvent.isCancelled = true
                }
            }

            slot(3, 1) {
                item = ItemStack(Material.YELLOW_STAINED_GLASS_PANE)
                onClick { clickEvent ->
                    clickEvent.isCancelled = true
                }
            }

            slot(5, 1) {
                item = ItemStack(Material.YELLOW_STAINED_GLASS_PANE)
                onClick { clickEvent ->
                    clickEvent.isCancelled = true
                }
            }

            slot(7, 1) {
                item = ItemStack(Material.YELLOW_STAINED_GLASS_PANE)
                onClick { clickEvent ->
                    clickEvent.isCancelled = true
                }
            }

            slot(8, 1) {
                item = ItemStack(Material.YELLOW_STAINED_GLASS_PANE)
                onClick { clickEvent ->
                    clickEvent.isCancelled = true
                }
            }

            for (x: Int in 0..8 step 1) {
                slot(x, 2) {
                    item = ItemStack(Material.YELLOW_STAINED_GLASS_PANE)
                    onClick { clickEvent ->
                        clickEvent.isCancelled = true
                    }
                }
            }

            slot(2, 1) {
                val advanced = ItemStack(Material.GOLD_BLOCK)
                val meta = advanced.itemMeta

                meta.setDisplayName("§6§l[§e§l고§6§l급§e§l] 뽑기상자")
                meta.lore = mutableListOf("§7§l10000코인으로 뽑기")

                advanced.itemMeta = meta

                item = advanced
                onClick { clickEvent ->
                    clickEvent.isCancelled = true
                }
            }

            slot(4, 1) {
                val resource = ItemStack(Material.CHEST)
                val meta = resource.itemMeta

                meta.setDisplayName("§7§l[자원] §e§l뽑기상자")
                meta.lore = mutableListOf("§7§l2500코인으로 뽑기")

                resource.itemMeta = meta

                item = resource
                onClick { clickEvent ->
                    clickEvent.isCancelled = true
                }
            }

            slot(6, 1) {
                val legend = ItemStack(Material.NETHERITE_BLOCK)
                val meta = legend.itemMeta

                meta.setDisplayName("§c§l[§e§l전§a§l설§9§l] §6§l뽑기상자")
                meta.lore = mutableListOf("§7§l50000코인으로 뽑기")

                legend.itemMeta = meta

                item = legend
                onClick { clickEvent ->
                    clickEvent.isCancelled = true
                }
            }
        }

        player.openFrame(invFrame)
    }

    private fun butchershop(player: Player) {
        val invFrame = frame(3, Component.text("§6§l정육점")) {
            onOpen { openEvent ->
                openEvent.player.sendMessage("§6§l반갑네! 오늘 새로운 고기가 들어왔다고!")
            }

            for (x: Int in 0..8 step 1) {
                slot(x, 0) {
                    item = ItemStack(Material.RED_STAINED_GLASS_PANE)
                    onClick { clickEvent ->
                        clickEvent.isCancelled = true
                    }
                }
            }

            for (x: Int in 0..8 step 2) {
                slot(x, 1) {
                    item = ItemStack(Material.RED_STAINED_GLASS_PANE)
                    onClick { clickEvent ->
                        clickEvent.isCancelled = true
                    }
                }
            }

            for (x: Int in 0..8 step 1) {
                slot(x, 2) {
                    item = ItemStack(Material.RED_STAINED_GLASS_PANE)
                    onClick { clickEvent ->
                        clickEvent.isCancelled = true
                    }
                }
            }

            slot(1, 1) {
                val pork = ItemStack(Material.COOKED_PORKCHOP)
                val meta = pork.itemMeta

                meta.lore = mutableListOf("§a§l1000원으로 구매")
                pork.amount = 32

                pork.itemMeta = meta

                item = pork

                onClick { clickEvent ->
                    clickEvent.isCancelled = true

                    if (getInstance().config.getInt("playerdata.${player.name}.money") >= 1000) {
                        getInstance().config.set(
                            "playerdata.${player.name}.money",
                            getInstance().config.getInt("playerdata.${player.name}.money") - 1000
                        )

                        val pork = ItemStack(Material.COOKED_PORKCHOP)
                        pork.amount = 32

                        player.inventory.addItem(pork)

                        player.sendMessage("§a구매 완료")
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                    } else {
                        player.sendMessage("§c잔액(코인)이 부족합니다")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
                    }
                }
            }

            slot(3, 1) {
                val beef = ItemStack(Material.COOKED_BEEF)
                val meta = beef.itemMeta

                meta.lore = mutableListOf("§a§l1000원으로 구매")
                beef.amount = 32

                beef.itemMeta = meta

                item = beef

                onClick { clickEvent ->
                    clickEvent.isCancelled = true

                    if (getInstance().config.getInt("playerdata.${player.name}.money") >= 1000) {
                        getInstance().config.set(
                            "playerdata.${player.name}.money",
                            getInstance().config.getInt("playerdata.${player.name}.money") - 1000
                        )

                        val beef = ItemStack(Material.COOKED_BEEF)
                        beef.amount = 32

                        player.inventory.addItem(beef)

                        player.sendMessage("§a구매 완료")
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                    } else {
                        player.sendMessage("§c잔액(코인)이 부족합니다")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
                    }
                }
            }

            slot(5, 1) {
                val chicken = ItemStack(Material.COOKED_CHICKEN)
                val meta = chicken.itemMeta

                meta.lore = mutableListOf("§a§l1000원으로 구매")
                chicken.amount = 32

                chicken.itemMeta = meta

                item = chicken

                onClick { clickEvent ->
                    clickEvent.isCancelled = true

                    if (getInstance().config.getInt("playerdata.${player.name}.money") >= 1000) {
                        getInstance().config.set(
                            "playerdata.${player.name}.money",
                            getInstance().config.getInt("playerdata.${player.name}.money") - 1000
                        )

                        val chicken = ItemStack(Material.COOKED_CHICKEN)
                        chicken.amount = 32

                        player.inventory.addItem(chicken)

                        player.sendMessage("§a구매 완료")
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                    } else {
                        player.sendMessage("§c잔액(코인)이 부족합니다")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
                    }
                }
            }

            slot(7, 1) {
                val rabbit = ItemStack(Material.COOKED_RABBIT)
                val meta = rabbit.itemMeta

                meta.lore = mutableListOf("§a§l1000원으로 구매")
                rabbit.amount = 32

                rabbit.itemMeta = meta

                item = rabbit

                onClick { clickEvent ->
                    clickEvent.isCancelled = true

                    if (getInstance().config.getInt("playerdata.${player.name}.money") >= 1000) {
                        getInstance().config.set(
                            "playerdata.${player.name}.money",
                            getInstance().config.getInt("playerdata.${player.name}.money") - 1000
                        )

                        val rabbit = ItemStack(Material.COOKED_RABBIT)
                        rabbit.amount = 32

                        player.inventory.addItem(rabbit)

                        player.sendMessage("§a구매 완료")
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                    } else {
                        player.sendMessage("§c잔액(코인)이 부족합니다")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
                    }
                }
            }
        }
        player.openFrame(invFrame)
    }

    private fun dragonshop(player: Player) {
        val invFrame = frame(3, Component.text("§9§l드§1§l래§9§l곤§1§l의 §9§l상§1§l점")) {
            onOpen { openEvent ->
                openEvent.player.sendMessage("§9§l그§1§l르§9§l르§1§l.§9§l.")
            }

            for (x: Int in 0..8 step 1) {
                slot(x, 0) {
                    item = ItemStack(Material.BLUE_STAINED_GLASS_PANE)
                    onClick { clickEvent ->
                        clickEvent.isCancelled = true
                    }
                }
            }

            for (x: Int in 1..7 step 2) {
                slot(x, 1) {
                    item = ItemStack(Material.BLUE_STAINED_GLASS_PANE)
                    onClick { clickEvent ->
                        clickEvent.isCancelled = true
                    }
                }
            }

            for (x: Int in 0..8 step 1) {
                slot(x, 2) {
                    item = ItemStack(Material.BLUE_STAINED_GLASS_PANE)
                    onClick { clickEvent ->
                        clickEvent.isCancelled = true
                    }
                }
            }

            slot(0, 1) {
                val phantom = ItemStack(Material.PHANTOM_MEMBRANE)
                val meta = phantom.itemMeta

                meta.lore = mutableListOf("§a§l750원으로 구매")

                phantom.itemMeta = meta

                item = phantom

                onClick { clickEvent ->
                    clickEvent.isCancelled = true

                    if (getInstance().config.getInt("playerdata.${player.name}.money") >= 750) {
                        getInstance().config.set(
                            "playerdata.${player.name}.money",
                            getInstance().config.getInt("playerdata.${player.name}.money") - 750
                        )

                        val pork = ItemStack(Material.PHANTOM_MEMBRANE)

                        player.inventory.addItem(pork)

                        player.sendMessage("§a구매 완료")
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                    } else {
                        player.sendMessage("§c잔액(코인)이 부족합니다")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
                    }
                }
            }

            slot(2, 1) {
                val endcrystal = ItemStack(Material.END_CRYSTAL)
                val meta = endcrystal.itemMeta

                meta.lore = mutableListOf("§a§l750000원으로 구매")

                endcrystal.itemMeta = meta

                item = endcrystal

                onClick { clickEvent ->
                    clickEvent.isCancelled = true

                    if (getInstance().config.getInt("playerdata.${player.name}.money") >= 750000) {
                        getInstance().config.set(
                            "playerdata.${player.name}.money",
                            getInstance().config.getInt("playerdata.${player.name}.money") - 750000
                        )

                        val endcrystal = ItemStack(Material.END_CRYSTAL)

                        player.inventory.addItem(endcrystal)

                        player.sendMessage("§a구매 완료")
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                    } else {
                        player.sendMessage("§c잔액(코인)이 부족합니다")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
                    }
                }
            }

            slot(4, 1) {
                val elytra = ItemStack(Material.ELYTRA)
                val meta = elytra.itemMeta

                meta.lore = mutableListOf("§a§l400000원으로 구매")

                elytra.itemMeta = meta

                item = elytra

                onClick { clickEvent ->
                    clickEvent.isCancelled = true

                    if (getInstance().config.getInt("playerdata.${player.name}.money") >= 400000) {
                        getInstance().config.set(
                            "playerdata.${player.name}.money",
                            getInstance().config.getInt("playerdata.${player.name}.money") - 400000
                        )

                        val elytra = ItemStack(Material.ELYTRA)

                        player.inventory.addItem(elytra)

                        player.sendMessage("§a구매 완료")
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                    } else {
                        player.sendMessage("§c잔액(코인)이 부족합니다")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
                    }
                }
            }

            slot(6, 1) {
                val dragonbreath = ItemStack(Material.DRAGON_BREATH)
                val meta = dragonbreath.itemMeta

                meta.lore = mutableListOf("§a§l500원으로 구매")
                dragonbreath.amount = 8

                dragonbreath.itemMeta = meta

                item = dragonbreath

                onClick { clickEvent ->
                    clickEvent.isCancelled = true

                    if (getInstance().config.getInt("playerdata.${player.name}.money") >= 500) {
                        getInstance().config.set(
                            "playerdata.${player.name}.money",
                            getInstance().config.getInt("playerdata.${player.name}.money") - 500
                        )

                        val dragonbreath = ItemStack(Material.DRAGON_BREATH)
                        dragonbreath.amount = 8

                        player.inventory.addItem(dragonbreath)

                        player.sendMessage("§a구매 완료")
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                    } else {
                        player.sendMessage("§c잔액(코인)이 부족합니다")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
                    }
                }
            }

            slot(8, 1) {
                val dragonhead = ItemStack(Material.DRAGON_HEAD)
                val meta = dragonhead.itemMeta

                meta.lore = mutableListOf("§a§l5000원으로 구매")

                dragonhead.itemMeta = meta

                item = dragonhead

                onClick { clickEvent ->
                    clickEvent.isCancelled = true

                    if (getInstance().config.getInt("playerdata.${player.name}.money") >= 5000) {
                        getInstance().config.set(
                            "playerdata.${player.name}.money",
                            getInstance().config.getInt("playerdata.${player.name}.money") - 5000
                        )

                        val dragonhead = ItemStack(Material.DRAGON_HEAD)

                        player.inventory.addItem(dragonhead)

                        player.sendMessage("§a구매 완료")
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                    } else {
                        player.sendMessage("§c잔액(코인)이 부족합니다")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
                    }
                }
            }
        }
        player.openFrame(invFrame)
    }

    private fun nethershop(player: Player) {
        val invFrame = frame(3, Component.text("§c§l지옥 상점")) {
            onOpen { openEvent ->
                openEvent.player.sendMessage("§4§l익스플로전!!")
            }

            for (x: Int in 0..8 step 1) {
                slot(x, 0) {
                    item = ItemStack(Material.RED_STAINED_GLASS_PANE)
                    onClick { clickEvent ->
                        clickEvent.isCancelled = true
                    }
                }
            }

            for (x: Int in 0..8 step 2) {
                slot(x, 1) {
                    item = ItemStack(Material.RED_STAINED_GLASS_PANE)
                    onClick { clickEvent ->
                        clickEvent.isCancelled = true
                    }
                }
            }

            for (x: Int in 0..8 step 1) {
                slot(x, 2) {
                    item = ItemStack(Material.RED_STAINED_GLASS_PANE)
                    onClick { clickEvent ->
                        clickEvent.isCancelled = true
                    }
                }
            }

            slot(1, 1) {
                val obsidian = ItemStack(Material.OBSIDIAN)
                val meta = obsidian.itemMeta

                meta.lore = mutableListOf("§a§l5000원으로 구매")

                obsidian.itemMeta = meta

                item = obsidian

                onClick { clickEvent ->
                    clickEvent.isCancelled = true

                    if (getInstance().config.getInt("playerdata.${player.name}.money") >= 5000) {
                        getInstance().config.set(
                            "playerdata.${player.name}.money",
                            getInstance().config.getInt("playerdata.${player.name}.money") - 5000
                        )

                        val obsidian = ItemStack(Material.OBSIDIAN)

                        player.inventory.addItem(obsidian)

                        player.sendMessage("§a구매 완료")
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                    } else {
                        player.sendMessage("§c잔액(코인)이 부족합니다")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
                    }
                }
            }

            slot(3, 1) {
                val netherite = ItemStack(Material.NETHERITE_SCRAP)
                val meta = netherite.itemMeta

                meta.lore = mutableListOf("§a§l12500원으로 구매")

                netherite.itemMeta = meta

                item = netherite

                onClick { clickEvent ->
                    clickEvent.isCancelled = true

                    if (getInstance().config.getInt("playerdata.${player.name}.money") >= 12500) {
                        getInstance().config.set(
                            "playerdata.${player.name}.money",
                            getInstance().config.getInt("playerdata.${player.name}.money") - 12500
                        )

                        val netherite = ItemStack(Material.NETHERITE_SCRAP)

                        player.inventory.addItem(netherite)

                        player.sendMessage("§a구매 완료")
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                    } else {
                        player.sendMessage("§c잔액(코인)이 부족합니다")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
                    }
                }
            }

            slot(5, 1) {
                val wart = ItemStack(Material.NETHER_WART)
                val meta = wart.itemMeta

                meta.lore = mutableListOf("§a§l2500원으로 구매")
                wart.amount = 5

                wart.itemMeta = meta

                item = wart

                onClick { clickEvent ->
                    clickEvent.isCancelled = true

                    if (getInstance().config.getInt("playerdata.${player.name}.money") >= 2500) {
                        getInstance().config.set(
                            "playerdata.${player.name}.money",
                            getInstance().config.getInt("playerdata.${player.name}.money") - 2500
                        )

                        val wart = ItemStack(Material.NETHER_WART)
                        wart.amount = 5

                        player.inventory.addItem(wart)

                        player.sendMessage("§a구매 완료")
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                    } else {
                        player.sendMessage("§c잔액(코인)이 부족합니다")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
                    }
                }
            }

            slot(7, 1) {
                val blazerod = ItemStack(Material.BLAZE_ROD)
                val meta = blazerod.itemMeta

                meta.lore = mutableListOf("§a§l20000원으로 구매")

                blazerod.itemMeta = meta

                item = blazerod

                onClick { clickEvent ->
                    clickEvent.isCancelled = true

                    if (getInstance().config.getInt("playerdata.${player.name}.money") >= 20000) {
                        getInstance().config.set(
                            "playerdata.${player.name}.money",
                            getInstance().config.getInt("playerdata.${player.name}.money") - 20000
                        )

                        val blazerod = ItemStack(Material.BLAZE_ROD)

                        player.inventory.addItem(blazerod)

                        player.sendMessage("§a구매 완료")
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                    } else {
                        player.sendMessage("§c잔액(코인)이 부족합니다")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
                    }
                }
            }
        }
        player.openFrame(invFrame)
    }

    private fun minershop(player: Player) {
        val invFrame = frame(3, Component.text("§7§l광부상점")) {
            onOpen { openEvent ->
                openEvent.player.sendMessage("§7§l오늘은 어떤 광물을 가져왔나?")
            }

            for (x: Int in 0..8 step 1) {
                slot(x, 0) {
                    item = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
                    onClick { clickEvent ->
                        clickEvent.isCancelled = true
                    }
                }
            }

            for (x: Int in 1..7 step 2) {
                slot(x, 1) {
                    item = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
                    onClick { clickEvent ->
                        clickEvent.isCancelled = true
                    }
                }
            }

            for (x: Int in 0..8 step 1) {
                slot(x, 2) {
                    item = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
                    onClick { clickEvent ->
                        clickEvent.isCancelled = true
                    }
                }
            }

            slot(0, 1) {
                val iron = ItemStack(Material.IRON_INGOT)
                val meta = iron.itemMeta

                meta.lore = mutableListOf("§a§l100원으로 판매")
                iron.amount = 10

                iron.itemMeta = meta

                item = iron

                onClick { clickEvent ->
                    clickEvent.isCancelled = true

                    val iron = ItemStack(Material.IRON_INGOT)

                    iron.amount = 10
                    if (player.inventory.containsAtLeast(ItemStack(Material.IRON_INGOT), 10)) {
                        getInstance().config.set(
                            "playerdata.${player.name}.money",
                            getInstance().config.getInt("playerdata.${player.name}.money") + 100
                        )

                        player.inventory.removeItem(iron)

                        player.sendMessage("§a판매 완료")
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                    } else {
                        player.sendMessage("§c아이템이 부족합니다")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
                    }
                }
            }

            slot(2, 1) {
                val gold = ItemStack(Material.GOLD_INGOT)
                val meta = gold.itemMeta

                meta.lore = mutableListOf("§a§l100원으로 판매")
                gold.amount = 5

                gold.itemMeta = meta

                item = gold

                onClick { clickEvent ->
                    clickEvent.isCancelled = true

                    val gold = ItemStack(Material.GOLD_INGOT)

                    gold.amount = 5

                    if (player.inventory.containsAtLeast(ItemStack(Material.GOLD_INGOT), 5)) {
                        getInstance().config.set(
                            "playerdata.${player.name}.money",
                            getInstance().config.getInt("playerdata.${player.name}.money") + 100
                        )

                        player.inventory.removeItem(gold)

                        player.sendMessage("§a판매 완료")
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                    } else {
                        player.sendMessage("§c아이템이 부족합니다")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
                    }
                }
            }

            slot(4, 1) {
                val diamond = ItemStack(Material.DIAMOND)
                val meta = diamond.itemMeta

                meta.lore = mutableListOf("§a§l500원으로 판매")
                diamond.amount = 5

                diamond.itemMeta = meta

                item = diamond

                onClick { clickEvent ->
                    clickEvent.isCancelled = true

                    val diamond = ItemStack(Material.DIAMOND)
                    diamond.amount = 5

                    if (player.inventory.containsAtLeast(ItemStack(Material.DIAMOND), 5)) {
                        getInstance().config.set(
                            "playerdata.${player.name}.money",
                            getInstance().config.getInt("playerdata.${player.name}.money") + 500
                        )

                        player.inventory.removeItem(diamond)

                        player.sendMessage("§a판매 완료")
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                    } else {
                        player.sendMessage("§c아이템이 부족합니다")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
                    }
                }
            }

            slot(6, 1) {
                val netherite = ItemStack(Material.NETHERITE_INGOT)
                val meta = netherite.itemMeta

                meta.lore = mutableListOf("§a§l5000원으로 판매")

                netherite.itemMeta = meta

                item = netherite

                onClick { clickEvent ->
                    clickEvent.isCancelled = true

                    val netherite = ItemStack(Material.NETHERITE_INGOT)
                    netherite.amount = 1

                    if (player.inventory.containsAtLeast(ItemStack(Material.NETHERITE_INGOT), 1)) {
                        getInstance().config.set(
                            "playerdata.${player.name}.money",
                            getInstance().config.getInt("playerdata.${player.name}.money") + 5000
                        )

                        player.inventory.removeItem(netherite)

                        player.sendMessage("§a판매 완료")
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                    } else {
                        player.sendMessage("§c아이템이 부족합니다")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
                    }
                }
            }

            slot(8, 1) {
                val coal = ItemStack(Material.COAL)
                val meta = coal.itemMeta

                meta.lore = mutableListOf("§a§l100원으로 판매")
                coal.amount = 50

                coal.itemMeta = meta

                item = coal

                onClick { clickEvent ->
                    clickEvent.isCancelled = true

                    val coal = ItemStack(Material.COAL)
                    coal.amount = 50

                    if (player.inventory.containsAtLeast(ItemStack(Material.COAL), 50)) {
                        getInstance().config.set(
                            "playerdata.${player.name}.money",
                            getInstance().config.getInt("playerdata.${player.name}.money") + 100
                        )

                        player.inventory.removeItem(coal)

                        player.sendMessage("§a판매 완료")
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f)
                    } else {
                        player.sendMessage("§c아이템이 부족합니다")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f)
                    }
                }
            }
        }
        player.openFrame(invFrame)
    }
}