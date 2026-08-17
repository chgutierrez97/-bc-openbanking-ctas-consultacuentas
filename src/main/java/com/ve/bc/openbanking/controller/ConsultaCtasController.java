package com.ve.bc.openbanking.controller;

import com.ve.bc.openbanking.dto.ConsultaCtaByNumRequest;
import com.ve.bc.openbanking.dto.ConsultaCtasByMonedaRequest;
import com.ve.bc.openbanking.dto.ConsultaDtoRequest;
import com.ve.bc.openbanking.dto.ContratoRequest;
import com.ve.bc.openbanking.dto.ContratoResponse;
import com.ve.bc.openbanking.dto.ErrorResponse;
import com.ve.bc.openbanking.dto.ResponseConsutaCtas;
import com.ve.bc.openbanking.dto.ServicioRequest;
import com.ve.bc.openbanking.dto.ServicioResponse;
import com.ve.bc.openbanking.service.ConsultaCtaServices;
import com.ve.bc.openbanking.utils.Utils;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping({"/ctasConsultaCuentas"})
@Tag(
   name = "Consulta Cuentas"
)
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

   @ApiResponses({@ApiResponse(
   responseCode = "200",
   content = {@Content(
   array = @ArraySchema(
   schema = @Schema(
   implementation = ResponseConsutaCtas.class
)
)
)},
   description = "Ok"
), @ApiResponse(
   responseCode = "400",
   description = "Bad Request",
   content = {@Content(
   mediaType = "application/json",
   schema = @Schema(
   implementation = ErrorResponse.class
)
)}
), @ApiResponse(
   responseCode = "401",
   description = "Unauthorized",
   content = {@Content(
   mediaType = "application/json",
   schema = @Schema(
   implementation = ErrorResponse.class
)
)}
), @ApiResponse(
   responseCode = "409",
   description = "Conflict",
   content = {@Content(
   mediaType = "application/json",
   schema = @Schema(
   implementation = ErrorResponse.class
)
)}
), @ApiResponse(
   responseCode = "500",
   description = "Internal Server Error",
   content = {@Content(
   mediaType = "application/json",
   schema = @Schema(
   implementation = ErrorResponse.class
)
)}
)})
   @PostMapping
   public ResponseEntity<?> getCosultaCuentas(@RequestHeader(value = "X-Request-Id",required = false) String requestId, @RequestBody @Valid ConsultaCtasByMonedaRequest request, HttpServletResponse response) {
      HttpHeaders headers = new HttpHeaders();
      ResponseEntity<?> valiServiciosResponse = null;
      if (requestId == null || requestId == "") {
         requestId = this.utils.generarCodigoTracerId();
      }

      ConsultaDtoRequest consultaDtoRequest = new ConsultaDtoRequest();
      consultaDtoRequest.setMoneda(request.getMoneda().toUpperCase());
      consultaDtoRequest.setCeduRif(request.getCeduRif());
      consultaDtoRequest.setHash(request.getHash());
      consultaDtoRequest.setIp(request.getIp());
      consultaDtoRequest.setNumCuenta("");
      LOGGER.info("Start ConsultaGralServiciosController : getConsultaServicios  RequestId :" + requestId);
      LOGGER.info("ConsultaGralContratosController Direccion IP : " + request.getIp());
      if (this.getValidaContrato(consultaDtoRequest, requestId, request.getIp())) {
         if (this.getValidaServices(consultaDtoRequest, requestId, request.getIp())) {
            valiServiciosResponse = this.servicioServices.getConsulta(consultaDtoRequest, requestId);
            LOGGER.info(" End  ConsultaGralServiciosController : getConsultaServicios  RequestId :" + requestId);
            return valiServiciosResponse;
         } else {
            ErrorResponse errorDto = new ErrorResponse();
            if (!this.errorServicio.equals("") && this.errorServicio.contains("409")) {
               errorDto = this.decoError(this.errorServicio, true);
               LOGGER.error(" End  ConsultaGralServiciosController falla validando el servicio  : getConsultaServicios  2 RequestId :" + requestId);
            } else {
               errorDto.setCodigoError("180233");
               errorDto.setDescripcionError("Falla validando la afiliacion de Servicio");
               LOGGER.error(" End  ConsultaGralServiciosController falla validando el servicio  : getConsultaServicios 1  RequestId :" + requestId);
            }

            headers.add("X-Request-Id", requestId);
            return new ResponseEntity(errorDto, headers, HttpStatus.CONFLICT);
         }
      } else {
         ErrorResponse errorDto = new ErrorResponse();
         if (!this.errorContrato.equals("") && this.errorContrato.contains("409")) {
            errorDto = this.decoError(this.errorContrato, false);
            LOGGER.error(" End  ConsultaGralServiciosController falla validando el contrato : getConsultaServicios  RequestId :" + requestId);
         } else {
            errorDto.setCodigoError("180234");
            errorDto.setDescripcionError("Falla validando la afiliacion del Contrato");
            LOGGER.error(" End  ConsultaGralServiciosController falla validando el contrato : getConsultaServicios  RequestId :" + requestId);
         }

         headers.add("X-Request-Id", requestId);
         return new ResponseEntity(errorDto, headers, HttpStatus.CONFLICT);
      }
   }

   @ApiResponses({@ApiResponse(
   responseCode = "200",
   content = {@Content(
   array = @ArraySchema(
   schema = @Schema(
   implementation = ResponseConsutaCtas.class
)
)
)},
   description = "Ok"
), @ApiResponse(
   responseCode = "400",
   description = "Bad Request",
   content = {@Content(
   mediaType = "application/json",
   schema = @Schema(
   implementation = ErrorResponse.class
)
)}
), @ApiResponse(
   responseCode = "401",
   description = "Unauthorized",
   content = {@Content(
   mediaType = "application/json",
   schema = @Schema(
   implementation = ErrorResponse.class
)
)}
), @ApiResponse(
   responseCode = "409",
   description = "Conflict",
   content = {@Content(
   mediaType = "application/json",
   schema = @Schema(
   implementation = ErrorResponse.class
)
)}
), @ApiResponse(
   responseCode = "500",
   description = "Internal Server Error",
   content = {@Content(
   mediaType = "application/json",
   schema = @Schema(
   implementation = ErrorResponse.class
)
)}
)})
   @PostMapping({"/cuenta"})
   public ResponseEntity<?> getConsultaCtaByNum(@RequestHeader(value = "X-Request-Id",required = false) String requestId, @RequestBody @Valid ConsultaCtaByNumRequest request) {
      HttpHeaders headers = new HttpHeaders();
      this.errorContrato = "";
      this.errorServicio = "";
      if (requestId == null || requestId == "") {
         requestId = this.utils.generarCodigoTracerId();
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
      this.servicioServices.getConsulta(consultaDtoRequest, requestId);
      if (this.getValidaContrato(consultaDtoRequest, requestId, request.getIp())) {
         if (this.getValidaServices(consultaDtoRequest, requestId, request.getIp())) {
            valiServiciosResponse = this.servicioServices.getConsulta(consultaDtoRequest, requestId);
            LOGGER.info(" End  ConsultaGralServiciosController : getConsultaServicios  RequestId :" + requestId);
            return valiServiciosResponse;
         } else {
            ErrorResponse errorDto = new ErrorResponse();
            LOGGER.error("errorContrato  " + this.errorServicio);
            if (!this.errorServicio.equals("") && this.errorServicio.contains("409")) {
               errorDto = this.decoError(this.errorServicio, true);
               LOGGER.error(" End  ConsultaGralServiciosController falla validando el servicio  : getConsultaServicios  RequestId :" + requestId);
            } else {
               errorDto.setCodigoError("180234");
               errorDto.setDescripcionError("Falla validando la afiliacion de Servicio");
               LOGGER.error(" End  ConsultaGralServiciosController falla validando el servicio  : getConsultaServicios  RequestId :" + requestId);
            }

            headers.add("X-Request-Id", requestId);
            return new ResponseEntity(errorDto, headers, HttpStatus.CONFLICT);
         }
      } else {
         ErrorResponse errorDto = new ErrorResponse();
         if (!this.errorContrato.equals("") && this.errorContrato.contains("409")) {
            errorDto = this.decoError(this.errorContrato, false);
            LOGGER.error(" End  ConsultaGralServiciosController falla validando el contrato : getConsultaServicios  RequestId :" + requestId);
         } else {
            errorDto.setCodigoError("180234");
            errorDto.setDescripcionError("Falla validando la afiliacion del Contrato");
            LOGGER.error(" End  ConsultaGralServiciosController falla validando el contrato : getConsultaServicios  RequestId :" + requestId);
         }

         headers.add("X-Request-Id", requestId);
         return new ResponseEntity(errorDto, headers, HttpStatus.CONFLICT);
      }
   }

   public Boolean getValidaContrato(ConsultaDtoRequest consultaDtoRequest, String requestId, String ip) {
      this.errorContrato = "";
      Boolean flag = Boolean.TRUE;
      RestTemplate template = new RestTemplate();
      LOGGER.info("Start ConsultaCtasController : getValidaContrato  RequestId :" + requestId);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      Map<String, String> map = new HashMap();
      map.put("X-Request-Id", requestId);
      headers.setAll(map);

      try {
         URI uri = new URI(this.UrlContrato);
         ContratoRequest contratoRequest = new ContratoRequest();
         contratoRequest.setClienteHash(consultaDtoRequest.getHash());
         contratoRequest.setClienteRIF(consultaDtoRequest.getCeduRif());
         contratoRequest.setIp(ip);
         HttpEntity<ContratoRequest> httpEntity = new HttpEntity(contratoRequest, headers);
         ContratoResponse resp = (ContratoResponse)template.postForObject(uri, httpEntity, ContratoResponse.class);
         LOGGER.info("End ConsultaCtasController : getValidaContrato  RequestId :" + requestId);
         return flag;
      } catch (URISyntaxException e) {
         flag = Boolean.FALSE;
         LOGGER.error("End ConsultaCtasController : getValidaContrato  TracerId :" + requestId + " causa >> " + e.getMessage());
      } catch (Exception e) {
         flag = Boolean.FALSE;
         this.errorContrato = e.getMessage();
         LOGGER.error("End ConsultaCtasController : getValidaContrato  TracerId :" + requestId + " causa >> " + e.getMessage());
      }

      return flag;
   }

   public Boolean getValidaServices(ConsultaDtoRequest consultaDtoRequest, String requestId, String ip) {
      Boolean flag = Boolean.TRUE;
      this.errorServicio = "";
      RestTemplate template = new RestTemplate();
      LOGGER.info("Start ConsultaCtasController : getValidaServices  RequestId :" + requestId);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      Map<String, String> map = new HashMap();
      map.put("X-Request-Id", requestId);
      headers.setAll(map);

      try {
         URI uri = new URI(this.UrlServicio);
         ServicioRequest servicioRequest = new ServicioRequest();
         servicioRequest.setClienteHash(consultaDtoRequest.getHash());
         servicioRequest.setClienteRIF(consultaDtoRequest.getCeduRif());
         servicioRequest.setNumeroCuenta(consultaDtoRequest.getNumCuenta());
         servicioRequest.setIdentificador(this.serviName);
         servicioRequest.setIp(ip);
         HttpEntity<ServicioRequest> httpEntity = new HttpEntity(servicioRequest, headers);
         List<ServicioResponse> resp = (List)template.postForObject(uri, httpEntity, List.class);
         LOGGER.info("End ConsultaCtasController : getValidaServices  RequestId :" + requestId);
         return flag;
      } catch (URISyntaxException e) {
         flag = Boolean.FALSE;
         LOGGER.error("End ConsultaCtasController : getValidaServices  TracerId :" + requestId + " causa >> " + e.getMessage());
      } catch (Exception e) {
         this.errorServicio = e.getMessage();
         flag = Boolean.FALSE;
         LOGGER.error("End ConsultaCtasController : getValidaServices  TracerId :" + requestId + " causa >> " + e.getMessage());
      }

      return flag;
   }

   public ErrorResponse decoError(String mensaje, Boolean servicio) {
      ErrorResponse errorDto = new ErrorResponse();
      String codigo = "";
      String descripcion = "";
      if (mensaje.contains("409")) {
         codigo = mensaje.split(":")[2].split(",")[0].replaceAll("\"", "");
         errorDto.setCodigoError(codigo);
         descripcion = mensaje.split(":")[3].replaceAll("[{}]", "").replaceAll("\"", "");
         errorDto.setDescripcionError(descripcion);
         System.err.println("codi " + codigo + " descrp: " + descripcion);
      }

      return errorDto;
   }
}
