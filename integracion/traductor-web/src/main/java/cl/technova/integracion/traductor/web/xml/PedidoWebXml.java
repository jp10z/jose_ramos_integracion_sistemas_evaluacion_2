package cl.technova.integracion.traductor.web.xml;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "pedido")
@XmlAccessorType(XmlAccessType.FIELD)
public class PedidoWebXml {
    public String fecha;

    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    public List<ItemXml> items = new ArrayList<>();

    public CompradorXml comprador;
    public DireccionXml direccionDespacho;

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ItemXml {
        public ProductoXml producto;
        public int cantidad;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ProductoXml {
        public String sku;
        public String nombre;
        public Long precioLista;
        public CategoriaXml categoria;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CategoriaXml {
        public String nombre;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CompradorXml {
        public String nombre;
        public String rut;
        public String telefono;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class DireccionXml {
        public String comuna;
        public String calleYNumero;
    }
}
