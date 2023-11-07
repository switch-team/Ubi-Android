pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://devrepo.kakao.com/nexus/repository/kakaomap-releases/")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://devrepo.kakao.com/nexus/repository/kakaomap-releases/")
    }


}


rootProject.name = "Ubi"
include(":app")
 