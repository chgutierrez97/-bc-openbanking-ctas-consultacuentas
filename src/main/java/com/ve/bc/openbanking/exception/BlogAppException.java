package com.ve.bc.openbanking.exception;

import org.springframework.http.HttpStatus;

public class BlogAppException extends RuntimeException {
   private static final long serialVersionUID = 1L;
   private HttpStatus estado;
   private String mensaje;

   public BlogAppException(HttpStatus estado, String mensaje) {
      this.estado = estado;
      this.mensaje = mensaje;
   }

   public BlogAppException(HttpStatus estado, String mensaje, String mensaje1) {
      this.estado = estado;
      this.mensaje = mensaje;
      this.mensaje = mensaje1;
   }

   public HttpStatus getEstado() {
      return this.estado;
   }

   public void setEstado(HttpStatus estado) {
      this.estado = estado;
   }

   public String getMensaje() {
      return this.mensaje;
   }

   public void setMensaje(String mensaje) {
      this.mensaje = mensaje;
   }

   public static long getSerialversionuid() {
      return 1L;
   }
}
