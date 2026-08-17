package com.ve.bc.openbanking.feignclient;

import com.ve.bc.openbanking.dto.ServicioRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import javax.servlet.http.HttpServletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
   name = "bc-openbanking-gral-servicios",
   path = "/afiliacionValidarServicio"
)
public interface AfiliacionServiciosFeignClient {
   @PostMapping
   ResponseEntity<?> getCosultaServicios(@RequestHeader(value = "X-Request-IP",required = true) String ip, @RequestHeader(value = "X-Request-Id",required = false) String tracerId, @RequestBody ServicioRequest request, HttpServletResponse response);
}
