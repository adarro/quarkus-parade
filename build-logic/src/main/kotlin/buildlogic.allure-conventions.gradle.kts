// configures AspectJ for the project, needed for Allure reporting
// Define the version of AspectJ
val aspectJVersion = "1.9.21"

// Define configuration for AspectJ agent
val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

dependencies {
    // Add aspectjweaver dependency
    agent("org.aspectj:aspectjweaver:$aspectJVersion")
}

// Configure javaagent for test execution
tasks.test {
    jvmArgs =
        listOf(
            "-javaagent:${agent.singleFile}",
        )
}
