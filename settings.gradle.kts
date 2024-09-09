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
        maven(url = "https://jitpack.io" )
    }
}

rootProject.name = "GOMS-Android-V2"
include(":app")

include(":core")
include(":core:analytics")
include(":core:common")
include(":core:data")
include(":core:datastore")
include(":core:design-system")
include(":core:domain")
include(":core:model")
include(":core:network")
include(":core:ui")

include(":feature")
include(":feature:find-password")
include(":feature:login")
include(":feature:main")
include(":feature:qrcode")
include(":feature:re-password")
include(":feature:setting")
include(":feature:sign-up")
