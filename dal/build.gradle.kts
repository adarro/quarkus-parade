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
    testImplementation(libs.testing.assertj)
}