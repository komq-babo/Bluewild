package io.github.koba.bluewildmoney

import io.github.monun.kommand.PluginKommand
import io.github.monun.kommand.getValue
import io.github.monun.tap.fake.FakeEntity
import io.github.monun.tap.mojangapi.MojangAPI
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

object Kommand {

    private fun getInstance(): Plugin {
        return BluewildmoneyPlugin.instance
    }

    var specialshop: FakeEntity<Player>? = null
    var butchershop: FakeEntity<Player>? = null
    var dragonshop: FakeEntity<Player>? = null
    var nethershop: FakeEntity<Player>? = null
    var minershop: FakeEntity<Player>? = null
    var weaponshop: FakeEntity<Player>? = null
    var wanderershop: FakeEntity<Player>? = null


    private lateinit var plugin: BluewildmoneyPlugin

    internal fun register(plugin: BluewildmoneyPlugin, kommand: PluginKommand) {
        Kommand.plugin = plugin

        kommand.register("coin") {

            requires { isPlayer }

            executes {
                player.sendMessage("")
                player.sendMessage("§b§l[ §f§l은행 §b§l] §f§l/coin 확인 - 자신이 가지고 있는 돈을 확인합니다.")
                player.sendMessage("§b§l[ §f§l은행 §b§l] §f§l/coin 보내기 [닉네임] [금액] - 자신의 돈을 [닉네임]에게 보냅니다.")
                player.sendMessage("")
            }

            then("확인") {
                executes {
                    val money = getInstance().config.getInt("playerdata.${player.name}.money")

                    player.sendMessage("")
                    player.sendMessage("§b§l[ §f§l은행 §b§l] §f§l당신이 가지고 있는 소지금은 §6§l$money§f§l원 입니다.")
                    player.sendMessage("")
                }
            }

            then("보내기") {
                executes {
                    player.sendMessage("")
                    player.sendMessage("§b§l[ §f§l은행 §b§l] §f§l/coin 보내기 [닉네임] [금액] - 자신의 돈을 [닉네임]에게 보냅니다.")
                    player.sendMessage("")
                }
                then("recipient" to player()) {
                    executes {
                        player.sendMessage("")
                        player.sendMessage("§b§l[ §f§l은행 §b§l] §f§l/coin 보내기 [닉네임] [금액] - 자신의 돈을 [닉네임]에게 보냅니다.")
                        player.sendMessage("")
                    }
                    then("money" to int()) {
                        executes {
                            val recipient: Player by it
                            val money: Int by it

                            if (money > getInstance().config.getInt("playerdata.${player.name}.money")) {
                                player.sendMessage("")
                                player.sendMessage("§b§l[ §f§l은행 §b§l] §c§l당신의 소지금보다 많은 양은 보내실 수 없습니다.")
                                player.sendMessage("")
                            } else if (money <= 0) {
                                player.sendMessage("")
                                player.sendMessage("§b§l[ §f§l은행 §b§l] §c§l돈은 §6§l0§c§l원 이하로 보내실 수 없습니다.")
                                player.sendMessage("")
                            } else {

                                getInstance().config.set(
                                    "playerdata.${player.name}.money",
                                    getInstance().config.getInt("playerdata.${player.name}.money") - money
                                )
                                getInstance().config.set(
                                    "playerdata.${recipient.name}.money",
                                    getInstance().config.getInt("playerdata.${recipient.name}.money") + money
                                )

                                getInstance().saveConfig()

                                player.sendMessage("")
                                player.sendMessage("§b§l[ §f§l은행 §b§l] §f§l당신은 " + "§c§l[ " + "§f§l" + recipient.name + "§c§l ]" + "§f§l에게 §6§l" + money + "§f§l원을 보내셨습니다.")
                                player.sendMessage("")

                                recipient.sendMessage("")
                                recipient.sendMessage("§b§l[ §f§l은행 §b§l] §f§l당신은 " + "§c§l[ " + "§f§l" + player.name + "§c§l ]" + "§f§l에게 §6§l" + money + "§f§l원을 받으셨습니다.")
                                recipient.sendMessage("")
                            }
                        }
                    }
                }
            }

            then("입금") {
                executes {
                    player.sendMessage("")
                    player.sendMessage("§b§l[ §f§l은행 §b§l] §c§l/coin 입금 [닉네임] [금액] - [닉네임]의 돈을 추가합니다.")
                    player.sendMessage("")
                }
                then("recipient" to player()) {
                    executes {
                        player.sendMessage("")
                        player.sendMessage("§b§l[ §f§l은행 §b§l] §c§l/coin 입금 [닉네임] [금액] - [닉네임]의 돈을 추가합니다.")
                        player.sendMessage("")
                    }
                    then("money" to int()) {
                        executes {
                            val recipient: Player by it
                            val money: Int by it

                            getInstance().config.set(
                                "playerdata.${recipient.name}.money",
                                getInstance().config.getInt("playerdata.${recipient.name}.money") + money
                            )
                            getInstance().saveConfig()

                            player.sendMessage("")
                            player.sendMessage("§b§l[ §f§l은행 §b§l] §f§l당신은 " + "§c§l[ " + "§f§l" + recipient.name + "§c§l ]" + "§f§l에게 §6§l" + money + "§f§l원을 주셨습니다.")
                            player.sendMessage("")

                            recipient.sendMessage("")
                            recipient.sendMessage("§b§l[ §f§l은행 §b§l] §f§l당신은 " + "§c§l[ " + "§f§l" + player.name + "§c§l ]" + "§f§l에게 §6§l" + money + "§f§l원을 받으셨습니다.")
                            recipient.sendMessage("")
                        }
                    }
                }
            }

            then("출금") {
                executes {
                    player.sendMessage("")
                    player.sendMessage("§b§l[ §f§l은행 §b§l] §c§l/coin 출금 [닉네임] [금액] - [닉네임]의 돈을 차감합니다.")
                    player.sendMessage("")
                }
                then("recipient" to player()) {
                    executes {
                        player.sendMessage("")
                        player.sendMessage("§b§l[ §f§l은행 §b§l] §c§l/coin 출금 [닉네임] [금액] - [닉네임]의 돈을 차감합니다.")
                        player.sendMessage("")
                    }
                    then("money" to int()) {
                        executes {
                            val recipient: Player by it
                            val money: Int by it

                            getInstance().config.set(
                                "playerdata.${recipient.name}.money",
                                getInstance().config.getInt("playerdata.${recipient.name}.money") - money
                            )
                            getInstance().saveConfig()

                            player.sendMessage("")
                            player.sendMessage("§b§l[ §f§l은행 §b§l] §f§l당신은 " + "§c§l[ " + "§f§l" + recipient.name + "§c§l ]" + "§f§l에게 §6§l" + money + "§f§l원을 빼셨습니다.")
                            player.sendMessage("")

                            recipient.sendMessage("")
                            recipient.sendMessage("§b§l[ §f§l은행 §b§l] §f§l당신은 " + "§c§l[ " + "§f§l" + player.name + "§c§l ]" + "§f§l에게 §6§l" + money + "§f§l원을 차감당하였습니다.")
                            recipient.sendMessage("")
                        }
                    }
                }
            }

            then("초기화") {
                executes {
                    player.sendMessage("")
                    player.sendMessage("§b§l[ §f§l은행 §b§l] §c§l/coin 초기화 [닉네임] - [닉네임]의 돈을 초기화 합니다.")
                    player.sendMessage("")
                }
                then("recipient" to player()) {
                    executes {
                        val recipient: Player by it

                        player.sendMessage("")
                        player.sendMessage(
                            "§b§l[ §f§l은행 §b§l] §f§l당신은 " + "§c§l[ " + "§f§l" + recipient.name + "§c§l ]" + "§f§l에게 §6§l" + getInstance().config.getInt(
                                "playerdata.${recipient.name}.money"
                            ) + "§f§l원을 초기화 하셨습니다."
                        )
                        player.sendMessage("")

                        recipient.sendMessage("")
                        recipient.sendMessage(
                            "§b§l[ §f§l은행 §b§l] §f§l당신은 " + "§c§l[ " + "§f§l" + player.name + "§c§l ]" + "§f§l에게 §6§l" + getInstance().config.getInt(
                                "playerdata.${recipient.name}.money"
                            ) + "§f§l원을 초기화 당하였습니다."
                        )
                        recipient.sendMessage("")

                        getInstance().config.set("playerdata.${recipient.name}.money", 0)
                        getInstance().saveConfig()
                    }
                }
            }
        }

        kommand.register("shop") {
            requires { isPlayer && isOp }

            then("spawn") {

                then("specialshop") {
                    executes {
                        val uuid =
                            MojangAPI.fetchProfile(getInstance().config.getString("shop.skin.specialshop").toString())!!.uuid()
                        val profiles = MojangAPI.fetchSkinProfile(uuid)!!.profileProperties()

                        specialshop = BluewildmoneyPlugin.fakeServer.spawnPlayer(
                            player.location,
                            "§e§lspecial shop",
                            profiles.toSet()
                        )
                    }
                }

                then("정육점") {
                    executes {
                        val uuid =
                            MojangAPI.fetchProfile(getInstance().config.getString("shop.skin.butchershop").toString())!!.uuid()
                        val profiles = MojangAPI.fetchSkinProfile(uuid)!!.profileProperties()

                        butchershop = BluewildmoneyPlugin.fakeServer.spawnPlayer(player.location, "§6§l정육점", profiles.toSet())
                    }
                }

                then("엔더드래곤") {
                    executes {
                        val uuid =
                            MojangAPI.fetchProfile(getInstance().config.getString("shop.skin.dragonshop").toString())!!.uuid()
                        val profiles = MojangAPI.fetchSkinProfile(uuid)!!.profileProperties()

                        dragonshop = BluewildmoneyPlugin.fakeServer.spawnPlayer(
                            player.location,
                            "§9§l엔§1§l더§9§l드§1§l래§9§l곤",
                            profiles.toSet()
                        )
                    }
                }

                then("지옥상점") {
                    executes {
                        val uuid =
                            MojangAPI.fetchProfile(getInstance().config.getString("shop.skin.nethershop").toString())!!.uuid()
                        val profiles = MojangAPI.fetchSkinProfile(uuid)!!.profileProperties()

                        nethershop = BluewildmoneyPlugin.fakeServer.spawnPlayer(player.location, "§c§l지옥 상점", profiles.toSet())
                    }
                }

                then("광부") {
                    executes {
                        val uuid =
                            MojangAPI.fetchProfile(getInstance().config.getString("shop.skin.minershop").toString())!!.uuid()
                        val profiles = MojangAPI.fetchSkinProfile(uuid)!!.profileProperties()

                        minershop = BluewildmoneyPlugin.fakeServer.spawnPlayer(player.location, "§7§l광부", profiles.toSet())
                    }
                }

                then("무기제작자") {
                    executes {
                        val uuid =
                            MojangAPI.fetchProfile(getInstance().config.getString("shop.skin.weaponshop").toString())!!.uuid()
                        val profiles = MojangAPI.fetchSkinProfile(uuid)!!.profileProperties()

                        weaponshop =
                            BluewildmoneyPlugin.fakeServer.spawnPlayer(player.location, "§8§l무기 제작자", profiles.toSet())
                    }
                }

                then("떠돌이") {
                    executes {
                        val uuid =
                            MojangAPI.fetchProfile(getInstance().config.getString("shop.skin.wanderershop").toString())!!.uuid()
                        val profiles = MojangAPI.fetchSkinProfile(uuid)!!.profileProperties()

                        wanderershop =
                            BluewildmoneyPlugin.fakeServer.spawnPlayer(player.location, "§7§l떠돌이", profiles.toSet())
                    }
                }
            }

            then("remove") {
                then("specialshop") {
                    executes {
                        specialshop!!.remove()
                    }
                }

                then("정육점") {
                    executes {
                        butchershop!!.remove()
                    }
                }

                then("엔더드래곤") {
                    executes {
                        dragonshop!!.remove()
                    }
                }

                then("지옥상점") {
                    executes {
                        nethershop!!.remove()
                    }
                }

                then("광부") {
                    executes {
                        minershop!!.remove()
                    }
                }

                then("무기제작자") {
                    executes {
                        weaponshop!!.remove()
                    }
                }

                then("떠돌이") {
                    executes {
                        wanderershop!!.remove()
                    }
                }
            }
        }
    }
}
