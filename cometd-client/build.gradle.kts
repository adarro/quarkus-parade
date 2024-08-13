plugins {
    java
    id("buildlogic.quarkus-common-conventions")
    id("buildlogic.cometd-client-conventions")
}

description = "CometD Client for Bayeux Protocol"

dependencies {

//    implementation("org.cometd.java:cometd-java-client:8.0.2")
//    implementation("org.cometd.java:cometd-java-client-http-jakarta:8.0.2")
//    implementation("org.cometd.java:cometd-java-common:8.0.2")

//    api(libs.org.cometd.java.cometd.java.api.server)
//    api(libs.org.cometd.javascript.cometd.javascript.common)
//    api(libs.org.cometd.java.cometd.java.server.http.jakarta)
//    api(libs.org.cometd.java.cometd.java.server.websocket.jakarta)
//    api(libs.org.eclipse.jetty.jetty.jmx)
//    api(libs.org.eclipse.jetty.ee10.jetty.ee10.servlets)
//    api(libs.org.eclipse.jetty.jetty.slf4j.impl)
//    api(libs.org.eclipse.jetty.jetty.util)

//    compileOnly(libs.jakarta.servlet.jakarta.servlet.api)

    // https://mvnrepository.com/artifact/org.jetbrains/annotations
    compileOnly("org.jetbrains:annotations:24.1.0")
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}
