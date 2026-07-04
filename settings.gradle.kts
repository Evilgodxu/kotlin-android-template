pluginManagement {
    repositories {
        // 插件优先走腾讯云镜像，避免 Gradle Plugin Portal 在国内网络下不可达
        maven { url = uri("https://mirrors.cloud.tencent.com/nexus/repository/gradle-plugin/") }
        maven { url = uri("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/") }
        maven { url = uri("https://mirrors.cloud.tencent.com/nexus/repository/google/") }
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}
plugins {
    // Gradle 8.8+ 已内置 Foojay Toolchain Resolver，无需额外声明
    id("de.fayard.refreshVersions") version "0.60.6"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // 依赖优先走腾讯云镜像，据称对新版 AndroidX 包同步更快
        maven { url = uri("https://mirrors.cloud.tencent.com/nexus/repository/google/") }
        maven { url = uri("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/") }
        google()
        mavenCentral()
    }
}

rootProject.name = "Template"
include(":app")
