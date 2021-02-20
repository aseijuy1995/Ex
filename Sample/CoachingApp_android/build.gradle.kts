buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(ClassPath.tools)
        classpath(ClassPath.kotlin)
        //data store - proto
        classpath(ClassPath.protobuf)
        //navigation
        classpath(ClassPath.navigation)
        //hilt
        classpath(ClassPath.hilt)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io")//rxPermissions
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}