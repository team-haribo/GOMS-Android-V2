import com.android.build.api.dsl.ApplicationExtension
import com.goms.goms_android_v2.configureKotlinAndroid
import com.goms.goms_android_v2.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("goms.android.lint")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig {
                    applicationId = "com.goms.goms_android_v2"
                    minSdk = 26
                    targetSdk = 34
                    versionCode = 26
                    versionName = "1.3.5"
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                    vectorDrawables.useSupportLibrary = true
                }

                buildFeatures.compose = true

                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = true
                        isShrinkResources = true
                        isDebuggable = false
                        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                    }
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = libs.findVersion("androidxComposeCompiler").get().toString()
                }

                dependencies {
                    add("implementation", libs.findBundle("kotlinx-coroutines").get())
                    add("implementation", libs.findBundle("compose").get())
                }
            }
        }
    }
}
