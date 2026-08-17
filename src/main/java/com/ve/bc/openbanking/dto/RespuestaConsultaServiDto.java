package com.ve.bc.openbanking.dto;

import java.util.List;

public class RespuestaConsultaServiDto {
   private RespuestaConError error;
   private List<ResponseConsutaCtas> Cuentas;

   public RespuestaConError getError() {
      return this.error;
   }

   public List<ResponseConsutaCtas> getCuentas() {
      return this.Cuentas;
   }

   public void setError(final RespuestaConError error) {
      this.error = error;
   }

   public void setCuentas(final List<ResponseConsutaCtas> Cuentas) {
      this.Cuentas = Cuentas;
   }

   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RespuestaConsultaServiDto)) {
         return false;
      } else {
         RespuestaConsultaServiDto other = (RespuestaConsultaServiDto)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$error = this.getError();
            Object other$error = other.getError();
            if (this$error == null) {
               if (other$error != null) {
                  return false;
               }
            } else if (!this$error.equals(other$error)) {
               return false;
            }

            Object this$Cuentas = this.getCuentas();
            Object other$Cuentas = other.getCuentas();
            if (this$Cuentas == null) {
               if (other$Cuentas != null) {
                  return false;
               }
            } else if (!this$Cuentas.equals(other$Cuentas)) {
               return false;
            }

            return true;
         }
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof RespuestaConsultaServiDto;
   }

   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $error = this.getError();
      result = result * 59 + ($error == null ? 43 : $error.hashCode());
      Object $Cuentas = this.getCuentas();
      result = result * 59 + ($Cuentas == null ? 43 : $Cuentas.hashCode());
      return result;
   }

   public String toString() {
      return "RespuestaConsultaServiDto(error=" + this.getError() + ", Cuentas=" + this.getCuentas() + ")";
   }
}
