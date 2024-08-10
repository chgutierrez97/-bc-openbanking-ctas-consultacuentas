package com.ve.bc.openbanking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Producto {
	private String numeroCuenta;
	private String tipoProducto;
	private String subProducto;
}
