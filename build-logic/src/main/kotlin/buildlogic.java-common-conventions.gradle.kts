plugins {
    // enforcing Quarkus BOM to allign Java / Kotlin versions
    id("buildlogic.quarkus-common-conventions")
    java
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }    
}

// tasks.withType<JavaCompile> {
//     options.encoding = "UTF-8"
//     options.compilerArgs.add("-parameters")
// }
