import com.goms.goms_android_v2.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
                apply("goms.android.lint")
                apply("com.google.devtools.ksp")
            }
            configureKotlinJvm()
        }
    }
}