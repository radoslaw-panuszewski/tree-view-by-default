import org.gradle.api.Project

fun Project.versionFromNearestTag(): String =
    exec("git describe --tags --always --first-parent").trim()