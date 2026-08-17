package com.ve.bc.openbanking.dto;

public class Estado {
   private String descripcion;
   private String codigo;

   public String getDescripcion() {
      return this.descripcion;
   }

   public String getCodigo() {
      return this.codigo;
   }

   public void setDescripcion(final String descripcion) {
      this.descripcion = descripcion;
   }

   public void setCodigo(final String codigo) {
      this.codigo = codigo;
   }

   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Estado)) {
         return false;
      } else {
         Estado other = (Estado)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$descripcion = this.getDescripcion();
            Object other$descripcion = other.getDescripcion();
            if (this$descripcion == null) {
               if (other$descripcion != null) {
                  return false;
               }
            } else if (!this$descripcion.equals(other$descripcion)) {
               return false;
            }

            Object this$codigo = this.getCodigo();
            Object other$codigo = other.getCodigo();
            if (this$codigo == null) {
               if (other$codigo != null) {
                  return false;
               }
            } else if (!this$codigo.equals(other$codigo)) {
               return false;
            }

            return true;
         }
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof Estado;
   }

   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $descripcion = this.getDescripcion();
      result = result * 59 + ($descripcion == null ? 43 : $descripcion.hashCode());
      Object $codigo = this.getCodigo();
      result = result * 59 + ($codigo == null ? 43 : $codigo.hashCode());
      return result;
   }

   public String toString() {
      return "Estado(descripcion=" + this.getDescripcion() + ", codigo=" + this.getCodigo() + ")";
   }
}
