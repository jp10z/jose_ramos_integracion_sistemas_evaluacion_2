package cl.technova.integracion.canonico;

public interface Traductor {
    PedidoCanonico traducir(String contenidoOrigen) throws Exception;
}
