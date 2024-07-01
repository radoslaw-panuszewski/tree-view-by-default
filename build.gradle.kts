@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.intellij)
}

group = "pl.semidude"
version = versionFromNearestTag()

repositories {
    mavenCentral()
    maven { url = uri("https://www.jetbrains.com/intellij-repository/snapshots/") }
}

intellij {
    version = "242-EAP-SNAPSHOT"
    type = "IU"
    plugins = listOf("DatabaseTools")
    updateSinceUntilBuild = false
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks {
    runIde {
        ideDir = File("${System.getenv("HOME")}/Applications/DataGrip.app/Contents")
    }

    signPlugin {
        certificateChain = System.getenv("CERTIFICATE_CHAIN")
        privateKey = System.getenv("PRIVATE_KEY")
        password = System.getenv("PRIVATE_KEY_PASSWORD")
    }

    publishPlugin {
        token = System.getenv("PUBLISH_TOKEN")
    }

    buildSearchableOptions {
        enabled = false
    }
}