import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.intellij") version "1.15.0"
    id("com.glovoapp.semantic-versioning") version "1.1.10"
}

group = "pl.semidude"

repositories {
    mavenCentral()
}

intellij {
    version.set("2023.2")
    type.set("IU")
    plugins.set(listOf("DatabaseTools"))
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
        gradleVersion = "8.2"
    }

    runIde {
        ideDir.set(File(System.getenv("DATAGRIP_DIR")))
    }

    patchPluginXml {
        sinceBuild.set("232")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    task("printVersion") {
        doLast {
            println(project.semanticVersion.version.get())
        }
    }
}