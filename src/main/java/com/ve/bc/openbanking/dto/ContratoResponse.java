package com.ve.bc.openbanking.dto;

public class ContratoResponse {
   private String estado;

   public String getEstado() {
      return this.estado;
   }

   public void setEstado(final String estado) {
      this.estado = estado;
   }

   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ContratoResponse)) {
         return false;
      } else {
         ContratoResponse other = (ContratoResponse)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$estado = this.getEstado();
            Object other$estado = other.getEstado();
            if (this$estado == null) {
               if (other$estado != null) {
                  return false;
               }
            } else if (!this$estado.equals(other$estado)) {
               return false;
            }

            return true;
         }
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof ContratoResponse;
   }

   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $estado = this.getEstado();
      result = result * 59 + ($estado == null ? 43 : $estado.hashCode());
      return result;
   }

   public String toString() {
      return "ContratoResponse(estado=" + this.getEstado() + ")";
   }
}
