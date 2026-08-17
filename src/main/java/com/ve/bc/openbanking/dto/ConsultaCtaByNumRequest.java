package com.ve.bc.openbanking.dto;

import javax.validation.constraints.NotBlank;

public class ConsultaCtaByNumRequest {
   private @NotBlank(
   message = " Es un dato requerido para la solicitd."
) String ip;
   private @NotBlank(
   message = " Es un dato requerido para la solicitd."
) String ceduRif;
   private @NotBlank(
   message = " Es un dato requerido para la solicitd."
) String hash;
   private @NotBlank(
   message = " Es un dato requerido para la solicitd."
) String numCuenta;

   public String getIp() {
      return this.ip;
   }

   public String getCeduRif() {
      return this.ceduRif;
   }

   public String getHash() {
      return this.hash;
   }

   public String getNumCuenta() {
      return this.numCuenta;
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

   public void setNumCuenta(final String numCuenta) {
      this.numCuenta = numCuenta;
   }

   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ConsultaCtaByNumRequest)) {
         return false;
      } else {
         ConsultaCtaByNumRequest other = (ConsultaCtaByNumRequest)o;
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
      return other instanceof ConsultaCtaByNumRequest;
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
      Object $numCuenta = this.getNumCuenta();
      result = result * 59 + ($numCuenta == null ? 43 : $numCuenta.hashCode());
      return result;
   }

   public String toString() {
      return "ConsultaCtaByNumRequest(ip=" + this.getIp() + ", ceduRif=" + this.getCeduRif() + ", hash=" + this.getHash() + ", numCuenta=" + this.getNumCuenta() + ")";
   }
}
