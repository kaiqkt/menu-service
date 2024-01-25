import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val mainPkgAndClass = "com.anon.menu.ApplicationKt"

val excludePackages: List<String> by extra {
    listOf()
}

@Suppress("UNCHECKED_CAST")
fun ignorePackagesForReport(jacocoBase: JacocoReportBase) {
    jacocoBase.classDirectories.setFrom(
        sourceSets.main.get().output.asFileTree.matching {
            exclude(jacocoBase.project.extra.get("excludePackages") as List<String>)
        }
    )
}

group = "com.anon"
version = "1.0.0"
description = "Spring boot application"

repositories {
    mavenCentral()
}

plugins {
    application
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.20"
    kotlin("plugin.jpa") version "1.9.20"
    kotlin("plugin.noarg") version "1.9.20"
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.openapi.generator") version "5.1.1"
    id("jacoco")
}

sourceSets {
    create("componentTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }

    create("archTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

val componentTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.implementation.get())
}

val archTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.implementation.get())
}

configurations["componentTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())
configurations["archTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

dependencies {
    //kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    //swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    //spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    //database
    implementation("org.postgresql:postgresql:42.6.0")

    //utils
    implementation("io.azam.ulidj:ulidj:1.0.4")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.0")
    implementation("javax.servlet:servlet-api:2.5")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("javax.validation:validation-api:2.0.1.Final")

    //jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

//    testImplementation("com.ninja-squad:springmockk:3.1.1")
//    testImplementation("org.mock-server:mockserver-netty:5.11.2")
//    testImplementation("org.springframework.boot:spring-boot-starter-test")

//    componentTestImplementation("org.mock-server:mockserver-netty:5.11.2")
//    componentTestImplementation("org.springframework.boot:spring-boot-starter-test")
//    componentTestImplementation("com.h2database:h2:2.2.220")
//    componentTestImplementation("it.ozimov:embedded-redis:0.7.3")
//    componentTestImplementation("io.rest-assured:kotlin-extensions:4.3.0")
//    componentTestImplementation(sourceSets["test"].output)
//
//    archTestImplementation("org.springframework.boot:spring-boot-starter-test")
//    archTestImplementation("com.tngtech.archunit:archunit-junit5-api:1.1.0")
//    archTestImplementation("com.tngtech.archunit:archunit-junit5-engine:1.1.0")
}

application {
    mainClass.set(mainPkgAndClass)
}

jacoco {
    toolVersion = "0.8.11"
    reportsDirectory.set(layout.buildDirectory.dir("jacoco"))
}

openApiGenerate {
    generatorName.set("kotlin-spring")
    skipValidateSpec.set(true)
    inputSpec.set("$rootDir/src/main/resources/static/api-docs.yml")
    outputDir.set("${layout.buildDirectory.get()}/generated/")
    configFile.set("$rootDir/src/main/resources/static/api-config.json")
}

java.sourceSets["main"].java.srcDir("${layout.buildDirectory.get()}/generated/src/main/kotlin")

tasks.withType<KotlinCompile>().all {
    java.sourceCompatibility = JavaVersion.VERSION_17
    java.targetCompatibility = JavaVersion.VERSION_17

    kotlinOptions {
        jvmTarget = "17"
    }
}

tasks.withType<CreateStartScripts> { mainClass.set(mainPkgAndClass) }

tasks.jar {
    enabled = false
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes("Main-Class" to mainPkgAndClass)
        attributes("Package-Version" to archiveVersion)
    }

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    from(sourceSets.main.get().output)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val componentTestTask = tasks.create("componentTest", Test::class) {
    description = "Run the component tests."
    group = "verification"

    testClassesDirs = sourceSets["componentTest"].output.classesDirs
    classpath = sourceSets["componentTest"].runtimeClasspath

    useJUnitPlatform()
}

val archTestTask = tasks.create("archTest", Test::class) {
    description = "Run the architeture tests."
    group = "verification"

    testClassesDirs = sourceSets["archTest"].output.classesDirs
    classpath = sourceSets["archTest"].runtimeClasspath

    useJUnitPlatform()
}

tasks.withType<JacocoReport> {
    reports {
        xml.required
        html.required
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
    ignorePackagesForReport(this)
}

tasks.withType<JacocoCoverageVerification> {
    violationRules {
        rule {
            limit {
                minimum = "0.0".toBigDecimal()
                counter = "LINE"
            }
            limit {
                minimum = "0.0".toBigDecimal()
                counter = "BRANCH"
            }
        }
    }
    ignorePackagesForReport(this)
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport, tasks.jacocoTestCoverageVerification, componentTestTask, archTestTask)
}