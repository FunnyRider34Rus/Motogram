import java.util.Properties

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.secrets) apply false
    id("vkid.manifest.placeholders") version "1.1.0" apply true
}

vkidManifestPlaceholders {
    val properties = Properties()
    properties.load(file("local.properties").inputStream())
    val clientId = properties["VKIDClientID"] ?: error("")
    val clientSecret = properties["VKIDClientSecret"] ?: error("")
    init(
        clientId = clientId.toString(),
        clientSecret = clientSecret.toString(),
    )
}