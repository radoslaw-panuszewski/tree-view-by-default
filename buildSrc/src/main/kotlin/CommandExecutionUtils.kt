import org.gradle.api.Project
import java.io.File

fun Project.exec(command: String): String =
    exec(command, rootDir)

fun exec(command: String, workingDirectory: File): String {
    print("$ $command")
    System.out.flush()
    val process = Runtime.getRuntime().exec(command, null, workingDirectory)
    val output = process.inputReader().readText()
    val errorOutput = process.errorReader().readText()
    val exitCode = process.waitFor()
    println(" (exit code: $exitCode)")
    print(output)
    print(errorOutput)

    if (exitCode != 0) {
        error("Command '$command' failed with exit code $exitCode. See the output above for details.")
    }
    return output + "\n" + errorOutput
}
