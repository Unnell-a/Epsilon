package cn.enaium.epsilon.client.commands

import cn.enaium.cf4m.annotation.Command
import cn.enaium.cf4m.command.ICommand
import cn.enaium.cf4m.setting.SettingBase
import cn.enaium.cf4m.setting.settings.*
import cn.enaium.epsilon.client.Epsilon
import cn.enaium.cf4m.CF4M
import cn.enaium.epsilon.client.setting.BlockListSetting
import cn.enaium.epsilon.client.utils.EpsilonConfiguration
import cn.enaium.epsilon.client.utils.Utils
import net.minecraft.util.Formatting
import java.util.*

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Command("s", "set")
class SetCommand : ICommand {
    override fun run(args: Array<String>): Boolean {
        if (args.size in 2..5) {

            val func = CF4M.INSTANCE.module.getModule(args[1])
            val settings: ArrayList<SettingBase> = CF4M.INSTANCE.module.getSettings(func)

            if (func == null) {
                this.error("""The func with the name "${args[1]}" does not exist.""", "")
                return true
            }

            if (settings.isEmpty()) {
                this.error("""The func with the name "${args[1]}" no setting exists.""", "")
                return true
            }

            if (args.size in 3..5) {
                if (Utils.getSetting(func, args[2]) == null) {
                    this.error("""The setting with the name "${args[2]}" does not exist.""", "")
                    return true
                }
            }

            if (args.size == 2) {
                this.message("Here are the list of settings:", "")
                for (s in settings) {
                    message(s.name, "[" + s.javaClass.simpleName + "]")
                }
            } else if (args.size == 3 || args.size == 4) {
                for (s in settings) {
                    if (args.size == 3) {
                        if (s.name.equals(args[2], true)) {
                            when (s) {
                                is EnableSetting -> {
                                    message("[${s.name}]" + "Enable:", s.enable)
                                }
                                is IntegerSetting -> {
                                    message("[${s.name}]" + "Current:", s.current)
                                }
                                is FloatSetting -> {
                                    message("[${s.name}]" + "Current:", s.current)
                                }
                                is DoubleSetting -> {
                                    message("[${s.name}]" + "Current:", s.current)
                                }
                                is LongSetting -> {
                                    message("[${s.name}]" + "Current:", s.current)
                                }
                                is BlockListSetting -> {
                                    message("[${s.name}]", s.blockList.toString())
                                }
                            }
                        }
                    } else if (args.size == 4) {
                        if (s.name.equals(args[2], true)) {
                            when (s) {
                                is EnableSetting -> {
                                    s.enable = args[3].toBoolean()
                                    success("Enable:", s.enable)
                                }
                                is IntegerSetting -> {
                                    s.current = args[3].toInt()
                                    success("Current:", s.current)
                                }
                                is FloatSetting -> {
                                    s.current = args[3].toFloat()
                                    success("Current:", s.current)
                                }
                                is DoubleSetting -> {
                                    s.current = args[3].toDouble()
                                    success("Current:", s.current)
                                }
                                is LongSetting -> {
                                    s.current = args[3].toLong()
                                    success("Current:", s.current)
                                }
                            }
                        }
                    }
                }
            } else if (args.size == 5) {
                if (args[3] == "add") {
                    (Utils.getSetting(
                        func,
                        args[2]
                    ) as BlockListSetting).blockList.add("minecraft:" + args[4])
                    success("Added minecraft:", args[4])
                } else if (args[3] == "remove") {
                    (Utils.getSetting(
                        func,
                        args[2]
                    ) as BlockListSetting).blockList.remove("minecraft:" + args[4])
                    success("Removed minecraft:", args[4])
                }
            }
            return true
        }
        return false
    }

    override fun usage(): String {
        return "set [module]\n" +
                "set [module] [setting]\n" +
                "set [module] [setting] [value]\n" +
                "set [module] [setting] add [value]\n" +
                "set [module] [setting] remove [value]"
    }

    private val success: String = "[" + Formatting.GREEN + Epsilon.NAME + Formatting.WHITE + "] "
    private val warning: String = "[" + Formatting.YELLOW + Epsilon.NAME + Formatting.WHITE + "] "
    private val message: String = "[" + Formatting.GRAY + Epsilon.NAME + Formatting.WHITE + "] "
    private val error: String = "[" + Formatting.RED + Epsilon.NAME + Formatting.WHITE + "] "

    private fun message(string: String, args: Any) {
        CF4M.INSTANCE.configuration.message(message + string + Formatting.LIGHT_PURPLE + args)
    }

    private fun error(string: String, args: Any) {
        CF4M.INSTANCE.configuration.message(error + string + Formatting.LIGHT_PURPLE + args)
    }

    private fun success(string: String, args: Any) {
        CF4M.INSTANCE.configuration.message(success + string + Formatting.LIGHT_PURPLE + args)
    }

}