package cl.technova.integracion.comun;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.JMSContext;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public final class Broker {
    private Broker() { }

    public static JMSContext crearContexto() throws NamingException {
        ConnectionFactory cf = lookup("ConnectionFactory");
        String usuario = System.getenv().getOrDefault("ARTEMIS_USER", "technova");
        String password = System.getenv().getOrDefault("ARTEMIS_PASSWORD", "technova");
        return cf.createContext(usuario, password);
    }

    public static Destination destino(String nombreLogico) throws NamingException {
        return lookup(nombreLogico);
    }

    @SuppressWarnings("unchecked")
    private static <T> T lookup(String nombre) throws NamingException {
        InitialContext jndi = new InitialContext();
        try {
            return (T) jndi.lookup(nombre);
        } finally {
            jndi.close();
        }
    }
}
