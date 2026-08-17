package com.ve.bc.openbanking.dto;

public class ConsultaDatosCtaRequest {
   private Integer codMoneda;
   private String numCuenta;

   public Integer getCodMoneda() {
      return this.codMoneda;
   }

   public String getNumCuenta() {
      return this.numCuenta;
   }

   public void setCodMoneda(final Integer codMoneda) {
      this.codMoneda = codMoneda;
   }

   public void setNumCuenta(final String numCuenta) {
      this.numCuenta = numCuenta;
   }

   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ConsultaDatosCtaRequest)) {
         return false;
      } else {
         ConsultaDatosCtaRequest other = (ConsultaDatosCtaRequest)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$codMoneda = this.getCodMoneda();
            Object other$codMoneda = other.getCodMoneda();
            if (this$codMoneda == null) {
               if (other$codMoneda != null) {
                  return false;
               }
            } else if (!this$codMoneda.equals(other$codMoneda)) {
               return false;
            }

            Object this$numCuenta = this.getNumCuenta();
            Object other$numCuenta = other.getNumCuenta();
            if (this$numCuenta == null) {
               if (other$numCuenta != null) {
                  return false;
               }
            } else if (!this$numCuenta.equals(other$numCuenta)) {
               return false;
            }

            return true;
         }
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof ConsultaDatosCtaRequest;
   }

   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $codMoneda = this.getCodMoneda();
      result = result * 59 + ($codMoneda == null ? 43 : $codMoneda.hashCode());
      Object $numCuenta = this.getNumCuenta();
      result = result * 59 + ($numCuenta == null ? 43 : $numCuenta.hashCode());
      return result;
   }

   public String toString() {
      return "ConsultaDatosCtaRequest(codMoneda=" + this.getCodMoneda() + ", numCuenta=" + this.getNumCuenta() + ")";
   }

   public ConsultaDatosCtaRequest(final Integer codMoneda, final String numCuenta) {
      this.codMoneda = codMoneda;
      this.numCuenta = numCuenta;
   }

   public ConsultaDatosCtaRequest() {
   }
}
