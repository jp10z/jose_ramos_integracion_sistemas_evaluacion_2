package cl.technova.integracion.canonico;

import java.util.ArrayList;
import java.util.List;

public class PedidoCanonico {
    private String versionEsquema = "1.0";
    private String idPedido;
    private Origen origen;
    private String idOrigen;
    private String fechaPedido;
    private String fechaProcesamiento;
    private Cliente cliente;
    private Despacho despacho;
    private List<Item> items = new ArrayList<>();
    private Totales totales;

    public String getVersionEsquema() { return versionEsquema; }
    public void setVersionEsquema(String versionEsquema) { this.versionEsquema = versionEsquema; }
    public String getIdPedido() { return idPedido; }
    public void setIdPedido(String idPedido) { this.idPedido = idPedido; }
    public Origen getOrigen() { return origen; }
    public void setOrigen(Origen origen) { this.origen = origen; }
    public String getIdOrigen() { return idOrigen; }
    public void setIdOrigen(String idOrigen) { this.idOrigen = idOrigen; }
    public String getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(String fechaPedido) { this.fechaPedido = fechaPedido; }
    public String getFechaProcesamiento() { return fechaProcesamiento; }
    public void setFechaProcesamiento(String fechaProcesamiento) { this.fechaProcesamiento = fechaProcesamiento; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Despacho getDespacho() { return despacho; }
    public void setDespacho(Despacho despacho) { this.despacho = despacho; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
    public Totales getTotales() { return totales; }
    public void setTotales(Totales totales) { this.totales = totales; }

    public void calcularTotales(String moneda) {
        long subtotal = items.stream().mapToLong(Item::getSubtotal).sum();
        long envio = (despacho != null && despacho.getCostoEnvio() != null) ? despacho.getCostoEnvio() : 0L;
        this.totales = new Totales(moneda, subtotal, envio, subtotal + envio);
    }
}
