plugins {
    application
}

dependencies {
    implementation(project(":canonico"))
    implementation(libs.jaxws.api)
    implementation(libs.cxf.jaxws)
    implementation(libs.cxf.http)
    implementation(libs.asm)
}

application {
    mainClass.set("cl.technova.integracion.adapter.facturacion.AdapterFacturacionApp")
}
