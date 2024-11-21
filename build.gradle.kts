plugins {
    `groovy`
    `maven-publish`
    `kotlin-dsl`
}

group = "org.ndrwdn.mbgradle"
version = "0.0.8"

dependencies {
    implementation("com.github.node-gradle:gradle-node-plugin:7.1.0")
    implementation("org.apache.httpcomponents:httpclient:4.5.14")
    implementation("org.apache.commons:commons-compress:1.27.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    testImplementation("org.mockito:mockito-core:4.11.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Jar>("sourcesJar") {
    from(sourceSets.main.get().allSource)
}

publishing {
    repositories {
        mavenLocal()
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = group.toString()
            artifactId = "mountebank"
            version = version.toString()
            artifact(tasks["jar"])
        }
    }
}

repositories {
    maven {
        url = uri("https://jitpack.io")
    }
    mavenLocal()
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("mountebank") {
            id = "org.ndrwdn.mbgradle"
            implementationClass = "org.ndrwdn.mbgradle.MountebankGradlePlugin"
            version = version.toString()
        }
    }
}
