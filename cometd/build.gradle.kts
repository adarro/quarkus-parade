import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin.Companion.isUseK2
import org.kordamp.gradle.plugin.jandex.internal.JandexExtensionImpl
import org.kordamp.gradle.plugin.jandex.tasks.JandexTask

plugins {
    id("buildlogic.quarkus-common-conventions")
    id ("org.kordamp.gradle.jandex") version "2.0.0"
}

description = "CometD Messaging"
repositories {
    mavenCentral()
    mavenLocal()
}

val cometdVersion = "8.0.2"
val jettyVersion = "12.0.12"
dependencies {

    implementation("io.undertow:undertow-core:2.3.15.Final")
    implementation("io.quarkus:quarkus-undertow")
//    implementation("org.cometd.java:cometd-java-server:$cometdVersion")
//    implementation("org.cometd.java:cometd-java-server-common:$cometdVersion")
////    implementation("org.cometd.java:cometd-java-server-websocket-javax:$cometdVersion")
//    // https://mvnrepository.com/artifact/org.cometd.java/cometd-java-client-http-jetty
//    implementation("org.cometd.java:cometd-java-client-http-jetty:$cometdVersion")


    implementation("org.eclipse.jetty:jetty-io:$jettyVersion") {
        because("transient httpclient has multiple CVE issues")
    }
// https://mvnrepository.com/artifact/org.cometd.java/cometd-java-api-server
    implementation("org.cometd.java:cometd-java-api-server:$cometdVersion")

//    implementation("org.cometd.java:cometd-java-api-server:$cometdVersion")
    implementation("org.eclipse.jetty:jetty-util-ajax:${jettyVersion}")
    testImplementation("org.eclipse.jetty:jetty-client:$jettyVersion")
    testImplementation("io.quarkus:quarkus-junit5-mockito:3.13.0")
// https://mvnrepository.com/artifact/org.cometd.java/cometd-java-client-websocket-okhttp
    testImplementation("org.cometd.java:cometd-java-client-websocket-okhttp:$cometdVersion")


//    implementation("org.cometd.java:cometd-java-examples-embedded:${cometdVersion}")
    implementation("org.cometd.java:cometd-java-oort-jakarta:${cometdVersion}")
    implementation("org.cometd.java:cometd-java-server-common:${cometdVersion}")
    implementation("org.cometd.java:cometd-java-server-websocket-jakarta:${cometdVersion}")
//    implementation("org.cometd.javascript:cometd-javascript-examples-es6:${cometdVersion}@war")
//    implementation("org.cometd.javascript:cometd-javascript-examples-jquery:${cometdVersion}@war")
//    implementation("org.cometd.javascript:cometd-javascript-jquery:${cometdVersion}@war")
    implementation("org.eclipse.jetty:jetty-client:$jettyVersion")
    implementation("org.eclipse.jetty:jetty-jmx:$jettyVersion")
    implementation("org.eclipse.jetty:jetty-util:$jettyVersion")
    implementation("org.eclipse.jetty:jetty-util-ajax:$jettyVersion")
    implementation("org.eclipse.jetty.ee10:jetty-ee10-servlets:$jettyVersion")
    implementation("org.eclipse.jetty.ee10.websocket:jetty-ee10-websocket-jakarta-client:$jettyVersion")
    // https://mvnrepository.com/artifact/org.cometd.java/cometd-java-annotation-server
    implementation("org.cometd.java:cometd-java-annotation-server:$cometdVersion")
    implementation("org.cometd.java:cometd-java-annotation-common:$cometdVersion")

//    providedCompile("jakarta.servlet:jakarta.servlet-api")
//    providedCompile("jakarta.websocket:jakarta.websocket-api")
//    providedCompile("jakarta.websocket:jakarta.websocket-client-api")
    runtimeOnly("org.eclipse.jetty:jetty-slf4j-impl:$jettyVersion")
    testImplementation("org.cometd.java:cometd-java-client-websocket-jetty:${cometdVersion}")
    testImplementation("org.eclipse.jetty.ee10:jetty-ee10-webapp:$jettyVersion")
    testImplementation("org.eclipse.jetty.ee10.websocket:jetty-ee10-websocket-jakarta-server:$jettyVersion")


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
tasks.withType(JandexTask::class.java) {
    logger.warn("filtering ${sources.count()} ${this.sources}")

    this.sources.filter {
        logger.warn("filtering ${it.name}")
        it.name.endsWith(".java") }
}