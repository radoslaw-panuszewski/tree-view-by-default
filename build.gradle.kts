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

intellij {
    version.set("2023.1")
    type.set("IU")
    plugins.set(listOf("DatabaseTools"))
}

tasks {
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
        sinceBuild.set("231")
        untilBuild.set("231.*")
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