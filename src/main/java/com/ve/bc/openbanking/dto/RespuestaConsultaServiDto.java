package com.ve.bc.openbanking.dto;

import java.util.List;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class RespuestaConsultaServiDto {
	
	private RespuestaConError error;
	private List<ResponseConsutaCtas> Cuentas; 

}
