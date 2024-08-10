package com.ve.bc.openbanking.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResponseConsutaCtas {
	
    private Producto producto;
	private Moneda moneda;
	private Oficina oficina;
	private Estado estado;

}
