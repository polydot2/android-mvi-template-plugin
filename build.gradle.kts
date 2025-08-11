plugins {
    id("org.jetbrains.intellij.platform")
    kotlin("jvm") version "2.0.0"
}

group = "com.poly"
version = "1.0-SNAPSHOT"

dependencies {
    intellijPlatform {
        androidStudio("251.26094")
        //local("C:/Program Files/Android/Android Studio")
        bundledPlugin("org.jetbrains.android")
    }
    implementation("com.github.spullara.mustache.java:compiler:0.9.10") {
        isTransitive = true
    }
}

intellijPlatform {
    pluginConfiguration {
        version = project.version.toString()
        id = "com.poly.repositorygenerator"
        name = "Repository Generator"
        description = "Generates a Repository with interface, implementation, and DI module using Mustache templates."
    }
}
//    buildPlatform {
//        sinceBuild = "251.*" // Compatible avec Android Studio 2025.1
//        untilBuild = "252.*" // Limite supérieure
//    }
//}
//
tasks {
    buildPlugin {
        doLast {
            copy {
                from(configurations.runtimeClasspath)
                into("$buildDir/distributions/lib")
            }
        }
    }
}
