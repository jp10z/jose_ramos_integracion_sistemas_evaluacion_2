package cl.technova.integracion.adapter.facturacion.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;

@WebService(name = "Servicio", targetNamespace = ServicioFacturacion.NS)
public interface ServicioFacturacion {
    String NS = "http://example.org/";

    @WebMethod(operationName = "generarBoleta")
    @WebResult(name = "boletaXml")
    @RequestWrapper(localName = "generarBoleta", targetNamespace = NS)
    @ResponseWrapper(localName = "generarBoletaResponse", targetNamespace = NS)
    String generarBoleta(@WebParam(name = "cliente") String cliente,
                         @WebParam(name = "rut") String rut,
                         @WebParam(name = "total") Long total);
}
