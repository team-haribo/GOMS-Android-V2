import com.android.build.gradle.LibraryExtension
import com.goms.goms_android_v2.configureAndroidCompose
import com.goms.goms_android_v2.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidComposeConventionPlugin :Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")

                extensions.configure<LibraryExtension> {
                    configureAndroidCompose(this)
                }

                dependencies {
                    add("implementation", libs.findBundle("compose").get())
                }
            }
        }
    }
}