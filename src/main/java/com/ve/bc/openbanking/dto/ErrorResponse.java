package com.ve.bc.openbanking.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ErrorResponse {
	private String codigoError;
	private String descripcionError;
}
