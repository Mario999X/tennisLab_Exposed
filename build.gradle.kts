import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    // Para generar modelos de DataFrames
    id("org.jetbrains.kotlinx.dataframe") version "0.8.1"
    // Plugin para serializar
    kotlin("plugin.serialization") version "1.7.10"
    //Dokka Documentación Kotlin
    id("org.jetbrains.dokka") version "1.7.20"
    application
}

group = "es.mendoza.resa"
version = "1"

repositories {
    mavenCentral()
}

dependencies {
    //Test
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.2")
    // Exposed
    implementation("org.jetbrains.exposed:exposed-core:0.39.2")
    implementation("org.jetbrains.exposed:exposed-dao:0.39.2")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.39.2")
    implementation("org.jetbrains.exposed:exposed-java-time:0.39.2")
    // Base de Datos H2 Driver JDBC
    implementation("com.h2database:h2:2.1.214")
    // Si quiero usar DataTime de Jetbrains Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
    // Kotlin's serialization JSON
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    // Para hacer logs
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.0")
    implementation("ch.qos.logback:logback-classic:1.4.1")
    // Para manejar un pool de conexions mega rápido con HikariCP (no es obligatorio)
    implementation("com.zaxxer:HikariCP:5.0.1")
    // gson
    implementation("com.google.code.gson:gson:2.10")
    //Dokka Documentación Kotlin
    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.7.20")
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    val fatJar = register<Jar>("fatJar") {
        dependsOn.addAll(
            listOf(
                "compileJava",
                "compileKotlin",
                "processResources"
            )
        ) // We need this for Gradle optimization to work
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest { attributes(mapOf("Main-Class" to application.mainClass)) } // Provided we set it up in the application plugin configuration
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) } +
                sourcesMain.output
        from(contents)
    }
    build {
        dependsOn(fatJar) // Trigger fat jar creation during build
    }
}
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

// Data Schema generator
// Make IDE aware of the generated code:
kotlin.sourceSets.getByName("main").kotlin.srcDir("build/generated/ksp/main/kotlin/")