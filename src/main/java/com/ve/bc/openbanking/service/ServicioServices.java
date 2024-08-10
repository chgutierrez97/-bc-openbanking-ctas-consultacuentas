package com.ve.bc.openbanking.service;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ve.bc.openbanking.dto.ServicioResponse;
import com.ve.bc.openbanking.dto.ServicioRequest;
import com.ve.bc.openbanking.dto.ConsultaDtoRequest;
import com.ve.bc.openbanking.dto.ErrorResponse;
import com.ve.bc.openbanking.dto.ResponseConsutaCtas;
import com.ve.bc.openbanking.dto.RespuestaConsultaServiDto;
import com.ve.bc.openbanking.exception.ResourceErroNoFoundServicesException;
import com.ve.bc.openbanking.repo.ServicioRepository;

@Component
public class ServicioServices {
	
	@Autowired
	ServicioRepository contratoRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServicioServices.class);
	@Autowired
	RestTemplate restTemplate;
	
	public ResponseEntity<?> getConsulta(ConsultaDtoRequest request, String tracerId) {
		RespuestaConsultaServiDto respuestaConsultaServiDto = new RespuestaConsultaServiDto();
		Map<String,String> error = new HashMap<>();
	
		if(true){
			if(true){
				respuestaConsultaServiDto = contratoRepository.getConsultaServicio(request, tracerId);
			}else {
				System.out.println("");
			}
		}else {
			System.out.println("");
		}
		
		
		
		if (respuestaConsultaServiDto.getError().getStatus().equals(Boolean.FALSE)) {
			return new ResponseEntity<List<ResponseConsutaCtas>>(respuestaConsultaServiDto.getCuentas(), HttpStatus.OK);
		} else {
			ErrorResponse errorDto = new ErrorResponse();
			errorDto.setCodigoError(respuestaConsultaServiDto.getError().getCodigoError());
			errorDto.setDescripcionError(respuestaConsultaServiDto.getError().getDescripcionError());	         
            return new ResponseEntity<ErrorResponse>(errorDto, HttpStatus.CONFLICT);
		}
	}

}

