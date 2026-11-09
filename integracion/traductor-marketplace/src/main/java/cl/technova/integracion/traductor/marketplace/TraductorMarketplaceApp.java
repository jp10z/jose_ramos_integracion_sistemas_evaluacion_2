package cl.technova.integracion.traductor.marketplace;

import cl.technova.integracion.comun.Consola;
import cl.technova.integracion.comun.TraductorEndpoint;

public class TraductorMarketplaceApp {
    public static void main(String[] args) throws Exception {
        TraductorEndpoint endpoint = new TraductorEndpoint(
                "Traductor Marketplace", "mkpPedidos", "pedidos", new TraductorMarketplace());
        Consola.esperarHastaCtrlC(endpoint);
    }
}
