package cl.technova.integracion.traductor.web;

import cl.technova.integracion.comun.Consola;
import cl.technova.integracion.comun.TraductorEndpoint;

public class TraductorWebApp {
    public static void main(String[] args) throws Exception {
        TraductorEndpoint endpoint = new TraductorEndpoint(
                "Traductor Web", "webPedidos", "pedidos", new TraductorWeb());
        Consola.esperarHastaCtrlC(endpoint);
    }
}
