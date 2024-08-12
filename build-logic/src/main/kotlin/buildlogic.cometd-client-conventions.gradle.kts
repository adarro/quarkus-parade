import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("buildlogic.java-common-conventions") apply false
}
val libs = the<LibrariesForLibs>()
/*
Applies to: CometD Client creation.
This convention should be applied to any project that uses CometD Client and is implemented as a Quarkus component.
Therefore, either directly or indirectly, the project should also apply either
 a.) 'buildlogic.quarkus-component-conventions' if used as a library
 b.) 'buildlogic.quarkus-common-conventions' if used as an application.
 */

dependencies {
    implementation("io.quarkus:quarkus-websockets") {
        because("Required for WebSockets and returned by ContainerProvider.getWebSocketContainer()")
    }

    implementation(libs.org.cometd.java.client.http.jetty) {
        because("CometD Long-polling client, may use OKHttp instead for Android")
    }
    implementation(libs.org.cometd.java.client.websocket.jakarta) {
        because("CometD WebSocket Client, could also use Jetty or OKHttp")
    }
    implementation(libs.org.eclipse.jetty.jetty.util.ajax) {
        because("Used internally by CometD")
    }
}
