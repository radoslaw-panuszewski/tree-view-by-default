@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.intellij)
}

group = "pl.semidude"
version = versionFromNearestTag()

repositories {
    mavenCentral()
}

intellij {
    version = "2023.3"
    type = "IU"
    plugins = listOf("DatabaseTools")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    withType<Wrapper> {
        gradleVersion = "8.5"
    }

    runIde {
        ideDir = File("/Users/radoslaw.panuszewski/Applications/DataGrip.app/Contents")
    }

    signPlugin {
        certificateChain = System.getenv("CERTIFICATE_CHAIN")
        privateKey = System.getenv("PRIVATE_KEY")
        password = System.getenv("PRIVATE_KEY_PASSWORD")
    }

    publishPlugin {
        token = System.getenv("PUBLISH_TOKEN")
    }

    patchPluginXml {
        sinceBuild = "233"
        untilBuild = "*.*"
    }
}