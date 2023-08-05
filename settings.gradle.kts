import java.net.URI

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google()
        mavenCentral()
        mavenLocal {
            content {
                includeGroup("io.github.libxposed")
            }
        }
    }
    versionCatalogs {
        create("libs")
    }
}

sourceControl {
    gitRepository(URI("https://github.com/libxposed/api.git")) {
        producesModule("io.github.libxposed:api")
    }
    gitRepository(URI("https://github.com/libxposed/service.git")) {
        producesModule("io.github.libxposed:service")
    }

}

include(":FingerprintEnabler")
