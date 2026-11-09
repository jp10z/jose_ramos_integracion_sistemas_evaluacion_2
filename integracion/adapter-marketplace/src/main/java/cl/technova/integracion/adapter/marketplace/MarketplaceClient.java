package cl.technova.integracion.adapter.marketplace;

import cl.technova.integracion.comun.Json;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class MarketplaceClient {
    private final HttpClient http = HttpClient.newHttpClient();
    private final String baseUrl;

    public MarketplaceClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public JsonArray obtenerVentasDelDia() throws IOException, InterruptedException {
        HttpResponse<String> resp = get("/orders/today");
        if (resp.statusCode() != 200) {
            throw new IOException("Marketplace respondió HTTP " + resp.statusCode());
        }
        return Json.GSON.fromJson(resp.body(), JsonArray.class);
    }

    public Optional<JsonObject> obtenerCostoEnvio(String idVenta) throws IOException, InterruptedException {
        HttpResponse<String> resp = get("/orders/" + idVenta + "/shipping-cost");
        if (resp.statusCode() == 404) return Optional.empty();
        if (resp.statusCode() != 200) {
            throw new IOException("shipping-cost respondió HTTP " + resp.statusCode());
        }
        return Optional.of(Json.GSON.fromJson(resp.body(), JsonObject.class));
    }

    private HttpResponse<String> get(String ruta) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder(URI.create(baseUrl + ruta))
                .header("Accept", "application/json")
                .GET()
                .build();
        return http.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    }
}
