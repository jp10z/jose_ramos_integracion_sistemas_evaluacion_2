subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }

    plugins.withId("application") {
        tasks.named<CreateStartScripts>("startScripts") {
            doLast {
                windowsScript.writeText(
                    windowsScript.readText().replace(Regex("(?m)^set CLASSPATH=.*$")) {
                        "set CLASSPATH=%APP_HOME%\\lib\\*"
                    }
                )
            }
        }
    }
}
