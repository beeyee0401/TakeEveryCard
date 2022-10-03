plugins {
    java
}

var stsInstallLocation: String = System.getenv("STS_INSTALL")
var compileOnlyLibs: String = System.getenv("STS_MODDING_LIB")

var modName: String = rootProject.name

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(fileTree(compileOnlyLibs))
    compileOnly("org.apache.commons:commons-lang3:3.12.0")
}

tasks.register<Jar>("buildJAR") {
    group = "Slay the Spire"
    description = "Builds a fat (includes runtime dependencies) JAR in the build/libs folder"

    // Main code
    from(sourceSets.main.get().output)

    // Any runtime dependencies (e.g. from mavenCentral(), local JARs, etc.)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter {
            it.name.endsWith("jar")
        }.map {
            zipTree(it)
        }
    })
}

tasks.register<Copy>("buildAndCopyJAR") {
    group = "Slay the Spire"
    description = "Copies the JAR from your build/libs folder into your \$STS_INSTALL location"

    dependsOn("buildJAR")

    from("build/libs/$modName.jar")
    into("$stsInstallLocation\\mods")
}
