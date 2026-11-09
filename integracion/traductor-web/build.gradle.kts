plugins {
    application
}

dependencies {
    implementation(project(":canonico"))
    implementation(libs.jaxb.api)
    runtimeOnly(libs.jaxb.runtime)
}

application {
    mainClass.set("cl.technova.integracion.traductor.web.TraductorWebApp")
}
