package com.ve.bc.openbanking.dto;

public class ResponseConsutaCtas {
   private Producto producto;
   private Moneda moneda;
   private Oficina oficina;
   private Estado estado;

   public Producto getProducto() {
      return this.producto;
   }

   public Moneda getMoneda() {
      return this.moneda;
   }

   public Oficina getOficina() {
      return this.oficina;
   }

   public Estado getEstado() {
      return this.estado;
   }

   public void setProducto(final Producto producto) {
      this.producto = producto;
   }

   public void setMoneda(final Moneda moneda) {
      this.moneda = moneda;
   }

   public void setOficina(final Oficina oficina) {
      this.oficina = oficina;
   }

   public void setEstado(final Estado estado) {
      this.estado = estado;
   }

   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ResponseConsutaCtas)) {
         return false;
      } else {
         ResponseConsutaCtas other = (ResponseConsutaCtas)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$producto = this.getProducto();
            Object other$producto = other.getProducto();
            if (this$producto == null) {
               if (other$producto != null) {
                  return false;
               }
            } else if (!this$producto.equals(other$producto)) {
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

            Object this$oficina = this.getOficina();
            Object other$oficina = other.getOficina();
            if (this$oficina == null) {
               if (other$oficina != null) {
                  return false;
               }
            } else if (!this$oficina.equals(other$oficina)) {
               return false;
            }

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
      return other instanceof ResponseConsutaCtas;
   }

   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $producto = this.getProducto();
      result = result * 59 + ($producto == null ? 43 : $producto.hashCode());
      Object $moneda = this.getMoneda();
      result = result * 59 + ($moneda == null ? 43 : $moneda.hashCode());
      Object $oficina = this.getOficina();
      result = result * 59 + ($oficina == null ? 43 : $oficina.hashCode());
      Object $estado = this.getEstado();
      result = result * 59 + ($estado == null ? 43 : $estado.hashCode());
      return result;
   }

   public String toString() {
      return "ResponseConsutaCtas(producto=" + this.getProducto() + ", moneda=" + this.getMoneda() + ", oficina=" + this.getOficina() + ", estado=" + this.getEstado() + ")";
   }
}
