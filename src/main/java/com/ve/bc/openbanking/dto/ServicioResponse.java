package com.ve.bc.openbanking.dto;

public class ServicioResponse {
   private Integer id;
   private String descripcion;
   private String identificador;
   private String estado;
   private String nombre;

   public Integer getId() {
      return this.id;
   }

   public String getDescripcion() {
      return this.descripcion;
   }

   public String getIdentificador() {
      return this.identificador;
   }

   public String getEstado() {
      return this.estado;
   }

   public String getNombre() {
      return this.nombre;
   }

   public void setId(final Integer id) {
      this.id = id;
   }

   public void setDescripcion(final String descripcion) {
      this.descripcion = descripcion;
   }

   public void setIdentificador(final String identificador) {
      this.identificador = identificador;
   }

   public void setEstado(final String estado) {
      this.estado = estado;
   }

   public void setNombre(final String nombre) {
      this.nombre = nombre;
   }

   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof ServicioResponse)) {
         return false;
      } else {
         ServicioResponse other = (ServicioResponse)o;
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

            Object this$identificador = this.getIdentificador();
            Object other$identificador = other.getIdentificador();
            if (this$identificador == null) {
               if (other$identificador != null) {
                  return false;
               }
            } else if (!this$identificador.equals(other$identificador)) {
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

            Object this$nombre = this.getNombre();
            Object other$nombre = other.getNombre();
            if (this$nombre == null) {
               if (other$nombre != null) {
                  return false;
               }
            } else if (!this$nombre.equals(other$nombre)) {
               return false;
            }

            return true;
         }
      }
   }

   protected boolean canEqual(final Object other) {
      return other instanceof ServicioResponse;
   }

   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $descripcion = this.getDescripcion();
      result = result * 59 + ($descripcion == null ? 43 : $descripcion.hashCode());
      Object $identificador = this.getIdentificador();
      result = result * 59 + ($identificador == null ? 43 : $identificador.hashCode());
      Object $estado = this.getEstado();
      result = result * 59 + ($estado == null ? 43 : $estado.hashCode());
      Object $nombre = this.getNombre();
      result = result * 59 + ($nombre == null ? 43 : $nombre.hashCode());
      return result;
   }

   public String toString() {
      return "ServicioResponse(id=" + this.getId() + ", descripcion=" + this.getDescripcion() + ", identificador=" + this.getIdentificador() + ", estado=" + this.getEstado() + ", nombre=" + this.getNombre() + ")";
   }
}
