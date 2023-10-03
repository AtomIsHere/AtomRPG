plugins {
    `java-library`

    id("io.papermc.paperweight.userdev") version "1.5.5"
    id("xyz.jpenilla.run-paper") version "2.2.0"

    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.github.atomishere"
version = "1.0-SNAPSHOT"
description = "A plugin which aims to add RPG elements into minecraft"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.aikar.co/content/groups/aikar/") }
}

dependencies {
    paperweight.paperDevBundle("1.20.2-R0.1-SNAPSHOT")

    implementation("com.google.inject:guice:7.0.0")

    implementation("io.github.classgraph:classgraph:4.8.162")
    implementation("io.github.toolfactory:narcissus:1.0.7")

    implementation("co.aikar:acf-paper:0.5.1-SNAPSHOT")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()

        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
        val props = mapOf(
                "name" to project.name,
                "version" to project.version,
                "description" to project.description,
                "apiVersion" to "1.20"
        )
        inputs.properties(props)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }

    shadowJar {
        isEnableRelocation = true
        relocationPrefix = "com.github.atomishere.atomrpg.depends"
    }
}