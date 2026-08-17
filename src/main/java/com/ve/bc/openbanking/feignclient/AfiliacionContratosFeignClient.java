package com.ve.bc.openbanking.feignclient;

import com.ve.bc.openbanking.dto.ContratoRequest;
import com.ve.bc.openbanking.dto.ContratoResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
   name = "bc-openbanking-gral-contratos",
   path = "/afiliacionValidarContrato"
)
public interface AfiliacionContratosFeignClient {
   @PostMapping
   ResponseEntity<ContratoResponse> getCosultaContratos(@RequestHeader(value = "X-Request-IP",required = true) String ip, @RequestHeader(value = "X-Request-Id",required = false) String tracerId, @RequestBody @Valid ContratoRequest request);
}
