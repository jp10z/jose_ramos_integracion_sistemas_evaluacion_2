package cl.technova.integracion.canonico;

public class Totales {
    private String moneda;
    private long subtotalProductos;
    private long costoEnvio;
    private long total;
    private Double comisionCanal;

    public Totales() { }

    public Totales(String moneda, long subtotalProductos, long costoEnvio, long total) {
        this.moneda = moneda;
        this.subtotalProductos = subtotalProductos;
        this.costoEnvio = costoEnvio;
        this.total = total;
    }

    public String getMoneda() { return moneda; }
    public long getSubtotalProductos() { return subtotalProductos; }
    public long getCostoEnvio() { return costoEnvio; }
    public long getTotal() { return total; }
    public Double getComisionCanal() { return comisionCanal; }
    public void setComisionCanal(Double comisionCanal) { this.comisionCanal = comisionCanal; }
}
