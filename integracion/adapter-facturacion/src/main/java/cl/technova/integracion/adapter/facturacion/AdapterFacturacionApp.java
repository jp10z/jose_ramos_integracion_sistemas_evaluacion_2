package cl.technova.integracion.adapter.facturacion;

import cl.technova.integracion.adapter.facturacion.soap.ServicioFacturacion;
import cl.technova.integracion.canonico.PedidoCanonico;
import cl.technova.integracion.comun.Broker;
import cl.technova.integracion.comun.Consola;
import cl.technova.integracion.comun.Json;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.Topic;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdapterFacturacionApp {
    private static final Logger LOG = LoggerFactory.getLogger(AdapterFacturacionApp.class);

    private static final String CLIENT_ID = "jra-adapter-facturacion";
    private static final String SUSCRIPCION = "jra-facturacion";

    public static void main(String[] args) throws Exception {
        String url = args.length > 0 ? args[0] : "http://localhost:8090/soap/facturacion";

        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setAddress(url);
        ServicioFacturacion facturacion = factory.create(ServicioFacturacion.class);

        JMSContext ctx = Broker.crearContexto();
        ctx.setClientID(CLIENT_ID);
        Topic topico = (Topic) Broker.destino("pedidos");
        JMSConsumer consumer = ctx.createDurableConsumer(topico, SUSCRIPCION);

        consumer.setMessageListener(msg -> {
            try {
                String json = msg.getBody(String.class);
                PedidoCanonico pedido = Json.GSON.fromJson(json, PedidoCanonico.class);
                LOG.info("Pedido recibido {} (origen {}):\n{}", pedido.getIdPedido(), pedido.getOrigen(), json);

                String cliente = pedido.getCliente().getNombreCompleto();
                String rut = pedido.getCliente().getRut();
                long total = pedido.getTotales().getTotal();

                LOG.info("Invocando SOAP generarBoleta(cliente='{}', rut='{}', total={})", cliente, rut, total);
                String boletaXml = facturacion.generarBoleta(cliente, rut, total);
                LOG.info("Respuesta SOAP para pedido {}:\n{}", pedido.getIdPedido(), boletaXml);
            } catch (Exception e) {
                LOG.error("Error al facturar el pedido", e);
                throw new RuntimeException(e);
            }
        });

        LOG.info("Adapter Facturación suscrito a {} (suscripción durable '{}'), SOAP en {}", topico, SUSCRIPCION, url);
        Consola.esperarHastaCtrlC(ctx);
    }
}
