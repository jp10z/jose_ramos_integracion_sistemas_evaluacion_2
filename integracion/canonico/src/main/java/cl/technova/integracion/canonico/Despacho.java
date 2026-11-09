package cl.technova.integracion.canonico;

public class Despacho {
    private String direccion;
    private String calle;
    private String numero;
    private String comuna;
    private String ciudad;
    private Long costoEnvio;

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getComuna() { return comuna; }
    public void setComuna(String comuna) { this.comuna = comuna; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public Long getCostoEnvio() { return costoEnvio; }
    public void setCostoEnvio(Long costoEnvio) { this.costoEnvio = costoEnvio; }
}
