plugins {
    java
}

group = "me.rtpgui"
version = "1.0.0"

repositories {
    mavenCentral()

    // Paper
    maven("https://repo.papermc.io/repository/maven-public/")

    // PlaceholderAPI (optional support)
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    // Paper API 1.21.11
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")

    // PlaceholderAPI hook (optional runtime)
    compileOnly("me.clip:placeholderapi:2.11.6")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.jar {

    // ✅ Jar name
    archiveFileName.set("RtpGUI.jar")

    manifest {
        attributes(
            "Main-Class" to "me.rtpgui.RtpGUIPlugin",
            "Implementation-Title" to "RtpGUI",
            "Implementation-Version" to version
        )
    }

    from("src/main/resources") {
        include("plugin.yml", "config.yml")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

tasks.processResources {
    from("src/main/resources") {
        include("plugin.yml", "config.yml")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}