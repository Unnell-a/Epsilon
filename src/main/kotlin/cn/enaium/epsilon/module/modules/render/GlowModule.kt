package cn.enaium.epsilon.module.modules.render

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.EventUpdate
import cn.enaium.epsilon.module.Category
import cn.enaium.epsilon.module.Module
import cn.enaium.epsilon.module.ModuleAT

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@ModuleAT
class GlowModule : Module("Glow", 0, Category.RENDER) {
    @EventAT
    fun onUpdate(eventUpdate: EventUpdate) {
        for (e in Epsilon.MC.world!!.entities) {
            e.isGlowing = true
        }
    }

    override fun onDisable() {
        super.onDisable()
        for (e in Epsilon.MC.world!!.entities) {
            e.isGlowing = false
        }
    }
}