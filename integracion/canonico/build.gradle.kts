plugins {
    `java-library`
}

dependencies {
    api(libs.jakarta.jms.api)
    api(libs.gson)
    api(libs.bundles.logging)
    runtimeOnly(libs.artemis.jakarta.client)
}
