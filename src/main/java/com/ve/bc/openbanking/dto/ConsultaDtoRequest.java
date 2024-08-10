package com.ve.bc.openbanking.dto;

import java.util.Date;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaDtoRequest {

	
	private Integer codMoneda;
	private String numCuenta;
	private String ip;
	private String ceduRif;
	private String hash;
	
	
}
