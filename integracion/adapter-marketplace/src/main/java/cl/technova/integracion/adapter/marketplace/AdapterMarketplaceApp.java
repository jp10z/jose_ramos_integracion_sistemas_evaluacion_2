package cl.technova.integracion.adapter.marketplace;

import cl.technova.integracion.comun.Broker;
import cl.technova.integracion.comun.Json;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.jms.Destination;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class AdapterMarketplaceApp {
    private static final Logger LOG = LoggerFactory.getLogger(AdapterMarketplaceApp.class);

    public static void main(String[] args) throws Exception {
        String baseUrl = args.length > 0 ? args[0] : "http://localhost:8091";
        MarketplaceClient marketplace = new MarketplaceClient(baseUrl);

        JsonArray ventas = marketplace.obtenerVentasDelDia();
        LOG.info("Ventas del día obtenidas desde {}: {}", baseUrl, ventas.size());

        int enviadas = 0;
        int omitidas = 0;
        try (JMSContext ctx = Broker.crearContexto()) {
            Destination canal = Broker.destino("mkpPedidos");
            JMSProducer producer = ctx.createProducer();

            for (JsonElement elemento : ventas) {
                JsonObject venta = elemento.getAsJsonObject();
                String id = venta.get("id").getAsString();

                Optional<JsonObject> envio = marketplace.obtenerCostoEnvio(id);
                if (envio.isEmpty()) {
                    LOG.warn("Venta {} sin costo de envío (404), se omite", id);
                    omitidas++;
                    continue;
                }
                JsonObject datosEnvio = new JsonObject();
                datosEnvio.add("costoEnvio", envio.get().get("costoEnvio"));
                datosEnvio.add("moneda", envio.get().get("moneda"));
                venta.add("envio", datosEnvio);

                String json = Json.GSON.toJson(venta);
                TextMessage mensaje = ctx.createTextMessage(json);
                mensaje.setStringProperty("origen", "MARKETPLACE");
                mensaje.setStringProperty("idVenta", id);
                producer.send(canal, mensaje);
                enviadas++;

                LOG.info("Venta {} enriquecida y enviada a {}:\n{}", id, canal, json);
            }
        }
        LOG.info("Fin: {} enviadas, {} omitidas", enviadas, omitidas);
    }
}
