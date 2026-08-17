package com.ve.bc.openbanking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceErroServicesException extends RuntimeException {
   private static final long serialVersionUID = 1L;
   private String nombreDelRecurso;
   private String nombeDelService;

   public ResourceErroServicesException(String nombreDelRecurso, String nombeDelService) {
      super(String.format("La clase %s Error al conectarce al metodo : '%s'", nombreDelRecurso, nombeDelService));
      this.nombreDelRecurso = nombreDelRecurso;
      this.nombeDelService = nombeDelService;
   }

   public String getNombreDelRecurso() {
      return this.nombreDelRecurso;
   }

   public void setNombreDelRecurso(String nombreDelRecurso) {
      this.nombreDelRecurso = nombreDelRecurso;
   }

   public String getNombeDelService() {
      return this.nombeDelService;
   }

   public void setNombeDelService(String nombeDelService) {
      this.nombeDelService = nombeDelService;
   }
}
