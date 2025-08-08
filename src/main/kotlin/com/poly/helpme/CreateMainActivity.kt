package com.poly.helpme

import com.intellij.openapi.actionSystem.AnActionEvent

class CreateMainActivity : AbstractAction() {
    override val title = "Enter main activity Name (e.g., Home):"
    override val CTA = "Create main activity"
    override var files = listOf<Pair<String, String>>(
        "MainActivity.mustache" to "Activity.kt",
        "NavHost.mustache" to "NavHost.kt",
        "Routes.mustache" to "Route.kt",
    )

    override fun actionPerformed(event: AnActionEvent) {
        super.actionPerformed(event)
    }
}