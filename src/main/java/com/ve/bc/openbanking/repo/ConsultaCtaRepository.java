package com.ve.bc.openbanking.repo;

import com.ve.bc.openbanking.dto.ConsultaDtoRequest;
import com.ve.bc.openbanking.dto.Estado;
import com.ve.bc.openbanking.dto.Moneda;
import com.ve.bc.openbanking.dto.Oficina;
import com.ve.bc.openbanking.dto.Producto;
import com.ve.bc.openbanking.dto.ResponseConsutaCtas;
import com.ve.bc.openbanking.dto.ResponseContratoCts;
import com.ve.bc.openbanking.dto.RespuestaConError;
import com.ve.bc.openbanking.dto.RespuestaConsultaServiDto;
import com.ve.bc.openbanking.dto.ServicioResponse;
import com.ve.bc.openbanking.exception.ResourceErroServicesException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Repository
public class ConsultaCtaRepository {
   @Value("${url.servi.consulta}")
   String UrlCccte;
   @Value("${api.ssl.status}")
   Boolean statusMetodo;
   @Value("${api.ssl.certif.name}")
   String certifName;
   @Autowired
   RestTemplate restTemplate;
   private static final Logger LOGGER = LoggerFactory.getLogger(ConsultaCtaRepository.class);

   public RespuestaConsultaServiDto getConsultaServicio(ConsultaDtoRequest Request, String tracerId) {
      LOGGER.info("Start ServicioRepository  : getConsultaServicio  RequestId :" + tracerId);
      new RespuestaConsultaServiDto();
      RespuestaConsultaServiDto respuestaConsultaServiDto;
      if (this.statusMetodo) {
         LOGGER.info("Start ServicioRepository  : getConsultaServicio Status True  RequestId :" + tracerId);
         respuestaConsultaServiDto = this.getConsultaServiciosCtsSsl(Request, tracerId);
      } else {
         LOGGER.info("Start ServicioRepository  : getConsultaServicio Status False  RequestId :" + tracerId);
         respuestaConsultaServiDto = this.getConsultaServiciosCts(Request, tracerId);
      }

      LOGGER.info("End  ServicioRepository  : getConsultaServicio  RequestId :" + tracerId);
      return respuestaConsultaServiDto;
   }

   public RespuestaConsultaServiDto getConsultaServiciosCts(ConsultaDtoRequest valiServicioRequest, String tracerId) {
      LOGGER.info("Start ServicioRepository  : getConsultaServiciosCts  RequestId :" + tracerId);
      List<ResponseConsutaCtas> listaCuentas = new ArrayList();
      RespuestaConsultaServiDto respuestaConsultaServiDto = new RespuestaConsultaServiDto();
      RespuestaConError errorConsulta = new RespuestaConError();
      URL url = null;
      URLConnection connection = null;
      HttpURLConnection httpConn = null;
      String responseString = null;
      String outputString = "";
      OutputStream out = null;
      InputStreamReader isr = null;
      BufferedReader in = null;
      String operacion = "ser:BuscarCuentas";
      
      String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.cc.ctas.ecobis.cobiscorp\" xmlns:dto2=\"http://dto2.sdf.cts.cobis.cobiscorp.com\" xmlns:dto21=\"http://dto2.commons.ecobis.cobiscorp\" xmlns:dto=\"http://dto.payload.cc.ctas.ecobis.cobiscorp\">\r\n"
    		  + "<soapenv:Header/>" 
				+ "<soapenv:Body>" 
				+ "<"+ operacion +">" 
				+ "<ser:inRequest>"
					+ "<dto:cedruc>" + valiServicioRequest.getCeduRif() + "</dto:cedruc>";
			      if (valiServicioRequest.getNumCuenta() != "") {
			         xmlInput = xmlInput + "<dto:numeroCuenta>" + valiServicioRequest.getNumCuenta() + "</dto:numeroCuenta>";
			      }

			      xmlInput += "</ser:inRequest>" 
							+ "</"+operacion+">" 
							+ "</soapenv:Body>" 
							+ "</soapenv:Envelope>";


      try {
         url = new URL(this.UrlCccte);
         connection = url.openConnection();
         httpConn = (HttpURLConnection)connection;
         byte[] buffer = new byte[xmlInput.length()];
         buffer = xmlInput.getBytes();
         String SOAPAction = "";
         httpConn.setRequestProperty("Content-Length", String.valueOf(buffer.length));
         httpConn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
         httpConn.setRequestProperty("SOAPAction", SOAPAction);
         httpConn.setRequestMethod("POST");
         httpConn.setDoOutput(true);
         httpConn.setDoInput(true);
         out = httpConn.getOutputStream();
         out.write(buffer);
         out.close();
         isr = new InputStreamReader(httpConn.getInputStream());

         for(BufferedReader var69 = new BufferedReader(isr); (responseString = var69.readLine()) != null; outputString = outputString + responseString) {
         }

         Document document = this.parseXmlFile(outputString);
         document.getDocumentElement().normalize();
         NodeList nodeLst = document.getElementsByTagName("success");
         String Status = nodeLst.item(0).getTextContent();
         LOGGER.info("Start ServicioRepository  " + Status);
         if (!Boolean.valueOf(Status)) {
            NodeList nodeCod = document.getElementsByTagName("ns2:code");
            String Cod = nodeCod.item(0).getTextContent();
            LOGGER.info("End  ServicioRepository Cod: " + Cod);
            NodeList nodeMsn = document.getElementsByTagName("ns2:message");
            String Mensaje = nodeMsn.item(0).getTextContent();
            LOGGER.info("End  ServicioRepository Mensaje : " + Mensaje);
            errorConsulta.setCodigoError(Cod);
            errorConsulta.setDescripcionError(Mensaje);
            errorConsulta.setStatus(Boolean.TRUE);
            LOGGER.info("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId);
            respuestaConsultaServiDto.setError(errorConsulta);
            return respuestaConsultaServiDto;
         } else {
            NodeList nList = document.getElementsByTagName("ns3:cuentas");

            for(int i = 0; i < nList.getLength(); ++i) {
               Producto producto = new Producto();
               Moneda moneda = new Moneda();
               Oficina oficina = new Oficina();
               Estado estado = new Estado();
               ResponseConsutaCtas responseConsutaCtas = new ResponseConsutaCtas();
               Element elemCta = (Element)nList.item(i);
               NodeList nodeLstProduc = elemCta.getElementsByTagName("ns5:producto");
               Element elemProduc = (Element)nodeLstProduc.item(0);
               NodeList nodeLstProducId = elemProduc.getElementsByTagName("ns5:id");
               String idProducto = nodeLstProducId.item(0).getTextContent();
               NodeList nodeLstProducType = elemProduc.getElementsByTagName("ns5:tipoProducto");
               String tipoProducto = nodeLstProducType.item(0).getTextContent();
               producto.setTipoProducto(tipoProducto);
               NodeList nodeLstSubProduc = elemProduc.getElementsByTagName("ns5:subProducto");
               String subProducto = nodeLstSubProduc.item(0).getTextContent();
               producto.setSubProducto(subProducto);
               NodeList nodeLstNumCta = elemProduc.getElementsByTagName("ns5:numeroCuenta");
               String numeroCuenta = nodeLstNumCta.item(0).getTextContent();
               producto.setNumeroCuenta(numeroCuenta);
               NodeList nodeLstFechaCreacion = elemProduc.getElementsByTagName("ns5:fechaCreacion");
               String fechaCreacion = nodeLstFechaCreacion.item(0).getTextContent();
               NodeList nodeLstMoneda = elemCta.getElementsByTagName("ns5:moneda");
               Element elemMoneda = (Element)nodeLstMoneda.item(0);
               NodeList nodeLstMonedaId = elemMoneda.getElementsByTagName("ns5:id");
               String idMoneda = nodeLstMonedaId.item(0).getTextContent();
               moneda.setId(Integer.valueOf(idMoneda));
               NodeList nodeLstMonedaDescripcion = elemMoneda.getElementsByTagName("ns5:descripcion");
               String descripcionMoneda = nodeLstMonedaDescripcion.item(0).getTextContent();
               moneda.setDescripcion(descripcionMoneda);
               NodeList nodeLstCodigoMoneda = elemMoneda.getElementsByTagName("ns5:codigo");
               String codigoMoneda = nodeLstCodigoMoneda.item(0).getTextContent();
               moneda.setCodigo(codigoMoneda);
               NodeList nodeLstOficina = elemCta.getElementsByTagName("ns5:oficina");
               Element elemOficina = (Element)nodeLstOficina.item(0);
               NodeList nodeLstOficinaId = elemOficina.getElementsByTagName("ns5:id");
               String idOficina = nodeLstOficinaId.item(0).getTextContent();
               oficina.setId(Integer.valueOf(idOficina));
               NodeList nodeLstOficinaDescripcion = elemOficina.getElementsByTagName("ns5:descripcion");
               String descripcionOficina = nodeLstOficinaDescripcion.item(0).getTextContent();
               oficina.setDescripcion(descripcionOficina);
               NodeList nodeLstEstado = elemCta.getElementsByTagName("ns5:estado");
               Element elemEstado = (Element)nodeLstEstado.item(0);
               NodeList nodeLstEstadoDescripcion = elemEstado.getElementsByTagName("ns5:descripcion");
               String descripcionEstado = nodeLstEstadoDescripcion.item(0).getTextContent();
               estado.setDescripcion(descripcionEstado);
               NodeList nodeLstEstadoCodigo = elemEstado.getElementsByTagName("ns5:codigo");
               String codigoEstado = nodeLstEstadoCodigo.item(0).getTextContent();
               estado.setCodigo(codigoEstado);
               responseConsutaCtas.setProducto(producto);
               responseConsutaCtas.setEstado(estado);
               responseConsutaCtas.setMoneda(moneda);
               responseConsutaCtas.setOficina(oficina);
               listaCuentas.add(responseConsutaCtas);
            }

            errorConsulta.setStatus(Boolean.FALSE);
            respuestaConsultaServiDto.setCuentas(listaCuentas);
            respuestaConsultaServiDto.setError(errorConsulta);
            return respuestaConsultaServiDto;
         }
      } catch (IOException e) {
         System.out.println(e.toString());
         LOGGER.error("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId + " >>>>>>> " + e.toString());
         throw new ResourceErroServicesException("ServicioRepository", "getConsultaServiciosCts");
      } catch (Exception e) {
         LOGGER.error("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId + " >>>>>>> " + e.toString());
         throw new ResourceErroServicesException("ServicioRepository", "getConsultaServiciosCts");
      }
   }

   public RespuestaConsultaServiDto getConsultaServiciosCtsSsl(ConsultaDtoRequest valiServicioRequest, String tracerId) {
      LOGGER.info("Start ServicioRepository  : getConsultaServiciosCts  RequestId :" + tracerId);
      List<ResponseConsutaCtas> listaCuentas = new ArrayList();
      new ResponseContratoCts();
      new ServicioResponse();
      RespuestaConsultaServiDto respuestaConsultaServiDto = new RespuestaConsultaServiDto();
      new ResponseConsutaCtas();
      RespuestaConError errorConsulta = new RespuestaConError();
      URL url = null;
      HttpsURLConnection connection = null;
      HttpsURLConnection httpConn = null;
      String responseString = null;
      String outputString = "";
      OutputStream out = null;
      InputStreamReader isr = null;
      BufferedReader in = null;
      String operacion = "ser:BuscarCuentas";
      String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.cc.ctas.ecobis.cobiscorp\" xmlns:dto2=\"http://dto2.sdf.cts.cobis.cobiscorp.com\" xmlns:dto21=\"http://dto2.commons.ecobis.cobiscorp\" xmlns:dto=\"http://dto.payload.cc.ctas.ecobis.cobiscorp\">\r\n<soapenv:Header/><soapenv:Body><" + operacion + ">" + "<ser:inRequest>" + "<dto:cedruc>" + valiServicioRequest.getCeduRif() + "</dto:cedruc>";
      if (valiServicioRequest.getNumCuenta() != "") {
         xmlInput = xmlInput + "<dto:numeroCuenta>" + valiServicioRequest.getNumCuenta() + "</dto:numeroCuenta>";
      }

      xmlInput = xmlInput + "</ser:inRequest></" + operacion + ">" + "</soapenv:Body>" + "</soapenv:Envelope>";

      try {
         CertificateFactory cf = CertificateFactory.getInstance("X.509");
         LOGGER.info("Paso 3  " + this.certifName);
         InputStream caInput = new BufferedInputStream(new FileInputStream(this.certifName));
         Certificate ca = cf.generateCertificate(caInput);
         String keyStoreType = KeyStore.getDefaultType();
         KeyStore keyStore = KeyStore.getInstance(keyStoreType);
         keyStore.load((InputStream)null, (char[])null);
         keyStore.setCertificateEntry("ca", ca);
         String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
         TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
         tmf.init(keyStore);
         SSLContext context = SSLContext.getInstance("TLS");
         context.init((KeyManager[])null, tmf.getTrustManagers(), (SecureRandom)null);
         url = new URL(this.UrlCccte);
         connection = (HttpsURLConnection)url.openConnection();
         connection.setSSLSocketFactory(context.getSocketFactory());
         byte[] buffer = new byte[xmlInput.length()];
         buffer = xmlInput.getBytes();
         String SOAPAction = "";
         connection.setRequestProperty("Content-Length", String.valueOf(buffer.length));
         connection.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
         connection.setRequestProperty("SOAPAction", SOAPAction);
         connection.setRequestMethod("POST");
         connection.setDoOutput(true);
         connection.setDoInput(true);
         out = connection.getOutputStream();
         out.write(buffer);
         out.close();
         isr = new InputStreamReader(connection.getInputStream());

         for(BufferedReader var79 = new BufferedReader(isr); (responseString = var79.readLine()) != null; outputString = outputString + responseString) {
         }

         Document document = this.parseXmlFile(outputString);
         document.getDocumentElement().normalize();
         NodeList nodeLst = document.getElementsByTagName("success");
         String Status = nodeLst.item(0).getTextContent();
         if (!Boolean.valueOf(Status)) {
            NodeList nodeCod = document.getElementsByTagName("ns2:code");
            String Cod = nodeCod.item(0).getTextContent();
            NodeList nodeMsn = document.getElementsByTagName("ns2:message");
            String Mensaje = nodeMsn.item(0).getTextContent();
            errorConsulta.setCodigoError(Cod);
            errorConsulta.setDescripcionError(Mensaje);
            errorConsulta.setStatus(Boolean.TRUE);
            LOGGER.info("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId);
            respuestaConsultaServiDto.setError(errorConsulta);
            return respuestaConsultaServiDto;
         } else {
            NodeList nList = document.getElementsByTagName("ns3:cuentas");

            for(int i = 0; i < nList.getLength(); ++i) {
               Producto producto = new Producto();
               Moneda moneda = new Moneda();
               Oficina oficina = new Oficina();
               Estado estado = new Estado();
               ResponseConsutaCtas responseConsutaCtas = new ResponseConsutaCtas();
               Element elemCta = (Element)nList.item(i);
               NodeList nodeLstProduc = elemCta.getElementsByTagName("ns5:producto");
               Element elemProduc = (Element)nodeLstProduc.item(0);
               NodeList nodeLstProducId = elemProduc.getElementsByTagName("ns5:id");
               String idProducto = nodeLstProducId.item(0).getTextContent();
               NodeList nodeLstProducType = elemProduc.getElementsByTagName("ns5:tipoProducto");
               String tipoProducto = nodeLstProducType.item(0).getTextContent();
               producto.setTipoProducto(tipoProducto);
               NodeList nodeLstSubProduc = elemProduc.getElementsByTagName("ns5:subProducto");
               String subProducto = nodeLstSubProduc.item(0).getTextContent();
               producto.setSubProducto(subProducto);
               NodeList nodeLstNumCta = elemProduc.getElementsByTagName("ns5:numeroCuenta");
               String numeroCuenta = nodeLstNumCta.item(0).getTextContent();
               producto.setNumeroCuenta(numeroCuenta);
               NodeList nodeLstFechaCreacion = elemProduc.getElementsByTagName("ns5:fechaCreacion");
               String fechaCreacion = nodeLstFechaCreacion.item(0).getTextContent();
               NodeList nodeLstMoneda = elemCta.getElementsByTagName("ns5:moneda");
               Element elemMoneda = (Element)nodeLstMoneda.item(0);
               NodeList nodeLstMonedaId = elemMoneda.getElementsByTagName("ns5:id");
               String idMoneda = nodeLstMonedaId.item(0).getTextContent();
               moneda.setId(Integer.valueOf(idMoneda));
               NodeList nodeLstMonedaDescripcion = elemMoneda.getElementsByTagName("ns5:descripcion");
               String descripcionMoneda = nodeLstMonedaDescripcion.item(0).getTextContent();
               moneda.setDescripcion(descripcionMoneda);
               NodeList nodeLstCodigoMoneda = elemMoneda.getElementsByTagName("ns5:codigo");
               String codigoMoneda = nodeLstCodigoMoneda.item(0).getTextContent();
               moneda.setCodigo(codigoMoneda);
               NodeList nodeLstOficina = elemCta.getElementsByTagName("ns5:oficina");
               Element elemOficina = (Element)nodeLstOficina.item(0);
               NodeList nodeLstOficinaId = elemOficina.getElementsByTagName("ns5:id");
               String idOficina = nodeLstOficinaId.item(0).getTextContent();
               oficina.setId(Integer.valueOf(idOficina));
               NodeList nodeLstOficinaDescripcion = elemOficina.getElementsByTagName("ns5:descripcion");
               String descripcionOficina = nodeLstOficinaDescripcion.item(0).getTextContent();
               oficina.setDescripcion(descripcionOficina);
               NodeList nodeLstEstado = elemCta.getElementsByTagName("ns5:estado");
               Element elemEstado = (Element)nodeLstEstado.item(0);
               NodeList nodeLstEstadoDescripcion = elemEstado.getElementsByTagName("ns5:descripcion");
               String descripcionEstado = nodeLstEstadoDescripcion.item(0).getTextContent();
               estado.setDescripcion(descripcionEstado);
               NodeList nodeLstEstadoCodigo = elemEstado.getElementsByTagName("ns5:codigo");
               String codigoEstado = nodeLstEstadoCodigo.item(0).getTextContent();
               estado.setCodigo(codigoEstado);
               responseConsutaCtas.setProducto(producto);
               responseConsutaCtas.setEstado(estado);
               responseConsutaCtas.setMoneda(moneda);
               responseConsutaCtas.setOficina(oficina);
               listaCuentas.add(responseConsutaCtas);
            }

            errorConsulta.setStatus(Boolean.FALSE);
            respuestaConsultaServiDto.setCuentas(listaCuentas);
            respuestaConsultaServiDto.setError(errorConsulta);
            return respuestaConsultaServiDto;
         }
      } catch (IOException e) {
         System.out.println(e.toString());
         LOGGER.info("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId + " >>>>>>> " + e.toString());
         throw new ResourceErroServicesException("ServicioRepository", "getConsultaServiciosCts");
      } catch (Exception e) {
         LOGGER.info("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId + " >>>>>>> " + e.toString());
         throw new ResourceErroServicesException("ServicioRepository", "getConsultaServiciosCts");
      }
   }

   private Document parseXmlFile(String in) {
      try {
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder db = dbf.newDocumentBuilder();
         InputSource is = new InputSource(new StringReader(in));
         return db.parse(is);
      } catch (ParserConfigurationException e) {
         throw new RuntimeException(e);
      } catch (SAXException e) {
         throw new RuntimeException(e);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
