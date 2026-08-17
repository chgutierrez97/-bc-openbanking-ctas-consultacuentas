package com.ve.bc.openbanking.service;

import com.ve.bc.openbanking.dto.ConsultaDtoRequest;
import com.ve.bc.openbanking.dto.ErrorResponse;
import com.ve.bc.openbanking.dto.RespuestaConsultaServiDto;
import com.ve.bc.openbanking.repo.ConsultaCtaRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ConsultaCtaServices {
   @Autowired
   ConsultaCtaRepository contratoRepository;
   private static final Logger LOGGER = LoggerFactory.getLogger(ConsultaCtaServices.class);
   @Autowired
   RestTemplate restTemplate;

   public ResponseEntity<?> getConsulta(ConsultaDtoRequest request, String tracerId) {
      new ErrorResponse();
      new RespuestaConsultaServiDto();
      new HashMap();
      new ArrayList();
      String moneda = request.getMoneda();
      HttpHeaders headers = new HttpHeaders();
      headers.add("X-Request-Id", tracerId);
      RespuestaConsultaServiDto respuestaConsultaServiDto = this.contratoRepository.getConsultaServicio(request, tracerId);
      if (respuestaConsultaServiDto.getError().getStatus().equals(Boolean.FALSE)) {
         if (respuestaConsultaServiDto.getCuentas().size() <= 0) {
            System.out.println("esta vaciaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa ");
         }

         List listConsulta;
         if (moneda != "" && Objects.nonNull(moneda)) {
            listConsulta = (List)respuestaConsultaServiDto.getCuentas().stream().filter((x) -> x.getMoneda().getCodigo().equals(moneda)).collect(Collectors.toList());
         } else {
            listConsulta = respuestaConsultaServiDto.getCuentas();
         }

         if (listConsulta.size() > 0) {
            return new ResponseEntity(listConsulta, headers, HttpStatus.OK);
         } else {
            ErrorResponse var9 = new ErrorResponse();
            var9.setCodigoError("145548");
            var9.setDescripcionError("No Existen registros asociados a tu busqueda");
            return new ResponseEntity(var9, headers, HttpStatus.NOT_FOUND);
         }
      } else {
         LOGGER.info(" >> " + respuestaConsultaServiDto.getError().toString());
         ErrorResponse errorDto = new ErrorResponse();
         errorDto.setCodigoError(respuestaConsultaServiDto.getError().getCodigoError());
         errorDto.setDescripcionError(respuestaConsultaServiDto.getError().getDescripcionError());
         return new ResponseEntity(errorDto, headers, HttpStatus.CONFLICT);
      }
   }
}
