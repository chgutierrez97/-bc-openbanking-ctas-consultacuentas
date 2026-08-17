package com.ve.bc.openbanking.dto;

public class Moneda {
   private Integer id;
   private String descripcion;
   private String codigo;

   public Integer getId() {
      return this.id;
   }

   public String getDescripcion() {
      return this.descripcion;
   }

   public String getCodigo() {
      return this.codigo;
   }

   public void setId(final Integer id) {
      this.id = id;
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
      } else if (!(o instanceof Moneda)) {
         return false;
      } else {
         Moneda other = (Moneda)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$id = this.getId();
            Object other$id = other.getId();
            if (this$id == null) {
               if (other$id != null) {
                  return false;
               }
            } else if (!this$id.equals(other$id)) {
               return false;
            }

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
      return other instanceof Moneda;
   }

   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $descripcion = this.getDescripcion();
      result = result * 59 + ($descripcion == null ? 43 : $descripcion.hashCode());
      Object $codigo = this.getCodigo();
      result = result * 59 + ($codigo == null ? 43 : $codigo.hashCode());
      return result;
   }

   public String toString() {
      return "Moneda(id=" + this.getId() + ", descripcion=" + this.getDescripcion() + ", codigo=" + this.getCodigo() + ")";
   }
}
