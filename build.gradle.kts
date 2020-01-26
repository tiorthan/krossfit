plugins {
    kotlin("jvm") version "1.3.61"
    id("groovy")
}

group = "de.wolkenfestung"
version = "1.0.0"

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")

    compile("org.codehaus.groovy:groovy-all:2.5.8")
    testCompile(platform("org.spockframework:spock-bom:2.0-M1-groovy-2.5"))
    testCompile("org.spockframework:spock-core")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    test {
        useJUnitPlatform()
    }

    compileGroovy {
        classpath += files(compileKotlin.get().destinationDir)
    }

    compileTestGroovy {
        classpath += files(compileTestKotlin.get().destinationDir)
    }
}