import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "goms.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidHilt") {
            id = "goms.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidLint") {
            id = "goms.android.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }

        register("androidCore") {
            id = "goms.android.core"
            implementationClass = "AndroidCoreConventionPlugin"
        }

        register("androidCompose") {
            id = "goms.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }

        register("jvmLibrary") {
            id = "goms.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }

        register("androidFeature") {
            id = "goms.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
    }
}