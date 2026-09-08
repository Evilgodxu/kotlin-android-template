package com.template.evilgodxu.update

import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * 版本检查：以 GitHub Releases 最新 tag_name 为线上版本号。
 * 仅做查询与语义化版本比较，不承担下载/安装。
 */
object AppUpdateChecker {

    /** GitHub Releases 最新版查询接口 */
    private const val LATEST_RELEASE_URL = "https://api.github.com/repos/Evilgodxu/android-template/releases/latest"

    private val json = Json { ignoreUnknownKeys = true }

    @Serializable
    private data class GitHubRelease(@SerialName("tag_name") val tagName: String? = null)

    /**
     * 拉取线上最新版本号。
     * @return 最新版本号（如 "v1.1.0"）；网络异常或响应非 200 时返回 null。
     * @WorkerThread 内部切至 IO 线程。
     */
    suspend fun fetchLatestVersion(): String? = withContext(Dispatchers.IO) {
        runCatching {
            val connection = URL(LATEST_RELEASE_URL).openConnection() as HttpURLConnection
            connection.apply {
                requestMethod = "GET"
                connectTimeout = CONNECT_TIMEOUT_MS
                readTimeout = READ_TIMEOUT_MS
                setRequestProperty("Accept", "application/vnd.github+json")
            }
            try {
                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    return@runCatching null
                }
                val body = connection.inputStream.bufferedReader().use { it.readText() }
                json.decodeFromString<GitHubRelease>(body).tagName
            } finally {
                connection.disconnect()
            }
        }.getOrNull()
    }

    /** latest 是否比 current 新；忽略前缀 v，缺省位按 0 补齐比较 */
    fun hasNewVersion(latest: String, current: String): Boolean =
        compareVersions(latest, current) > 0

    private fun compareVersions(a: String, b: String): Int {
        val aParts = a.trimStart('v').split('.').map { it.toIntOrNull() ?: 0 }
        val bParts = b.trimStart('v').split('.').map { it.toIntOrNull() ?: 0 }
        val maxLength = maxOf(aParts.size, bParts.size)
        for (index in 0 until maxLength) {
            val diff = aParts.getOrElse(index) { 0 } - bParts.getOrElse(index) { 0 }
            if (diff != 0) return diff
        }
        return 0
    }

    private const val CONNECT_TIMEOUT_MS = 10_000
    private const val READ_TIMEOUT_MS = 10_000
}