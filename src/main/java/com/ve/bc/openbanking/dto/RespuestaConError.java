package com.ve.bc.openbanking.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RespuestaConError {
	private Boolean status;
	private String codigoError;
	private String descripcionError;
}
