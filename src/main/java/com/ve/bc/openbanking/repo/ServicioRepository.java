package com.ve.bc.openbanking.repo;

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
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ve.bc.openbanking.dto.ConsultaDtoRequest;
import com.ve.bc.openbanking.dto.Estado;
import com.ve.bc.openbanking.dto.Moneda;
import com.ve.bc.openbanking.dto.Oficina;
import com.ve.bc.openbanking.dto.Producto;


import com.ve.bc.openbanking.dto.ServicioResponse;

import com.ve.bc.openbanking.dto.ServicioRequest;
import com.ve.bc.openbanking.dto.ResponseConsutaCtas;
import com.ve.bc.openbanking.dto.ResponseContratoCts;
import com.ve.bc.openbanking.dto.RespuestaConError;
import com.ve.bc.openbanking.dto.RespuestaConsultaServiDto;
import com.ve.bc.openbanking.exception.ResourceErroServicesException;


@Repository
public class ServicioRepository {

	@Value("${url.servi.consulta}")
    String UrlCccte;
	@Value("${api.contrato.canal}")
	String Canal;
	
	@Value("${api.ssl.status}")
    Boolean statusMetodo;
	@Value("${api.ssl.certif.name}")
    String certifName;
	
				
			
	private static final Logger LOGGER = LoggerFactory.getLogger(ServicioRepository.class);

	public RespuestaConsultaServiDto getConsultaServicio(ConsultaDtoRequest Request, String tracerId) {
		LOGGER.info("Start ServicioRepository  : getConsultaServicio  RequestId :" + tracerId);
		RespuestaConsultaServiDto respuestaConsultaServiDto = new RespuestaConsultaServiDto();
		if(statusMetodo){
			respuestaConsultaServiDto = null;//getConsultaServiciosCtsSsl(Request, tracerId);
		}else {
			respuestaConsultaServiDto = getConsultaServiciosCts(Request, tracerId);
		}
		
		LOGGER.info("End  ServicioRepository  : getConsultaServicio  RequestId :" + tracerId);
		return respuestaConsultaServiDto;
	}

	public RespuestaConsultaServiDto getConsultaServiciosCts(ConsultaDtoRequest valiServicioRequest, String tracerId) {

		LOGGER.info("Start ServicioRepository  : getConsultaServiciosCts  RequestId :" + tracerId);
		
		List<ResponseConsutaCtas>listaCuentas = new ArrayList<>();
		
	
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
	

		String xmlInput =  "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.cc.ctas.ecobis.cobiscorp\" xmlns:dto2=\"http://dto2.sdf.cts.cobis.cobiscorp.com\" xmlns:dto21=\"http://dto2.commons.ecobis.cobiscorp\" xmlns:dto=\"http://dto.payload.cc.ctas.ecobis.cobiscorp\">\r\n"
				+ "<soapenv:Header/>" 
				+ "<soapenv:Body>" 
				+ "<"+ operacion +">" 
				+ "<ser:inRequest>"
				
				+ "<dto:cedruc>" + valiServicioRequest.getCeduRif() + "</dto:cedruc>" ;
				if(valiServicioRequest.getNumCuenta()!="") {
					xmlInput +="<dto:numeroCuenta>"+ valiServicioRequest.getNumCuenta() + "</dto:numeroCuenta>" ;
				}
				if(valiServicioRequest.getCodMoneda()!=0) {
					xmlInput +="<dto:moneda>"+ valiServicioRequest.getCodMoneda() + "</dto:moneda>" ;
				}

				xmlInput += "</ser:inRequest>" 
				+ "</"+operacion+">" 
				+ "</soapenv:Body>" 
				+ "</soapenv:Envelope>";
 
		try {
			
			url = new URL(UrlCccte);
			connection = url.openConnection();

			httpConn = (HttpURLConnection) connection;

			byte[] buffer = new byte[xmlInput.length()];
			buffer = xmlInput.getBytes();
			
			String SOAPAction = "";
			// Set the appropriate HTTP parameters.
			httpConn.setRequestProperty("Content-Length", String.valueOf(buffer.length));
			httpConn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
			httpConn.setRequestProperty("SOAPAction", SOAPAction);
			// httpConn.setRequestProperty ("Authorization", "Basic " + Llave);
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			out = httpConn.getOutputStream();
			out.write(buffer);
			out.close();

			// Read the response and write it to standard out.
			isr = new InputStreamReader(httpConn.getInputStream());
			in = new BufferedReader(isr);

			while ((responseString = in.readLine()) != null) {
				outputString = outputString + responseString;
			}
			
			// Get the response from the web service call
			Document document = parseXmlFile(outputString);

			document.getDocumentElement().normalize();

			NodeList nodeLst = document.getElementsByTagName("ns3:success");
			String Status = nodeLst.item(0).getTextContent();

			if (Boolean.valueOf(Status)) {
				
				NodeList nList = document.getElementsByTagName("ns2:cuentas");
				System.out.println(nList.getLength());
				for (int i = 0; i < nList.getLength(); i++) {
					Producto producto = new Producto();
					Moneda moneda = new Moneda();
					Oficina oficina = new Oficina();
					Estado estado = new Estado();
					ResponseConsutaCtas responseConsutaCtas = new ResponseConsutaCtas();
					
					Element elemCta = (Element) nList.item(i);
					
					NodeList nodeLstProduc = elemCta.getElementsByTagName("ns1:producto");
					Element elemProduc = (Element) nodeLstProduc.item(0);

						NodeList nodeLstProducId = elemProduc.getElementsByTagName("ns1:id");
						String idProducto = nodeLstProducId.item(0).getTextContent();
						//System.out.println(idProducto);
						
					
						NodeList nodeLstProducType = elemProduc.getElementsByTagName("ns1:tipoProducto");
						String tipoProducto = nodeLstProducType.item(0).getTextContent();
						//System.out.println(tipoProducto);
						producto.setTipoProducto(tipoProducto);
						
						NodeList nodeLstSubProduc = elemProduc.getElementsByTagName("ns1:subProducto");
						String subProducto = nodeLstSubProduc.item(0).getTextContent();
						//System.out.println(subProducto);
						producto.setSubProducto(subProducto);
						
						NodeList nodeLstNumCta = elemProduc.getElementsByTagName("ns1:numeroCuenta");
						String numeroCuenta = nodeLstNumCta.item(0).getTextContent();
						//System.out.println(numeroCuenta);
						producto.setNumeroCuenta(numeroCuenta);
						
						NodeList nodeLstFechaCreacion = elemProduc.getElementsByTagName("ns1:fechaCreacion");
						String fechaCreacion = nodeLstFechaCreacion.item(0).getTextContent();
						//System.out.println(fechaCreacion);
						
	
			      NodeList nodeLstMoneda = elemCta.getElementsByTagName("ns1:moneda");
				  Element elemMoneda = (Element) nodeLstMoneda.item(0);
				  
				       NodeList nodeLstMonedaId = elemMoneda.getElementsByTagName("ns1:id");
					   String idMoneda = nodeLstMonedaId.item(0).getTextContent();
					   //System.out.println(idMoneda);
					   moneda.setId(Integer.valueOf(idMoneda));
					   
					   NodeList nodeLstMonedaDescripcion = elemMoneda.getElementsByTagName("ns1:descripcion");
					   String descripcionMoneda = nodeLstMonedaDescripcion.item(0).getTextContent();
					   //System.out.println(descripcionMoneda);
					   moneda.setDescripcion(descripcionMoneda);
					   
					   NodeList nodeLstCodigoMoneda = elemMoneda.getElementsByTagName("ns1:codigo");
					   String codigoMoneda = nodeLstCodigoMoneda.item(0).getTextContent();
					   //System.out.println(codigoMoneda);
					   moneda.setCodigo(codigoMoneda);
				   
				   NodeList nodeLstOficina = elemCta.getElementsByTagName("ns1:oficina");
				   Element elemOficina = (Element) nodeLstOficina.item(0);  
				   
					   NodeList nodeLstOficinaId = elemOficina.getElementsByTagName("ns1:id");
					   String idOficina = nodeLstOficinaId.item(0).getTextContent();
					   //System.out.println(idOficina);
					   oficina.setId(Integer.valueOf(idOficina));
					   
					   NodeList nodeLstOficinaDescripcion = elemOficina.getElementsByTagName("ns1:descripcion");
					   String descripcionOficina = nodeLstOficinaDescripcion.item(0).getTextContent();
					   //System.out.println(descripcionOficina);
					   oficina.setDescripcion(descripcionOficina);
					   
				   NodeList nodeLstEstado = elemCta.getElementsByTagName("ns1:estado");
				   Element elemEstado = (Element) nodeLstEstado.item(0);  
					   
					/*   NodeList nodeLstEstadoId = elemEstado.getElementsByTagName("ns1:id");
					   String idEstado = nodeLstEstadoId.item(0).getTextContent();
					   System.out.println(idEstado);*/
						
					   NodeList nodeLstEstadoDescripcion = elemEstado.getElementsByTagName("ns1:descripcion");
					   String descripcionEstado = nodeLstEstadoDescripcion.item(0).getTextContent();
					   //System.out.println(descripcionEstado);  
					   estado.setDescripcion(descripcionEstado);
					   
					   NodeList nodeLstEstadoCodigo = elemEstado.getElementsByTagName("ns1:codigo");
					   String codigoEstado = nodeLstEstadoCodigo.item(0).getTextContent();
					   //System.out.println(codigoEstado);
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
			} else {	
				
				NodeList nodeCod = document.getElementsByTagName("ns0:code");
				String Cod = nodeCod.item(0).getTextContent();
				NodeList nodeMsn = document.getElementsByTagName("ns0:message");
				String Mensaje = nodeMsn.item(0).getTextContent();

				errorConsulta.setCodigoError(Cod);
				errorConsulta.setDescripcionError(Mensaje);
				errorConsulta.setStatus(Boolean.TRUE);
				LOGGER.info("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId);
				respuestaConsultaServiDto.setError(errorConsulta);
			
				return respuestaConsultaServiDto;
			}
		} catch (IOException e) {
			System.out.println(e.toString());

			LOGGER.info("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId+" >>>>>>> "+e.toString());
			throw new ResourceErroServicesException("ServicioRepository", "getConsultaServiciosCts");
		} catch (Exception e) {		
			LOGGER.info("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId+" >>>>>>> "+e.toString());
			throw new ResourceErroServicesException("ServicioRepository", "getConsultaServiciosCts");
		}
	}

//*********************************************************** - CON SERTIFICADO TSL - ***********************************************************************
	

	/*public ResponseConsutaCtas getConsultaServiciosCtsSsl(ConsultaDtoRequest valiServicioRequest, String tracerId) {

		LOGGER.info("Start ServicioRepository  : getConsultaServiciosCts  RequestId :" + tracerId);
		ResponseContratoCts responseContratoCts = new ResponseContratoCts();
		ServicioResponse servicioResponse = new ServicioResponse();
		ResponseConsutaCtas valiServicioResponse = new ResponseConsutaCtas();
		RespuestaConError errorConsulta = new RespuestaConError();
		URL url = null;
		HttpsURLConnection connection = null;
		HttpsURLConnection httpConn = null;
		String responseString = null;
		String outputString = "";
		OutputStream out = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		String operacion = "ser:ValidarServicios";
		Certificate ca;
	
		String xmlInput =  "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.cc.ctas.ecobis.cobiscorp\" xmlns:dto2=\"http://dto2.sdf.cts.cobis.cobiscorp.com\" xmlns:dto21=\"http://dto2.commons.ecobis.cobiscorp\" xmlns:dto=\"http://dto.payload.cc.ctas.ecobis.cobiscorp\">\r\n"
				+ "<soapenv:Header/>" 
				+ "<soapenv:Body>" 
				+ "<"+ operacion +">" 
				+ "<ser:inRequest>"
				
				+ "<dto:cedruc>" + valiServicioRequest.getCeduRif() + "</dto:cedruc>" ;
				if(valiServicioRequest.getNumCuenta()!="") {
					xmlInput +="<dto:numeroCuenta>"+ valiServicioRequest.getNumCuenta() + "</dto:numeroCuenta>" ;
				}
				if(valiServicioRequest.getCodMoneda()!=0) {
					xmlInput +="<dto:moneda>"+ valiServicioRequest.getCodMoneda() + "</dto:moneda>" ;
				}

				xmlInput += "</ser:inRequest>" 
				+ "</"+operacion+">" 
				+ "</soapenv:Body>" 
				+ "</soapenv:Envelope>";
 
		try {
			
			//LOGGER.info("Paso 2 "+xmlInput);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			LOGGER.info("Paso 3  " +certifName);
			InputStream caInput = new BufferedInputStream(new FileInputStream(certifName));
			//LOGGER.info("Paso 4");
			ca = cf.generateCertificate(caInput);
			//LOGGER.info("Paso 5");
			String keyStoreType = KeyStore.getDefaultType();
			//LOGGER.info("Paso 6 " +keyStoreType);
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);
			//LOGGER.info("Paso 7");
			keyStore.load(null, null);
			keyStore.setCertificateEntry("ca", ca);
			//LOGGER.info("Paso 8");
			String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
			//LOGGER.info("Paso 9 "+tmfAlgorithm);
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
			//LOGGER.info("Paso 10");
			tmf.init(keyStore);
			SSLContext context = SSLContext.getInstance("TLS");
			//LOGGER.info("Paso 11");
			context.init(null, tmf.getTrustManagers(), null);
			//LOGGER.info("Paso 12");
			url = new URL(UrlCccte);
			//LOGGER.info("Paso 13");
			connection = (HttpsURLConnection) url.openConnection();
			//LOGGER.info("Paso 14");
			connection.setSSLSocketFactory(context.getSocketFactory());
			//LOGGER.info("Paso 15");
			httpConn = (HttpsURLConnection) connection;
			//LOGGER.info("Paso 16");
			byte[] buffer = new byte[xmlInput.length()];
			//LOGGER.info("Paso 17");
			buffer = xmlInput.getBytes();
			//LOGGER.info("Paso 18");

			String SOAPAction = "";

			httpConn.setRequestProperty("Content-Length", String.valueOf(buffer.length));
			httpConn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
			httpConn.setRequestProperty("SOAPAction", SOAPAction);
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			//LOGGER.info("Paso 19");
			out = httpConn.getOutputStream();
			//LOGGER.info("Paso 20");
			out.write(buffer);
			//LOGGER.info("Paso 21");
			out.close();

			// Read the response and write it to standard out.
			isr = new InputStreamReader(httpConn.getInputStream());
			in = new BufferedReader(isr);

			while ((responseString = in.readLine()) != null) {
				outputString = outputString + responseString;
			}
			
			// Get the response from the web service call
			Document document = parseXmlFile(outputString);

			document.getDocumentElement().normalize();

			NodeList nodeLst = document.getElementsByTagName("ns3:success");
			String Status = nodeLst.item(0).getTextContent();

			if (Boolean.valueOf(Status)) {
				
				NodeList nodeLstId = document.getElementsByTagName("ns1:id");
				String Id = nodeLstId.item(0).getTextContent();
				servicioResponse.setId(Integer.valueOf(Id));
				
				NodeList nodeLstDescrip = document.getElementsByTagName("ns1:descripcion");
				String Descripcion = nodeLstDescrip.item(0).getTextContent();
				servicioResponse.setDescripcion(Descripcion);
				
				NodeList nodeLstIdentific= document.getElementsByTagName("ns1:identificador");
				String Identificador = nodeLstIdentific.item(0).getTextContent();
				servicioResponse.setIdentificador(Identificador);
				
				NodeList nodeLstEstado = document.getElementsByTagName("ns1:estado");
				String Estado = nodeLstEstado.item(0).getTextContent();
				servicioResponse.setEstado(Estado);
				
				NodeList nodeLstNombre = document.getElementsByTagName("ns1:nombre");
				String Nombre = nodeLstNombre.item(0).getTextContent();
				servicioResponse.setNombre(Nombre);
				
				
				errorConsulta.setStatus(Boolean.FALSE);
				valiServicioResponse.setErrorConsulta(errorConsulta);
				valiServicioResponse.setServicio(servicioResponse);
				valiServicioResponse.setTracerId(tracerId);
				return valiServicioResponse;
				
			} else {
				
				NodeList nodeCod = document.getElementsByTagName("ns0:code");
				String Cod = nodeCod.item(0).getTextContent();
				NodeList nodeMsn = document.getElementsByTagName("ns0:message");
				String Mensaje = nodeMsn.item(0).getTextContent();

				errorConsulta.setCodigoError(Cod);
				errorConsulta.setDescripcionError(Mensaje);
				errorConsulta.setStatus(Boolean.TRUE);
				LOGGER.info("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId);
				valiServicioResponse.setErrorConsulta(errorConsulta);
				valiServicioResponse.setTracerId(tracerId);
				return valiServicioResponse;
			}
		} catch (IOException e) {
			System.out.println(e.toString());
			LOGGER.info("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId+" >>>>>>> "+e.toString());
			throw new ResourceErroServicesException("ServicioRepository", "getConsultaServiciosCts");
		} catch (Exception e) {		
			LOGGER.info("End  ServicioRepository : getConsultaServiciosCts  RequestId :" + tracerId+" >>>>>>> "+e.toString());
			throw new ResourceErroServicesException("ServicioRepository", "getConsultaServiciosCts");
		}
	}
*/

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
