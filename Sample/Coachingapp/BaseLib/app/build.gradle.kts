//import com.google.protobuf.gradle.builtins
//import com.google.protobuf.gradle.generateProtoTasks
//import com.google.protobuf.gradle.id
//import com.google.protobuf.gradle.protoc

plugins {
		id(Plugins.library)
		id(Plugins.kotlinAndroid)
//		id(Plugins.protobuf)
}

android {
		compileSdkVersion(Android.compileSdk)
		buildToolsVersion(Android.buildTools)

		defaultConfig {
				minSdkVersion(Android.minSdk)
				targetSdkVersion(Android.targetSdk)
				versionCode = Android.versionCode
				versionName = Android.versionName

				testInstrumentationRunner = Android.runner
		}

		buildTypes {
				getByName(Android.release) {
						isMinifyEnabled = false
						proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
				}
		}
		compileOptions {
				sourceCompatibility = JavaVersion.VERSION_1_8
				targetCompatibility = JavaVersion.VERSION_1_8
		}
		kotlinOptions {
				jvmTarget = Android.jvmTarget
		}
}

dependencies {
		implementation(Dependencies.kotlinStbLib)
		implementation(Dependencies.coreKtx)
		implementation(Dependencies.appcompat)
		implementation(Dependencies.material)
		implementation(Dependencies.material)
		implementation(Dependencies.constraintlayout)
		testImplementation(Dependencies.junit)
		androidTestImplementation(Dependencies.testJunit)
		androidTestImplementation(Dependencies.espressoCore)
		//Lifecycle
		implementation(Dependencies.Lifecycle.commonJava8)
		implementation(Dependencies.Lifecycle.process)
		//DataStore
		implementation(Dependencies.DataStore.dataStore)
		implementation(Dependencies.DataStore.rxJava3)
		implementation(Dependencies.DataStore.core)
		implementation(Dependencies.DataStore.preferences)
		implementation(Dependencies.DataStore.preferencesRxJava3)
		implementation(Dependencies.DataStore.preferencesCore)
		implementation(Dependencies.DataStore.preferencesCore)
		//Protobuf
//		implementation(Dependencies.Protobuf.javalite)
}

//proto
// Generates the java Protobuf-lite code for the Protobufs in this project. See
// https://github.com/google/protobuf-gradle-plugin#customizing-protobuf-compilation
// for more information.
//protobuf.protobuf.run {
//		protoc {
//				artifact = "com.google.protobuf:protoc:${Versions.protobuf}"
//		}
//		generateProtoTasks {
//				all().forEach {
//						it.builtins {
//								java {
//										id("lite")
//								}
//						}
//				}
//		}
//}