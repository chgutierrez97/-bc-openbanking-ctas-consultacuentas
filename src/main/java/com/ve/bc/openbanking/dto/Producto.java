package com.ve.bc.openbanking.dto;

public class Producto {
   private String numeroCuenta;
   private String tipoProducto;
   private String subProducto;

   public String getNumeroCuenta() {
      return this.numeroCuenta;
   }

   public String getTipoProducto() {
      return this.tipoProducto;
   }

   public String getSubProducto() {
      return this.subProducto;
   }

   public void setNumeroCuenta(final String numeroCuenta) {
      this.numeroCuenta = numeroCuenta;
   }

   public void setTipoProducto(final String tipoProducto) {
      this.tipoProducto = tipoProducto;
   }

   public void setSubProducto(final String subProducto) {
      this.subProducto = subProducto;
   }

   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Producto)) {
         return false;
      } else {
         Producto other = (Producto)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$numeroCuenta = this.getNumeroCuenta();
            Object other$numeroCuenta = other.getNumeroCuenta();
            if (this$numeroCuenta == null) {
               if (other$numeroCuenta != null) {
                  return false;
               }
            } else if (!this$numeroCuenta.equals(other$numeroCuenta)) {
               return false;
            }

            Object this$tipoProducto = this.getTipoProducto();
            Object other$tipoProducto = other.getTipoProducto();
            if (this$tipoProducto == null) {
               if (other$tipoProducto != null) {
                  return false;
               }
            } else if (!this$tipoProducto.equals(other$tipoProducto)) {
               return false;
            }

            Object this$subProducto = this.getSubProducto();
            Object other$subProducto = other.getSubProducto();
            if (this$subProducto == null) {
               if (other$subProducto != null) {
                  return false;
               }
            } else if (!this$subProducto.equals(other$subProducto)) {
               return false;
            }

            return true;
         }
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof Producto;
   }

   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $numeroCuenta = this.getNumeroCuenta();
      result = result * 59 + ($numeroCuenta == null ? 43 : $numeroCuenta.hashCode());
      Object $tipoProducto = this.getTipoProducto();
      result = result * 59 + ($tipoProducto == null ? 43 : $tipoProducto.hashCode());
      Object $subProducto = this.getSubProducto();
      result = result * 59 + ($subProducto == null ? 43 : $subProducto.hashCode());
      return result;
   }

   public String toString() {
      return "Producto(numeroCuenta=" + this.getNumeroCuenta() + ", tipoProducto=" + this.getTipoProducto() + ", subProducto=" + this.getSubProducto() + ")";
   }
}
