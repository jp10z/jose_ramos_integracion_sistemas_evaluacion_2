package cl.technova.integracion.traductor.marketplace;

import cl.technova.integracion.canonico.*;
import cl.technova.integracion.comun.Json;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class TraductorMarketplace implements Traductor {

    @Override
    public PedidoCanonico traducir(String json) {
        VentaMarketplace v = Json.GSON.fromJson(json, VentaMarketplace.class);

        PedidoCanonico p = new PedidoCanonico();
        p.setIdPedido(UUID.randomUUID().toString());
        p.setOrigen(Origen.MARKETPLACE);
        p.setIdOrigen(v.id);
        p.setFechaPedido(v.fecha);
        p.setFechaProcesamiento(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());

        Cliente c = new Cliente();
        c.setNombre(v.cliente.nombre);
        c.setApellido(v.cliente.apellido);
        c.setNombreCompleto((v.cliente.nombre + " " + v.cliente.apellido).trim());
        c.setRut(Rut.normalizar(v.cliente.rut));
        p.setCliente(c);

        Despacho d = new Despacho();
        d.setCalle(v.direccion.calle);
        d.setNumero(v.direccion.numero);
        d.setDireccion((v.direccion.calle + " " + v.direccion.numero).trim());
        d.setComuna(v.direccion.comuna);
        d.setCiudad(v.direccion.ciudad);
        d.setCostoEnvio(v.envio != null ? v.envio.costoEnvio : 0L);
        p.setDespacho(d);

        for (VentaMarketplace.ItemMkp i : v.items) {
            p.getItems().add(new Item(null, i.producto, null, i.cantidad, i.precioUnitario));
        }

        String moneda = (v.envio != null && v.envio.moneda != null) ? v.envio.moneda : "CLP";
        p.calcularTotales(moneda);
        p.getTotales().setComisionCanal(v.comisionMarketplace);
        return p;
    }
}
