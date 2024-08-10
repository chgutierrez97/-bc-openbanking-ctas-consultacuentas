package com.ve.bc.openbanking.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ve.bc.openbanking.dto.ServicioResponse;
import com.ve.bc.openbanking.dto.ServicioRequest;
import com.ve.bc.openbanking.dto.ConsultaCtasRequest;
import com.ve.bc.openbanking.dto.ConsultaDatosCtaRequest;
import com.ve.bc.openbanking.dto.ConsultaDtoRequest;
import com.ve.bc.openbanking.dto.ErrorResponse;
import com.ve.bc.openbanking.dto.ResponseConsutaCtas;
import com.ve.bc.openbanking.service.ServicioServices;
import com.ve.bc.openbanking.utils.Utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/consulta")

@Tag(name = "Validacion Servicios")
public class ConsultaCtasController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultaCtasController.class);

	@Autowired
	Utils utils;
	
	@Autowired
	ServicioServices servicioServices;
	
	
	
	@Operation(summary = "${api.doc.summary.servi.contr}", description = "${api.doc.description.servi.contr}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK",
					content = {
							@Content(mediaType = "application/json",
							schema = @Schema(implementation = ServicioResponse.class)		)					
							}),
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
	@PostMapping("/ctasCliente")
	public ResponseEntity<?> getCosultaServicios(@RequestHeader(value = "X-Request-IP", required = true) String ip,@RequestHeader(value = "X-ClienteRIF", required = true) String clienteRif,@RequestHeader(value = "X-Cliente-HasH", required = true) String clienteHash,@RequestHeader(value = "X-Request-Id", required = false) String requestId,
			@Valid @RequestBody ConsultaCtasRequest request, HttpServletResponse response){
		
		if (requestId == null || requestId == ""){
			requestId = utils.generarCodigoTracerId();
		}
		ConsultaDtoRequest consultaDtoRequest = new ConsultaDtoRequest(); 
		consultaDtoRequest.setCodMoneda(request.getCodMoneda());
		consultaDtoRequest.setCeduRif(clienteRif);
		consultaDtoRequest.setHash(clienteHash);
		consultaDtoRequest.setIp(ip);
		consultaDtoRequest.setNumCuenta("");

		LOGGER.info("Start ConsultaGralServiciosController : getCosultaServicios  RequestId :" + requestId);
		LOGGER.info("ConsultaGralContratosController Direccion IP : " + ip);
		ResponseEntity<?> valiServiciosResponse = servicioServices.getConsulta(consultaDtoRequest, requestId);		
		LOGGER.info(" End  ConsultaGralServiciosController : getCosultaServicios  RequestId :" + requestId);
		response.setHeader("X-Request-Id", requestId);
		return valiServiciosResponse;
		
	}
	
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK",
					content = {
							@Content(mediaType = "application/json",
							schema = @Schema(implementation = ServicioResponse.class)		)					
							}),
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
	@PostMapping("/ConsultaNrodeCta")
	public ResponseEntity<?> getCosultaServicios3(@RequestHeader(value = "X-Request-IP", required = true) String ip,@RequestHeader(value = "X-ClienteRIF", required = true) String clienteRif,@RequestHeader(value = "X-Cliente-HasH", required = true) String clienteHash,@RequestHeader(value = "X-Request-Id", required = false) String requestId,
			@Valid @RequestBody ConsultaDatosCtaRequest request, HttpServletResponse response){
		
		if (requestId == null || requestId == ""){
			requestId = utils.generarCodigoTracerId();
		}
		
		ConsultaDtoRequest consultaDtoRequest = new ConsultaDtoRequest(); 
		consultaDtoRequest.setCodMoneda(request.getCodMoneda());
		consultaDtoRequest.setCeduRif(clienteRif);
		consultaDtoRequest.setHash(clienteHash);
		consultaDtoRequest.setIp(ip);
		consultaDtoRequest.setNumCuenta(request.getNumCuenta());
		LOGGER.info("Start ConsultaGralServiciosController : getCosultaServicios  RequestId :" + requestId);
		LOGGER.info("ConsultaGralContratosController Direccion IP : " + ip);
		ResponseEntity<?> valiServiciosResponse = servicioServices.getConsulta(consultaDtoRequest, requestId);		
		LOGGER.info(" End  ConsultaGralServiciosController : getCosultaServicios  RequestId :" + requestId);
		response.setHeader("X-Request-Id", requestId);
		return valiServiciosResponse;
		
	}
	
	
	
	
	

}