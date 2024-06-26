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

include(":feature")
include(":core:data")
include(":core:domain")
include(":core:design-system")
include(":core:model")
include(":core:network")
include(":core:datastore")
include(":core:ui")
include(":core:common")

include(":feature:login")
include(":feature:sign-up")
include(":feature:main")
include(":feature:qrcode")
include(":feature:setting")
include(":feature:re-password")
include(":feature:find-password")
