plugins {
    java
}

group = "me.neondeex"
version = providers.gradleProperty("releaseVersion").orElse("1.0-SNAPSHOT").get()

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://storehouse.okaeri.eu/repository/maven-public/")
    maven("https://repo.xenondevs.xyz/releases")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://mvn.wesjd.net/")
    maven("https://jitpack.io")
    maven("https://maven.enginehub.org/repo/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.2.build.43-alpha")

    // --- Shaded (van dentro del JAR) ---
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.0")  // disponible para todos los plugins Kotlin que dependan de NeoLib
    implementation("eu.okaeri:okaeri-configs-yaml-bukkit:5.0.5")
    implementation("eu.okaeri:okaeri-configs-serdes-bukkit:5.0.5")
    implementation("xyz.xenondevs.invui:invui:2.2.0")
    implementation("com.zaxxer:HikariCP:7.0.2")
    implementation("com.mysql:mysql-connector-j:8.3.0")
    implementation("org.xerial:sqlite-jdbc:3.45.3.0")
    implementation("com.h2database:h2:2.2.224")
    implementation("org.mongodb:mongodb-driver-sync:5.1.4") {
        exclude(group = "org.slf4j")
    }
    implementation("redis.clients:jedis:5.1.3") {
        exclude(group = "org.slf4j")
    }
    implementation("net.wesjd:anvilgui:1.10.12-SNAPSHOT")
    implementation("com.github.Tofaa2.EntityLib:spigot:2.4.11")
    compileOnly("dev.jorel:commandapi-paper-core:11.2.0")
    implementation("com.github.retrooper:packetevents-spigot:2.12.1")
    implementation("com.github.cryptomorin:XSeries:13.6.0")
    implementation("de.tr7zw:item-nbt-api:2.15.7")

    // --- CompileOnly (NO van en el JAR, son plugins externos) ---
    // VaultAPI trae bukkit 1.13 como transitivo - lo excluimos, Paper ya lo provee
    compileOnly("com.github.MilkBowl:VaultAPI:1.7") {
        exclude(group = "org.bukkit", module = "bukkit")
    }
    // WorldGuard tiene constraints estrictos de guava/gson/fastutil que chocan con Paper
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.12") {
        exclude(group = "com.google.guava", module = "guava")
        exclude(group = "com.google.code.gson", module = "gson")
        exclude(group = "it.unimi.dsi", module = "fastutil")
    }
    compileOnly("me.clip:placeholderapi:2.11.6")
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(25)
    }

    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
        exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
    }

    processResources {
        filesMatching("plugin.yml") {
            expand(
                "version" to project.version,
                "name" to project.name
            )
        }
    }
}
