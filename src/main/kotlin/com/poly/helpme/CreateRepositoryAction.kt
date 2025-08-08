package com.poly.helpme

import com.intellij.openapi.actionSystem.AnActionEvent

class CreateRepositoryAction : AbstractAction() {
    override val title = "Enter repository Name (e.g., MyRepo):"
    override val CTA = "Create repositories"
    override var files = listOf<Pair<String, String>>(
        "Repository.mustache" to "Repository.kt",
        "RepositoryImpl.mustache" to "RepositoryImpl.kt",
    )

    override fun actionPerformed(event: AnActionEvent) {
        super.actionPerformed(event)
    }
}