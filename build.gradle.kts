plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.0"
    id("org.jetbrains.intellij") version "1.11.0"
    id("com.glovoapp.semantic-versioning") version "1.1.10"
}

group = "pl.semidude"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2022.3.2")
    type.set("IU")
    plugins.set(listOf("DatabaseTools"))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    runIde {
        ideDir.set(File(System.getenv("DATAGRIP_DIR")))
    }

    patchPluginXml {
        sinceBuild.set("223")
        untilBuild.set("223.*")
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