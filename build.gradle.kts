@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.intellij.platform)
    alias(libs.plugins.axion.release)
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

scmVersion {
    tag {
        fallbackPrefixes = listOf("")
    }
    unshallowRepoOnCI = true
}

group = "pl.semidude"
version = scmVersion.version

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaUltimate("2024.2.3")
        bundledPlugins("com.intellij.database")
        instrumentationTools()
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "242"
            untilBuild = provider { null }
        }
        vendor {
            name = "Radosław Panuszewski"
            email = "radoslaw.panuszewski15@gmail.com"
            url = "https://panuszewski.dev"
        }
        id = "pl.semidude.tree-view-by-default"
        name = "Tree View By Default"
        description = """
            If you use document databases (like MongoDB), it is more convenient to preview the query
            results in <tt>Tree</tt> mode instead of the standard <tt>Table</tt> mode. Unfortunately, DataGrip does
            not provide any way to set <tt>Tree<tt> as default presentation mode, so you have to switch it
            manually every time.
            <br /> <br />
            This plugin comes to rescue!
            <br /> <br />
            What it does:
            <ul>
                <li>Changes default presentation mode of the query results to <tt>Tree</tt></li>
                <li>If there is only one result, it is automatically expanded</li>
                <li>You can copy value of the leaf node without the containing JSON via Cmd+Option+C </li>
            </ul>
            <br />
            It is recommended to use this plugin in DataGrip, but it should also work in other IDEs with database support.
        """.trimIndent()
    }
    signing {
        certificateChain = System.getenv("CERTIFICATE_CHAIN")
        privateKey = System.getenv("PRIVATE_KEY")
        password = System.getenv("PRIVATE_KEY_PASSWORD")
    }
    publishing {
        token = System.getenv("PUBLISH_TOKEN")
    }
    buildSearchableOptions = false
}