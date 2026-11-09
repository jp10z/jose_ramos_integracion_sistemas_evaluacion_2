package cl.technova.integracion.traductor.marketplace;

import java.util.List;

public class VentaMarketplace {
    String id;
    String fecha;
    ClienteMkp cliente;
    DireccionMkp direccion;
    List<ItemMkp> items;
    EnvioMkp envio;
    Double comisionMarketplace;

    static class ClienteMkp { String nombre; String apellido; String rut; }
    static class DireccionMkp { String calle; String numero; String comuna; String ciudad; }
    static class ItemMkp { String producto; int cantidad; long precioUnitario; }
    static class EnvioMkp { Long costoEnvio; String moneda; }
}
