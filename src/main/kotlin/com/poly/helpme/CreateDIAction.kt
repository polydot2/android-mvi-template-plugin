package com.poly.helpme

import com.intellij.openapi.actionSystem.AnActionEvent

class CreateDIAction : AbstractAction() {
    override val title = "Enter DI Name (e.g., di):"
    override val CTA = "Create DI file"
    override var files = listOf<Pair<String, String>>(
        "DI.mustache" to "Module.kt",
    )

    override fun actionPerformed(event: AnActionEvent) {
        super.actionPerformed(event)
    }
}