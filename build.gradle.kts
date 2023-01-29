plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.0"
    id("org.jetbrains.intellij") version "1.11.0"
    id("io.github.nefilim.gradle.semver-plugin") version "0.3.13"
}

semver {
    initialVersion("0.0.0")
    tagPrefix("")
    when (System.getenv("INCREASE_VERSION")) {
        "MAJOR" -> versionModifier { nextMajor() }
        "MINOR" -> versionModifier { nextMinor() }
        "PATCH" -> versionModifier { nextPatch() }
    }
}

group = "pl.semidude"
version = semver.version

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2021.3.3")
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
        sinceBuild.set("213")
        untilBuild.set("213.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    createAndPushVersionTag {
        dependsOn(publishPlugin)
    }

    register("releasePlugin") {
        dependsOn(createAndPushVersionTag)
    }
}