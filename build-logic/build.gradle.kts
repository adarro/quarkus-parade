/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    // Support convention plugins written in Kotlin. Convention plugins are build scripts in 'src/main' that automatically become available as plugins in the main build.
    `kotlin-dsl`
}

repositories {
    // Use the plugin portal to apply community plugins in convention plugins.
    gradlePluginPortal()
    mavenCentral()
    mavenLocal()
}

dependencies {

    implementation(libs.quarkus.gradle.plugin)
    implementation(libs.kordamp.jandex.gradle.plugin)
// kotlin plugin versioning needs to match by quarkus bom (quarkus-kotlin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.kotlin.allopen.plugin)
    implementation(libs.freefair.lombok.plugin)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
