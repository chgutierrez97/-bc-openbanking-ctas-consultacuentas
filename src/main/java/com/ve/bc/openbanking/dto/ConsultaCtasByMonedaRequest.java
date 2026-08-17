package com.ve.bc.openbanking.dto;

import javax.validation.constraints.NotBlank;

public class ConsultaCtasByMonedaRequest {
   private @NotBlank(
   message = " Es un dato requerido para la solicitd."
) String ip;
   private @NotBlank(
   message = " Es un dato requerido para la solicitd."
) String ceduRif;
   private @NotBlank(
   message = " Es un dato requerido para la solicitd."
) String hash;
   private String moneda;

   public String getIp() {
      return this.ip;
   }

   public String getCeduRif() {
      return this.ceduRif;
   }

   public String getHash() {
      return this.hash;
   }

   public String getMoneda() {
      return this.moneda;
   }

   public void setIp(final String ip) {
      this.ip = ip;
   }

   public void setCeduRif(final String ceduRif) {
      this.ceduRif = ceduRif;
   }

   public void setHash(final String hash) {
      this.hash = hash;
   }

   public void setMoneda(final String moneda) {
      this.moneda = moneda;
   }

   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ConsultaCtasByMonedaRequest)) {
         return false;
      } else {
         ConsultaCtasByMonedaRequest other = (ConsultaCtasByMonedaRequest)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$ip = this.getIp();
            Object other$ip = other.getIp();
            if (this$ip == null) {
               if (other$ip != null) {
                  return false;
               }
            } else if (!this$ip.equals(other$ip)) {
               return false;
            }

            Object this$ceduRif = this.getCeduRif();
            Object other$ceduRif = other.getCeduRif();
            if (this$ceduRif == null) {
               if (other$ceduRif != null) {
                  return false;
               }
            } else if (!this$ceduRif.equals(other$ceduRif)) {
               return false;
            }

            Object this$hash = this.getHash();
            Object other$hash = other.getHash();
            if (this$hash == null) {
               if (other$hash != null) {
                  return false;
               }
            } else if (!this$hash.equals(other$hash)) {
               return false;
            }

            Object this$moneda = this.getMoneda();
            Object other$moneda = other.getMoneda();
            if (this$moneda == null) {
               if (other$moneda != null) {
                  return false;
               }
            } else if (!this$moneda.equals(other$moneda)) {
               return false;
            }

            return true;
         }
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof ConsultaCtasByMonedaRequest;
   }

   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $ip = this.getIp();
      result = result * 59 + ($ip == null ? 43 : $ip.hashCode());
      Object $ceduRif = this.getCeduRif();
      result = result * 59 + ($ceduRif == null ? 43 : $ceduRif.hashCode());
      Object $hash = this.getHash();
      result = result * 59 + ($hash == null ? 43 : $hash.hashCode());
      Object $moneda = this.getMoneda();
      result = result * 59 + ($moneda == null ? 43 : $moneda.hashCode());
      return result;
   }

   public String toString() {
      return "ConsultaCtasByMonedaRequest(ip=" + this.getIp() + ", ceduRif=" + this.getCeduRif() + ", hash=" + this.getHash() + ", moneda=" + this.getMoneda() + ")";
   }
}
