package cn.enaium.epsilon.func.functions.render

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.IMC
import cn.enaium.epsilon.event.events.Render3DEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.setting.settings.EnableSetting
import cn.enaium.epsilon.utils.BlockUtils
import cn.enaium.epsilon.utils.Render3DUtils
import net.minecraft.block.entity.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.world.chunk.BlockEntityTickInvoker
import org.lwjgl.opengl.GL11
import java.awt.Color

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class StorageESPFunc : Func("StorageESP", 0, Category.RENDER) {
    private val chest = EnableSetting(this, "Chest", true)

    private val trappedChest = EnableSetting(this, "TrappedChest", true)

    private val enderChest = EnableSetting(this, "EnderChest", true)

    private val shulkerBoxChest = EnableSetting(this, "ShulkerBoxChest", true)

    private val hopper = EnableSetting(this, "Hopper", true)

    private val furnace = EnableSetting(this, "Furnace", true)

    private val dispenser = EnableSetting(this, "Dispenser", true)

    private val dropper = EnableSetting(this, "Dropper", true)

    private val barrel = EnableSetting(this, "Barrel", true)

    private val blastFurnace = EnableSetting(this, "BlastFurnace", true)

    private val smoker = EnableSetting(this, "Smoker", true)

    private var storageBox = 0

    override fun onEnable() {
        super.onEnable()
        storageBox = GL11.glGenLists(1)
        GL11.glNewList(storageBox, GL11.GL_COMPILE)
        val bb = Box(BlockPos.ORIGIN)
        Render3DUtils.drawOutlined(bb)
        GL11.glEndList()
    }

    override fun onDisable() {
        super.onDisable()
        GL11.glDeleteLists(storageBox, 1)
        storageBox = 0
    }

    fun onRender(render3DEvent: Render3DEvent) {
        for (t in getTargets()) {
            Render3DUtils.drawBox(BlockUtils.getBoundingBox(t), Color.GREEN, storageBox)
        }
    }

    private fun getTargets(): ArrayList<BlockPos> {
        val blocks: ArrayList<BlockPos> = ArrayList()
        for (blockEntity in BlockUtils.blockEntities.values) {
            when (blockEntity) {
                is ChestBlockEntity -> if (chest.enable) blocks.add(blockEntity.pos)
                is TrappedChestBlockEntity -> if (trappedChest.enable) blocks.add(blockEntity.pos)
                is EnderChestBlockEntity -> if (enderChest.enable) blocks.add(blockEntity.pos)
                is ShulkerBoxBlockEntity -> if (shulkerBoxChest.enable) blocks.add(blockEntity.pos)
                is HopperBlockEntity -> if (hopper.enable) blocks.add(blockEntity.pos)
                is DispenserBlockEntity -> if (dispenser.enable) blocks.add(blockEntity.pos)
                is DropperBlockEntity -> if (dropper.enable) blocks.add(blockEntity.pos)
                is FurnaceBlockEntity -> if (furnace.enable) blocks.add(blockEntity.pos)
                is BarrelBlockEntity -> if (barrel.enable) blocks.add(blockEntity.pos)
                is BlastFurnaceBlockEntity -> if (blastFurnace.enable) blocks.add(blockEntity.pos)
                is SmokerBlockEntity -> if (smoker.enable) blocks.add(blockEntity.pos)
            }
        }
        return blocks
    }
}