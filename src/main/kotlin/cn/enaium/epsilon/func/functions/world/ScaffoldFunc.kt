package cn.enaium.epsilon.func.functions.world

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.IMC
import cn.enaium.epsilon.event.Event.Type
import cn.enaium.epsilon.event.events.MotionEvent
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.setting.settings.EnableSetting
import net.minecraft.block.AirBlock
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d


/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class ScaffoldFunc : Func("Scaffold", 0, Category.WORLD) {
    private var currentPos: BlockPos? = null
    private var currentDirection: Direction? = null

    private val eagle = EnableSetting(this, "Eagle", false)
    
    fun on(motionEvent: MotionEvent) {
        if (eagle.enable) {
            MC.options.keySneak.isPressed = (currentPos != null)
        }

        when (motionEvent.type) {
            Type.PRE -> {
                currentPos = null
                currentDirection = null
                val pos = BlockPos(MC.player!!.pos.x, MC.player!!.pos.y - 1.0, MC.player!!.pos.z)
                if (MC.world!!.getBlockState(pos).block is AirBlock) {
                    setBlockAndFacing(pos)
                }
            }
            Type.POST -> {
                if (currentPos != null) {
                    var newSlot = -1
                    for (i in 0..9) {
                        val itemStack = MC.player!!.inventory.getStack(i)
                        if (itemStack.isEmpty || itemStack.item !is BlockItem)
                            continue
                        newSlot = i
                    }

                    if (newSlot == -1)
                        return

                    MC.player!!.swingHand(Hand.MAIN_HAND)
                    onPlayerRightClick(
                        currentPos!!, currentDirection!!,
                        Vec3d(currentPos!!.x.toDouble(), currentPos!!.y.toDouble(), currentPos!!.z.toDouble())
                    )
                }
            }
        }
    }

    override fun onDisable() {
        super.onDisable()
        MC.options.keySneak.isPressed = false
    }

    private fun setBlockAndFacing(var1: BlockPos) = when {
        MC.world!!.getBlockState(var1.add(0, -1, 0)).block !== Blocks.AIR -> {
            currentPos = var1.add(0, -1, 0)
            currentDirection = Direction.UP
        }
        MC.world!!.getBlockState(var1.add(-1, 0, 0)).block !== Blocks.AIR -> {
            currentPos = var1.add(-1, 0, 0)
            currentDirection = Direction.EAST
        }
        MC.world!!.getBlockState(var1.add(1, 0, 0)).block !== Blocks.AIR -> {
            currentPos = var1.add(1, 0, 0)
            currentDirection = Direction.WEST
        }
        MC.world!!.getBlockState(var1.add(0, 0, -1)).block !== Blocks.AIR -> {
            currentPos = var1.add(0, 0, -1)
            currentDirection = Direction.SOUTH
        }
        MC.world!!.getBlockState(var1.add(0, 0, 1)).block !== Blocks.AIR -> {
            currentPos = var1.add(0, 0, 1)
            currentDirection = Direction.NORTH
        }
        else -> {
            currentPos = null
            currentDirection = null
        }
    }

    private fun onPlayerRightClick(blockPos: BlockPos, direction: Direction, vec3d: Vec3d) {
        IMC.interactionManager.invokeInteractBlock(
            MC.player,
            MC.world,
            Hand.MAIN_HAND,
            BlockHitResult(vec3d, direction, blockPos, false)
        )
        IMC.interactionManager.invokeInteractItem(MC.player, MC.world, Hand.MAIN_HAND)
    }
}