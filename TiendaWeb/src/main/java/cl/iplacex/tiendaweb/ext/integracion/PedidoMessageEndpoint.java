package cl.iplacex.tiendaweb.ext.integracion;

import cl.iplacex.tiendaweb.ext.carrito.domain.LineaPedidoImpl;
import cl.iplacex.tiendaweb.ext.carrito.domain.Pedido;
import cl.iplacex.tiendaweb.ext.carrito.event.PedidoCompletadoEvent;
import jakarta.annotation.PostConstruct;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.JMSContext;
import jakarta.jms.TextMessage;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.StringWriter;
import java.util.Hashtable;

@Component
public class PedidoMessageEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(PedidoMessageEndpoint.class);

    @Value("${technova.mensajeria.url}")
    private String brokerUrl;
    @Value("${technova.mensajeria.usuario}")
    private String usuario;
    @Value("${technova.mensajeria.password}")
    private String password;
    @Value("${technova.mensajeria.canal}")
    private String canal;

    private ConnectionFactory connectionFactory;
    private Destination destino;
    private JAXBContext jaxb;

    @PostConstruct
    void inicializar() throws Exception {
        Hashtable<String, Object> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.artemis.jndi.ActiveMQInitialContextFactory");
        env.put("connectionFactory.ConnectionFactory", brokerUrl);
        env.put("queue.webPedidos", canal);
        InitialContext jndi = new InitialContext(env);
        try {
            connectionFactory = (ConnectionFactory) jndi.lookup("ConnectionFactory");
            destino = (Destination) jndi.lookup("webPedidos");
        } finally {
            jndi.close();
        }
        jaxb = JAXBContext.newInstance(Pedido.class, LineaPedidoImpl.class);
        LOG.info("Message Endpoint listo: pedidos se enviarán a {} en {}", canal, brokerUrl);
    }

    @EventListener
    public void enviarPedido(PedidoCompletadoEvent event) {
        try {
            Marshaller marshaller = jaxb.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            marshaller.marshal(event.getPedido(), writer);
            String xml = writer.toString();

            try (JMSContext ctx = connectionFactory.createContext(usuario, password)) {
                TextMessage mensaje = ctx.createTextMessage(xml);
                mensaje.setStringProperty("origen", "TIENDA_WEB");
                ctx.createProducer().send(destino, mensaje);
            }
            LOG.info("Pedido enviado a la cola {}:\n{}", canal, xml);
        } catch (Exception e) {
            LOG.error("No fue posible enviar el pedido a la cola {}", canal, e);
        }
    }
}
