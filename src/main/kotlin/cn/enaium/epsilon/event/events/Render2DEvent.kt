package cn.enaium.epsilon.event.events

import cn.enaium.epsilon.event.Event
import net.minecraft.client.util.math.MatrixStack

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class Render2DEvent(var matrixStack: MatrixStack, var partialTicks: Float) : Event(Type.PRE)