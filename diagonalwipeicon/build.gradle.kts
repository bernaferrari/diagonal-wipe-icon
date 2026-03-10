import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.mavenPublish)
}

kotlin {
    androidLibrary {
        namespace = "com.bernaferrari.diagonalwipeicon.lib"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    js(IR) {
        compilerOptions {
            sourceMap.set(false)
        }
        browser()
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "DiagonalWipeIcon"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)
    signAllPublications()

    pom {
        name.set("Diagonal Wipe Icon")
        description.set("Diagonal wipe icon transition component for Compose Multiplatform.")
        inceptionYear.set("2026")
        url.set("https://github.com/bernaferrari/diagonal-wipe-icon/")
        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/license/mit")
                distribution.set("repo")
            }
        }
        developers {
            developer {
                id.set("bernaferrari")
                name.set("Bernardo Ferrari")
                url.set("https://github.com/bernaferrari/")
            }
        }
        scm {
            url.set("https://github.com/bernaferrari/diagonal-wipe-icon/")
            connection.set("scm:git:git://github.com/bernaferrari/diagonal-wipe-icon.git")
            developerConnection.set("scm:git:ssh://git@github.com/bernaferrari/diagonal-wipe-icon.git")
        }
    }
}
