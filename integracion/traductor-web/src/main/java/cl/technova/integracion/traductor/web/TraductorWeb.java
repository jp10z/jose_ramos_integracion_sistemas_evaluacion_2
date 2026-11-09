package cl.technova.integracion.traductor.web;

import cl.technova.integracion.canonico.*;
import cl.technova.integracion.traductor.web.xml.PedidoWebXml;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class TraductorWeb implements Traductor {
    private final JAXBContext jaxb;

    public TraductorWeb() throws JAXBException {
        this.jaxb = JAXBContext.newInstance(PedidoWebXml.class);
    }

    @Override
    public PedidoCanonico traducir(String xml) throws JAXBException {
        PedidoWebXml w = (PedidoWebXml) jaxb.createUnmarshaller().unmarshal(new StringReader(xml));

        PedidoCanonico p = new PedidoCanonico();
        p.setIdPedido(UUID.randomUUID().toString());
        p.setOrigen(Origen.TIENDA_WEB);
        p.setFechaPedido(LocalDateTime.parse(w.fecha).truncatedTo(ChronoUnit.SECONDS).toString());
        p.setFechaProcesamiento(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());

        Cliente c = new Cliente();
        c.setNombreCompleto(w.comprador.nombre.trim());
        c.setRut(Rut.normalizar(w.comprador.rut));
        c.setTelefono(w.comprador.telefono);
        p.setCliente(c);

        Despacho d = new Despacho();
        d.setDireccion(w.direccionDespacho.calleYNumero);
        d.setComuna(w.direccionDespacho.comuna);
        d.setCostoEnvio(0L);
        p.setDespacho(d);

        for (PedidoWebXml.ItemXml i : w.items) {
            String categoria = i.producto.categoria != null ? i.producto.categoria.nombre : null;
            p.getItems().add(new Item(i.producto.sku, i.producto.nombre, categoria, i.cantidad, i.producto.precioLista));
        }

        p.calcularTotales("CLP");
        return p;
    }
}
