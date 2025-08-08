package com.poly.helpme

import com.github.mustachejava.DefaultMustacheFactory
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import java.io.File
import java.io.StringWriter
import kotlin.text.drop
import kotlin.text.plus
import com.intellij.openapi.project.Project

abstract class AbstractAction : AnAction() {
    abstract val title: String
    abstract val CTA: String
    abstract var files: List<Pair<String, String>>

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: run {
            Messages.showErrorDialog("No project found.", "Error")
            return
        }
        val virtualFile = event.getData(CommonDataKeys.VIRTUAL_FILE) ?: run {
            Messages.showErrorDialog("Please select a directory in the Project view.", "Error")
            return
        }
        if (!virtualFile.isDirectory) {
            Messages.showErrorDialog("Please select a directory, not a file.", "Error")
            return
        }
        val directory = PsiManager.getInstance(project).findDirectory(virtualFile) ?: run {
            Messages.showErrorDialog("Unable to find directory.", "Error")
            return
        }
        val repoName = Messages.showInputDialog(
            project,
            title,
            CTA,
            null
        )
        if (repoName.isNullOrBlank()) {
            Messages.showErrorDialog(project, "Name cannot be empty.", "Error")
            return
        }
        val packageName = directory.findPackageName(project)
        val repoNameLower = repoName[0].lowercaseChar() + repoName.drop(1)
        val context = mapOf(
            "PACKAGE_NAME" to packageName,
            "NAME" to repoName,
            "NAME_LOWER" to repoNameLower
        )
        val templateDir = File(project.basePath, "templates")
        val mustacheFactory = if (templateDir.exists() && templateDir.isDirectory) {
            println("Using external templates from: ${templateDir.absolutePath}")
            DefaultMustacheFactory(templateDir)
        } else {
            println("Using internal templates from resources")
            DefaultMustacheFactory()
        }

        WriteCommandAction.runWriteCommandAction(project) {
            try {
                for (file in files) {
                    // Générer et ajouter l'interface
                    val interfaceMustache = mustacheFactory.compile(file.first)
                    val interfaceWriter = StringWriter()
                    interfaceMustache.execute(interfaceWriter, context).flush()
                    val interfaceText = StringUtil.convertLineSeparators(interfaceWriter.toString())
                    val interfaceFile = PsiFileFactory.getInstance(project)
                        .createFileFromText(fileName(file.first, repoName, file.second), interfaceText)
                    directory.add(interfaceFile)
                }

                Messages.showInfoMessage(project, "Files created successfully!", "Success")
            } catch (e: Exception) {
                Messages.showErrorDialog(project, "Failed to generate files: ${e.message}", "Error")
                e.printStackTrace()
            }
        }
    }

    open fun fileName(mustache: String, name: String, filename: String): String {
        return "$name$filename"
    }
}

fun PsiDirectory.findPackageName(project: Project): String {
    val psiManager = PsiManager.getInstance(project)
    val javaPsiFacade = JavaPsiFacade.getInstance(project)
    var current: PsiDirectory? = this
    val packageParts = mutableListOf<String>()
    while (current != null) {
        val virtualFile = current.virtualFile
        val sourceRoots = project.baseDir.findChild("src")?.findChild("main")?.findChild("java")
        if (virtualFile.path.contains("/src/main/java")) {
            val relativePath = virtualFile.path.substringAfter("/src/main/java/")
            if (relativePath.isNotEmpty()) {
                val packageName = relativePath.replace('/', '.').removeSuffix("/")
                if (packageName.isNotEmpty()) {
                    packageParts.add(packageName)
                    break
                }
            }
        }
        current = current.parent
    }
    return if (packageParts.isNotEmpty()) {
        packageParts.reversed().joinToString(".")
    } else {
        "com.example"
    }
}