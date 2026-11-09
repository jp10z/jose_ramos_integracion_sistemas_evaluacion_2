package cl.technova.integracion.canonico;

public class Item {
    private String sku;
    private String descripcion;
    private String categoria;
    private int cantidad;
    private long precioUnitario;
    private long subtotal;

    public Item() { }

    public Item(String sku, String descripcion, String categoria, int cantidad, long precioUnitario) {
        this.sku = sku;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = precioUnitario * cantidad;
    }

    public String getSku() { return sku; }
    public String getDescripcion() { return descripcion; }
    public String getCategoria() { return categoria; }
    public int getCantidad() { return cantidad; }
    public long getPrecioUnitario() { return precioUnitario; }
    public long getSubtotal() { return subtotal; }
}
