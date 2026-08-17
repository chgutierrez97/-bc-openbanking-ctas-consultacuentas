package com.ve.bc.openbanking.dto;

public class ConsultaCtasRequest {
   private Integer codMoneda;

   public Integer getCodMoneda() {
      return this.codMoneda;
   }

   public void setCodMoneda(final Integer codMoneda) {
      this.codMoneda = codMoneda;
   }

   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ConsultaCtasRequest)) {
         return false;
      } else {
         ConsultaCtasRequest other = (ConsultaCtasRequest)o;
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

            return true;
         }
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof ConsultaCtasRequest;
   }

   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $codMoneda = this.getCodMoneda();
      result = result * 59 + ($codMoneda == null ? 43 : $codMoneda.hashCode());
      return result;
   }

   public String toString() {
      return "ConsultaCtasRequest(codMoneda=" + this.getCodMoneda() + ")";
   }

   public ConsultaCtasRequest(final Integer codMoneda) {
      this.codMoneda = codMoneda;
   }

   public ConsultaCtasRequest() {
   }
}
