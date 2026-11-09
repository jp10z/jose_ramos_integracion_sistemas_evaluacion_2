package cl.technova.integracion.comun;

import cl.technova.integracion.canonico.PedidoCanonico;
import cl.technova.integracion.canonico.Traductor;
import jakarta.jms.Destination;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TraductorEndpoint implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(TraductorEndpoint.class);

    private final JMSContext ctx;

    public TraductorEndpoint(String nombre, String canalEntrada, String canalSalida, Traductor traductor) throws Exception {
        this.ctx = Broker.crearContexto();
        Destination entrada = Broker.destino(canalEntrada);
        Destination salida = Broker.destino(canalSalida);
        JMSProducer producer = ctx.createProducer();
        JMSConsumer consumer = ctx.createConsumer(entrada);

        consumer.setMessageListener(msg -> {
            try {
                String original = msg.getBody(String.class);
                LOG.info("[{}] Mensaje recibido de {}:\n{}", nombre, entrada, original);

                PedidoCanonico canonico = traductor.traducir(original);
                String json = Json.GSON.toJson(canonico);

                TextMessage mensaje = ctx.createTextMessage(json);
                mensaje.setStringProperty("origen", canonico.getOrigen().name());
                mensaje.setStringProperty("idPedido", canonico.getIdPedido());
                producer.send(salida, mensaje);

                LOG.info("[{}] Pedido {} publicado en {}:\n{}", nombre, canonico.getIdPedido(), salida, json);
            } catch (Exception e) {
                LOG.error("[{}] Error al traducir el mensaje", nombre, e);
                throw new RuntimeException(e);
            }
        });
        LOG.info("[{}] Escuchando {} -> publicando en {}", nombre, entrada, salida);
    }

    @Override
    public void close() {
        ctx.close();
    }
}
