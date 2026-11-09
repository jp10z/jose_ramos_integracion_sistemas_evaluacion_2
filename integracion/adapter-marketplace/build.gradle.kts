plugins {
    application
}

dependencies {
    implementation(project(":canonico"))
}

application {
    mainClass.set("cl.technova.integracion.adapter.marketplace.AdapterMarketplaceApp")
}
