pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GOMS-Android-V2"
include(":app")

include(":core")

include(":feature")
include(":core:data")
include(":core:domain")
include(":core:design-system")
include(":core:model")
include(":core:data:repository")
include(":feature:login")
include(":feature:sign-up")
