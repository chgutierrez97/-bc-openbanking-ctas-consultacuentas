package com.ve.bc.openbanking.dto;

import java.util.List;

import lombok.Data;
@Data
public class RespuestaConsultaServiDto {
	
	private RespuestaConError error;
	private List<ResponseConsutaCtas> Cuentas; 

}
