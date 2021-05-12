object Dependencies {

		const val kotlinStbLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.gradlePlugin}"

		const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"

		const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"

		const val material = "com.google.android.material:material:${Versions.material}"

		const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"

		const val junit = "junit:junit:${Versions.junit}"

		const val testJunit = "androidx.test.ext:junit:${Versions.testJunit}"

		const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"

		object Lifecycle {
				const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"

				const val process = "androidx.lifecycle:lifecycle-process:${Versions.lifecycle}"
		}

		object DataStore {
				const val dataStore = "androidx.datastore:datastore:${Versions.dataStore}"

				const val rxJava3 = "androidx.datastore:datastore-rxjava3:${Versions.dataStore}"

				const val core = "androidx.datastore:datastore-core:${Versions.dataStore}"

				const val preferences = "androidx.datastore:datastore-preferences:${Versions.dataStore}"

				const val preferencesRxJava3 = "androidx.datastore:datastore-preferences-rxjava3:${Versions.dataStore}"

				const val preferencesCore = "androidx.datastore:datastore-preferences-core:${Versions.dataStore}"
		}

		object Protobuf{
				const val javalite = "com.google.protobuf:protobuf-javalite:${Versions.protobuf}"
		}
}