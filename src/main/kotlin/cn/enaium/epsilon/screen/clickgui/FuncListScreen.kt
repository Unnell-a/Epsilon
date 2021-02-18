package cn.enaium.epsilon.screen.clickgui

import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.Epsilon
import cn.enaium.epsilon.client.MC
import cn.enaium.cf4m.CF4M
import cn.enaium.epsilon.ui.UI
import cn.enaium.epsilon.ui.elements.Button
import cn.enaium.epsilon.ui.elements.ScrollPanel
import cn.enaium.epsilon.client.utils.Render2DUtils
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class FuncListScreen(val category: Category) : UI() {

    override fun initUI() {
        super.initUI()
        val scrollPanel = ScrollPanel(Render2DUtils.scaledWidth / 2 - 50, 50, 100, 120)
        var y = 0
        for (func in getFuncForCategory(category)) {
            scrollPanel.addElement(object : Button(
                0,
                y,
                CF4M.INSTANCE.module.getName(func)
            ) {
                override fun onLeftClicked() {
                    CF4M.INSTANCE.module.enable(func)
                    super.onLeftClicked()
                }

                override fun onRightClicked() {
                    MC.openScreen(SettingListScreen(func))
                    super.onRightClicked()
                }

                override fun onMiddleClicked() {
                    MC.openScreen(EditKeyboardScreen(func))
                    super.onMiddleClicked()
                }
            })
            y += 30
        }
        addElement(scrollPanel)
    }

    private fun getFuncForCategory(category: Category): ArrayList<Any> {
        var list: ArrayList<Any> = ArrayList();
        for (module in CF4M.INSTANCE.module.modules) {
            if (CF4M.INSTANCE.module.getCategory(module).equals(category)) {
                list.add(module)
            }
        }
        return list;
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE)
            MC.openScreen(CategoryListScreen())
        return super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun isPauseScreen(): Boolean {
        return false
    }

    override fun shouldCloseOnEsc(): Boolean {
        return false
    }
}