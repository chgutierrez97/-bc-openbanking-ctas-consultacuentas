package com.ve.bc.openbanking.dto;

public class ResponseContratoCts {
   private RespuestaConError errorConsulta;
   private ServicioResponse datosContrato;

   public RespuestaConError getErrorConsulta() {
      return this.errorConsulta;
   }

   public ServicioResponse getDatosContrato() {
      return this.datosContrato;
   }

   public void setErrorConsulta(final RespuestaConError errorConsulta) {
      this.errorConsulta = errorConsulta;
   }

   public void setDatosContrato(final ServicioResponse datosContrato) {
      this.datosContrato = datosContrato;
   }

   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ResponseContratoCts)) {
         return false;
      } else {
         ResponseContratoCts other = (ResponseContratoCts)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$errorConsulta = this.getErrorConsulta();
            Object other$errorConsulta = other.getErrorConsulta();
            if (this$errorConsulta == null) {
               if (other$errorConsulta != null) {
                  return false;
               }
            } else if (!this$errorConsulta.equals(other$errorConsulta)) {
               return false;
            }

            Object this$datosContrato = this.getDatosContrato();
            Object other$datosContrato = other.getDatosContrato();
            if (this$datosContrato == null) {
               if (other$datosContrato != null) {
                  return false;
               }
            } else if (!this$datosContrato.equals(other$datosContrato)) {
               return false;
            }

            return true;
         }
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof ResponseContratoCts;
   }

   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $errorConsulta = this.getErrorConsulta();
      result = result * 59 + ($errorConsulta == null ? 43 : $errorConsulta.hashCode());
      Object $datosContrato = this.getDatosContrato();
      result = result * 59 + ($datosContrato == null ? 43 : $datosContrato.hashCode());
      return result;
   }

   public String toString() {
      return "ResponseContratoCts(errorConsulta=" + this.getErrorConsulta() + ", datosContrato=" + this.getDatosContrato() + ")";
   }
}
