package com.ve.bc.openbanking.dto;

import javax.validation.constraints.NotBlank;

public class ContratoRequest {
   private @NotBlank(
   message = " Es un dato requerido para la solicitd."
) String ip;
   private @NotBlank(
   message = " Es un dato requerido para la solicitd."
) String clienteHash;
   private @NotBlank(
   message = " Es un dato requerido para la solicitd."
) String clienteRIF;

   public String getIp() {
      return this.ip;
   }

   public String getClienteHash() {
      return this.clienteHash;
   }

   public String getClienteRIF() {
      return this.clienteRIF;
   }

   public void setIp(final String ip) {
      this.ip = ip;
   }

   public void setClienteHash(final String clienteHash) {
      this.clienteHash = clienteHash;
   }

   public void setClienteRIF(final String clienteRIF) {
      this.clienteRIF = clienteRIF;
   }

   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ContratoRequest)) {
         return false;
      } else {
         ContratoRequest other = (ContratoRequest)o;
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

            Object this$clienteHash = this.getClienteHash();
            Object other$clienteHash = other.getClienteHash();
            if (this$clienteHash == null) {
               if (other$clienteHash != null) {
                  return false;
               }
            } else if (!this$clienteHash.equals(other$clienteHash)) {
               return false;
            }

            Object this$clienteRIF = this.getClienteRIF();
            Object other$clienteRIF = other.getClienteRIF();
            if (this$clienteRIF == null) {
               if (other$clienteRIF != null) {
                  return false;
               }
            } else if (!this$clienteRIF.equals(other$clienteRIF)) {
               return false;
            }

            return true;
         }
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof ContratoRequest;
   }

   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $ip = this.getIp();
      result = result * 59 + ($ip == null ? 43 : $ip.hashCode());
      Object $clienteHash = this.getClienteHash();
      result = result * 59 + ($clienteHash == null ? 43 : $clienteHash.hashCode());
      Object $clienteRIF = this.getClienteRIF();
      result = result * 59 + ($clienteRIF == null ? 43 : $clienteRIF.hashCode());
      return result;
   }

   public String toString() {
      return "ContratoRequest(ip=" + this.getIp() + ", clienteHash=" + this.getClienteHash() + ", clienteRIF=" + this.getClienteRIF() + ")";
   }

   public ContratoRequest(final String ip, final String clienteHash, final String clienteRIF) {
      this.ip = ip;
      this.clienteHash = clienteHash;
      this.clienteRIF = clienteRIF;
   }

   public ContratoRequest() {
   }
}
