import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    // enforcing Quarkus BOM to align Java / Kotlin versions
    id("buildlogic.quarkus-common-conventions")
    java
}
val libs = the<LibrariesForLibs>()
// include lombok plugin for Java projects but not Scala / Kotlin projects
if (!pluginManager.hasPlugin("kotlin") && !pluginManager.hasPlugin("scala")) {
    logger.info("Adding Lombok plugin to ${project.name}")
    apply(plugin = "io.freefair.lombok")
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation(libs.org.cometd.java.client.http.jetty)
    testImplementation(platform(libs.allure.bom))
    // Add necessary Allure dependencies to dependencies section
    testImplementation("io.qameta.allure:allure-junit5")
}
// tasks.withType<JavaCompile> {
//     options.encoding = "UTF-8"
//     options.compilerArgs.add("-parameters")
// }
