package com.ve.bc.openbanking.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ve.bc.openbanking.dto.ServicioResponse;
import com.ve.bc.openbanking.feignclient.AfiliacionContratosFeignClient;
import com.ve.bc.openbanking.feignclient.AfiliacionServiciosFeignClient;
import com.ve.bc.openbanking.dto.ServicioRequest;
import com.ve.bc.openbanking.dto.ConsultaCtaByNumRequest;
import com.ve.bc.openbanking.dto.ConsultaCtasByMonedaRequest;
import com.ve.bc.openbanking.dto.ConsultaCtasRequest;
import com.ve.bc.openbanking.dto.ConsultaDatosCtaRequest;
import com.ve.bc.openbanking.dto.ConsultaDtoRequest;
import com.ve.bc.openbanking.dto.ContratoRequest;
import com.ve.bc.openbanking.dto.ContratoResponse;
import com.ve.bc.openbanking.dto.ErrorResponse;
import com.ve.bc.openbanking.dto.ResponseConsutaCtas;
import com.ve.bc.openbanking.service.ConsultaCtaServices;
import com.ve.bc.openbanking.utils.Utils;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ctasConsultaCuentas")

@Tag(name = "Consulta Cuentas")
public class ConsultaCtasController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultaCtasController.class);

	@Autowired
	Utils utils;
	
	@Autowired
	ConsultaCtaServices servicioServices;
	
	@Autowired
	RestTemplate restTemplate;
	

	
	@Value("${url.servi.contrato}")
    String UrlContrato;

	@Value("${url.servi.servicio}")
    String UrlServicio;

	@Value("${api.servi.name}")
    String serviName;	
	
	
	
	String errorContrato = "";
	String errorServicio = "";
	
	//@Operation(summary = "${api.doc.summary.servi.contr}", description = "${api.doc.description.servi.contr}")
	@ApiResponses(value = {
			
			@ApiResponse(
					  responseCode = "200",
					  content = @Content(
					  array = 
					  @io.swagger.v3.oas.annotations.media.ArraySchema(
					      schema = @Schema(implementation = ResponseConsutaCtas.class))), 
					      description = "Ok"),
			
			@ApiResponse(responseCode = "400", description = "Bad Request",
					content = {
							@Content(mediaType = "application/json",
							schema = @Schema(implementation = ErrorResponse.class)		)					
							}),
			@ApiResponse(responseCode = "401", description = "Unauthorized",
			content = {
					@Content(mediaType = "application/json",
					schema = @Schema(implementation = ErrorResponse.class)		)					
					}),
			@ApiResponse(responseCode = "409", description = "Conflict",
			content = {
					@Content(mediaType = "application/json",
					schema = @Schema(implementation = ErrorResponse.class)		)					
					}),
			@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = {
					@Content(mediaType = "application/json",
					schema = @Schema(implementation = ErrorResponse.class)		)					
					})
	})

	@PostMapping
	public ResponseEntity<?> getCosultaCuentas(@RequestHeader(value = "X-Request-Id", required = false) String requestId,
			@Valid @RequestBody ConsultaCtasByMonedaRequest request, HttpServletResponse response){	
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> valiServiciosResponse = null;
		if (requestId == null || requestId == ""){
			requestId = utils.generarCodigoTracerId();
		}
		ConsultaDtoRequest consultaDtoRequest = new ConsultaDtoRequest(); 
		consultaDtoRequest.setMoneda(request.getMoneda().toUpperCase());
		consultaDtoRequest.setCeduRif(request.getCeduRif());
		consultaDtoRequest.setHash(request.getHash());
		consultaDtoRequest.setIp(request.getIp());
		consultaDtoRequest.setNumCuenta("");

		LOGGER.info("Start ConsultaGralServiciosController : getConsultaServicios  RequestId :" + requestId);
		LOGGER.info("ConsultaGralContratosController Direccion IP : " + request.getIp());
		
		if(getValidaContrato(consultaDtoRequest,requestId,request.getIp())) {			
			if(getValidaServices(consultaDtoRequest,requestId,request.getIp())){
				valiServiciosResponse =  servicioServices.getConsulta(consultaDtoRequest, requestId);	
			}else {
				ErrorResponse errorDto = new ErrorResponse();
				if(errorServicio.equals("") || !(errorServicio.contains("409"))) {
					errorDto.setCodigoError("180233");
					errorDto.setDescripcionError("Falla validando la afiliacion de Servicio");	
					LOGGER.error(" End  ConsultaGralServiciosController falla validando el servicio  : getConsultaServicios 1  RequestId :" + requestId);
				}else {
					errorDto = decoError(errorServicio, true);
					LOGGER.error(" End  ConsultaGralServiciosController falla validando el servicio  : getConsultaServicios  2 RequestId :" + requestId);	
				}
				headers.add("X-Request-Id", requestId);
	            return new ResponseEntity<ErrorResponse>(errorDto,headers,HttpStatus.CONFLICT);
			}			
		}else {
			ErrorResponse errorDto = new ErrorResponse();
			if(errorContrato.equals("") || !(errorContrato.contains("409"))) {
				errorDto.setCodigoError("180234");
				errorDto.setDescripcionError("Falla validando la afiliacion del Contrato");
				LOGGER.error(" End  ConsultaGralServiciosController falla validando el contrato : getConsultaServicios  RequestId :" + requestId);
				
			}else {
				errorDto = decoError(errorContrato,false);
				LOGGER.error(" End  ConsultaGralServiciosController falla validando el contrato : getConsultaServicios  RequestId :" + requestId);
			}
			
			headers.add("X-Request-Id", requestId);
            return new ResponseEntity<ErrorResponse>(errorDto,headers, HttpStatus.CONFLICT);		
		}	
		LOGGER.info(" End  ConsultaGralServiciosController : getConsultaServicios  RequestId :" + requestId);
		
		return valiServiciosResponse;
		
	}
	
	
	@ApiResponses(value = {
			
			@ApiResponse(
					  responseCode = "200",
					  content = @Content(
					  array = 
					  @io.swagger.v3.oas.annotations.media.ArraySchema(
					      schema = @Schema(implementation = ResponseConsutaCtas.class))), 
					      description = "Ok"),
						  
			@ApiResponse(responseCode = "400", description = "Bad Request",
					content = {
							@Content(mediaType = "application/json",
							schema = @Schema(implementation = ErrorResponse.class)		)					
							}),
			@ApiResponse(responseCode = "401", description = "Unauthorized",
			content = {
					@Content(mediaType = "application/json",
					schema = @Schema(implementation = ErrorResponse.class)		)					
					}),
			@ApiResponse(responseCode = "409", description = "Conflict",
			content = {
					@Content(mediaType = "application/json",
					schema = @Schema(implementation = ErrorResponse.class)		)					
					}),
			@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = {
					@Content(mediaType = "application/json",
					schema = @Schema(implementation = ErrorResponse.class)		)					
					})
	})

	@PostMapping("/cuenta")
	public ResponseEntity<?> getConsultaCtaByNum(@RequestHeader(value = "X-Request-Id", required = false) String requestId,
			@Valid @RequestBody ConsultaCtaByNumRequest request){
		HttpHeaders headers = new HttpHeaders();
		errorContrato = "";
		errorServicio = "";
		
		if (requestId == null || requestId == ""){
			requestId = utils.generarCodigoTracerId();
		}
		LOGGER.info("Start ConsultaGralServiciosController : getConsultaServicios  RequestId :" + requestId);
		LOGGER.info("ConsultaGralContratosController Direccion IP : " + request.getIp());
		ResponseEntity<?> valiServiciosResponse = null;		
		ConsultaDtoRequest consultaDtoRequest = new ConsultaDtoRequest(); 
		consultaDtoRequest.setMoneda("");
		consultaDtoRequest.setCeduRif(request.getCeduRif());
		consultaDtoRequest.setHash(request.getHash());
		consultaDtoRequest.setIp(request.getIp());
		consultaDtoRequest.setNumCuenta(request.getNumCuenta());
		
		valiServiciosResponse = servicioServices.getConsulta(consultaDtoRequest, requestId);
		
	
		if(getValidaContrato(consultaDtoRequest,requestId,request.getIp())) {
			
			if(getValidaServices(consultaDtoRequest,requestId,request.getIp())){
				valiServiciosResponse = servicioServices.getConsulta(consultaDtoRequest, requestId);
			}else {
				ErrorResponse errorDto = new ErrorResponse();
				LOGGER.error("errorContrato  "+errorServicio);
				if(errorServicio.equals("") || !(errorServicio.contains("409"))) {
					errorDto.setCodigoError("180234");
					errorDto.setDescripcionError("Falla validando la afiliacion de Servicio");	
					LOGGER.error(" End  ConsultaGralServiciosController falla validando el servicio  : getConsultaServicios  RequestId :" + requestId);
		            	
				}else {
					errorDto = decoError(errorServicio,true);					
					LOGGER.error(" End  ConsultaGralServiciosController falla validando el servicio  : getConsultaServicios  RequestId :" + requestId);
				}
				headers.add("X-Request-Id", requestId);
	            return new ResponseEntity<ErrorResponse>(errorDto,headers, HttpStatus.CONFLICT);
			}			
		}else {
			ErrorResponse errorDto = new ErrorResponse();
			if(errorContrato.equals("") || !(errorContrato.contains("409"))) {
				errorDto.setCodigoError("180234");
				errorDto.setDescripcionError("Falla validando la afiliacion del Contrato");
				LOGGER.error(" End  ConsultaGralServiciosController falla validando el contrato : getConsultaServicios  RequestId :" + requestId);
			}else {
				errorDto = decoError(errorContrato,false);
				LOGGER.error(" End  ConsultaGralServiciosController falla validando el contrato : getConsultaServicios  RequestId :" + requestId);
			}
			headers.add("X-Request-Id", requestId);
            return new ResponseEntity<ErrorResponse>(errorDto,headers,HttpStatus.CONFLICT);		
		}
	
		LOGGER.info(" End  ConsultaGralServiciosController : getConsultaServicios  RequestId :" + requestId);
		
		return valiServiciosResponse;
		
	}
	
	
	public Boolean getValidaContrato(ConsultaDtoRequest consultaDtoRequest, String requestId, String ip)  {
		errorContrato = "";
		Boolean flag = Boolean.TRUE;
	     RestTemplate template = new RestTemplate();
		LOGGER.info("Start ConsultaCtasController : getValidaContrato  RequestId :" + requestId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, String> map = new HashMap<>();
		map.put("X-Request-Id", requestId);
		headers.setAll(map);
		URI uri;
		try {
			uri = new URI(UrlContrato);
			ContratoRequest contratoRequest = new ContratoRequest();
			contratoRequest.setClienteHash(consultaDtoRequest.getHash());
			contratoRequest.setClienteRIF(consultaDtoRequest.getCeduRif());
			contratoRequest.setIp(ip);
						HttpEntity<ContratoRequest> httpEntity = new HttpEntity<>(contratoRequest, headers);			
			ContratoResponse resp = template.postForObject(uri, httpEntity, ContratoResponse.class);			
			LOGGER.info("End ConsultaCtasController : getValidaContrato  RequestId :" + requestId);
			return flag;
			
		} catch (URISyntaxException e) {
			flag = Boolean.FALSE;
			LOGGER.error("End ConsultaCtasController : getValidaContrato  TracerId :" + requestId +" causa >> "+ e.getMessage());
		}catch (Exception e) {
			flag = Boolean.FALSE;
			errorContrato = e.getMessage();
			LOGGER.error("End ConsultaCtasController : getValidaContrato  TracerId :" + requestId +" causa >> "+ e.getMessage());
			
		}
	
		return flag;
    }
	
		
	public Boolean getValidaServices(ConsultaDtoRequest consultaDtoRequest , String requestId, String ip)  {
		Boolean flag = Boolean.TRUE;
		errorServicio = "";
	    RestTemplate template = new RestTemplate();
		LOGGER.info("Start ConsultaCtasController : getValidaServices  RequestId :" + requestId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, String> map = new HashMap<>();
		map.put("X-Request-Id", requestId);
		headers.setAll(map);
		URI uri;
		try {
			uri = new URI(UrlServicio);
			ServicioRequest servicioRequest = new ServicioRequest();
			servicioRequest.setClienteHash(consultaDtoRequest.getHash());
			servicioRequest.setClienteRIF(consultaDtoRequest.getCeduRif());
			servicioRequest.setNumeroCuenta(consultaDtoRequest.getNumCuenta());
			servicioRequest.setServicio(serviName);	
			servicioRequest.setIp(ip);
			HttpEntity<ServicioRequest> httpEntity = new HttpEntity<>(servicioRequest, headers);			
			List<ServicioResponse> resp = template.postForObject(uri, httpEntity, List.class);
			LOGGER.info("End ConsultaCtasController : getValidaServices  RequestId :" + requestId);
			return flag;			
		} catch (URISyntaxException e) {
			flag = Boolean.FALSE;
			LOGGER.error("End ConsultaCtasController : getValidaServices  TracerId :" + requestId +" causa >> "+ e.getMessage());		
		}catch (Exception e) {
			errorServicio = e.getMessage();
			flag = Boolean.FALSE;
			LOGGER.error("End ConsultaCtasController : getValidaServices  TracerId :" + requestId +" causa >> "+ e.getMessage());			
		}
		return flag;
    }
	
	 public ErrorResponse decoError(String mensaje, Boolean servicio) {
		 ErrorResponse errorDto = new ErrorResponse();
		 String codigo = "", descripcion="";
			
			if(mensaje.contains("409")) {
				codigo = mensaje.split(":")[2].split(",")[0].replaceAll("\"", "");
				errorDto.setCodigoError(codigo);
				descripcion = mensaje.split(":")[3].replaceAll("[{}]", "").replaceAll("\"", "");
				errorDto.setDescripcionError(descripcion);
				System.err.println("codi "+codigo+" descrp: "+descripcion);
			}
			return errorDto;
			
			
	 }
	
	

}