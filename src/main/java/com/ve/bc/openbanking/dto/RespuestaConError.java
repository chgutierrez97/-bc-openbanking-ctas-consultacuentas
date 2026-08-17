package com.ve.bc.openbanking.dto;

public class RespuestaConError {
   private Boolean status;
   private String codigoError;
   private String descripcionError;

   public Boolean getStatus() {
      return this.status;
   }

   public String getCodigoError() {
      return this.codigoError;
   }

   public String getDescripcionError() {
      return this.descripcionError;
   }

   public void setStatus(final Boolean status) {
      this.status = status;
   }

   public void setCodigoError(final String codigoError) {
      this.codigoError = codigoError;
   }

   public void setDescripcionError(final String descripcionError) {
      this.descripcionError = descripcionError;
   }

   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RespuestaConError)) {
         return false;
      } else {
         RespuestaConError other = (RespuestaConError)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$status = this.getStatus();
            Object other$status = other.getStatus();
            if (this$status == null) {
               if (other$status != null) {
                  return false;
               }
            } else if (!this$status.equals(other$status)) {
               return false;
            }

            Object this$codigoError = this.getCodigoError();
            Object other$codigoError = other.getCodigoError();
            if (this$codigoError == null) {
               if (other$codigoError != null) {
                  return false;
               }
            } else if (!this$codigoError.equals(other$codigoError)) {
               return false;
            }

            Object this$descripcionError = this.getDescripcionError();
            Object other$descripcionError = other.getDescripcionError();
            if (this$descripcionError == null) {
               if (other$descripcionError != null) {
                  return false;
               }
            } else if (!this$descripcionError.equals(other$descripcionError)) {
               return false;
            }

            return true;
         }
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof RespuestaConError;
   }

   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $status = this.getStatus();
      result = result * 59 + ($status == null ? 43 : $status.hashCode());
      Object $codigoError = this.getCodigoError();
      result = result * 59 + ($codigoError == null ? 43 : $codigoError.hashCode());
      Object $descripcionError = this.getDescripcionError();
      result = result * 59 + ($descripcionError == null ? 43 : $descripcionError.hashCode());
      return result;
   }

   public String toString() {
      return "RespuestaConError(status=" + this.getStatus() + ", codigoError=" + this.getCodigoError() + ", descripcionError=" + this.getDescripcionError() + ")";
   }
}
