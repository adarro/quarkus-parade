plugins {
    id("buildlogic.quarkus-common-conventions")
}

description = "CometD Messaging"

val cometdVersion = "7.0.0"
val jettyVersion = "12.0.12"
dependencies {
    implementation("io.quarkus:quarkus-undertow")
    implementation("org.cometd.java:cometd-java-server:$cometdVersion")
    implementation("org.cometd.java:cometd-java-server-common:$cometdVersion")
    implementation("org.cometd.java:cometd-java-server-websocket-javax:$cometdVersion")
    implementation("org.cometd.java:cometd-java-api-server:$cometdVersion")
    implementation("org.eclipse.jetty:jetty-util-ajax:${jettyVersion}")
    testImplementation("io.quarkus:quarkus-junit5-mockito:3.13.0")

    // from maven cometd archetype
//                compile "org.cometd.javascript:cometd-javascript-dojo:${cometd-version}"
//        compile "org.eclipse.jetty:jetty-jmx:${jetty-version}"
//        compile "org.eclipse.jetty:jetty-servlets:${jetty-version}"
//        compile "org.eclipse.jetty:jetty-util:${jetty-version}"
//        compile "org.eclipse.jetty:jetty-util-ajax:${jetty-version}"
//        compile "org.slf4j:slf4j-simple:2.0.13"

    //    implementation("io.quarkus:quarkus-jdbc-postgresql")
//    implementation("io.quarkus:quarkus-hibernate-reactive-rest-data-panache")
//    implementation("io.quarkus:quarkus-reactive-pg-client")
//    implementation("io.quarkus:quarkus-resteasy-reactive")
//    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    testImplementation(libs.testing.assertj)
}