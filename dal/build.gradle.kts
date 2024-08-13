plugins {
    id("buildlogic.quarkus-common-conventions")
}

description = "Data Entities"

dependencies {
    //    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("io.quarkus:quarkus-hibernate-reactive-rest-data-panache")
    implementation("io.quarkus:quarkus-reactive-pg-client")
    implementation("io.quarkus:quarkus-resteasy-reactive")
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    // lightweight alternative to hibernate-reactive-panache
    implementation("org.jetbrains.xodus:xodus-openAPI:2.0.1")
    implementation(libs.jetbrains.xodus.xodus.openAPI)
    testImplementation(libs.testing.assertj)
}
