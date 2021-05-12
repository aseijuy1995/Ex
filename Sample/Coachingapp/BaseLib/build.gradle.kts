// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
		repositories {
				google()
				mavenCentral()
		}
		dependencies {
				classpath(ClassPath.gradle)
				classpath(ClassPath.kotlinGradlePlugin)
//				classpath(ClassPath.protoGradlePlugin)
		}
}

allprojects {
		repositories {
				google()
				mavenCentral()
				jcenter() // Warning: this repository is going to shut down soon
		}
}

tasks {
		val clean by registering(Delete::class) {
				delete(buildDir)
		}
}