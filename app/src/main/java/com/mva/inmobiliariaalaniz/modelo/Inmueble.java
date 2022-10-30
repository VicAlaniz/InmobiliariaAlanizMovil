package com.mva.inmobiliariaalaniz.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Inmueble implements Serializable {
    private int id;
    private String direccion;
    private String uso;
    private String tipo;
    private int cantAmbientes;
    private double coordenadaE;
    private double coordenadaN;
    private double precio;
    private Propietario duenio;
    private int propietarioId;
    //En falso significa que el innmueble no está disponible por alguna falla en el mismo.
    private boolean estado=true;
    private String imagen;

    public Inmueble() {
    }

    public int getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(int propietarioId) {
        this.propietarioId = propietarioId;
    }

    public Inmueble(int id, String direccion, String uso, String tipo, int cantAmbientes, double coordenadaE, double coordenadaN, double precio, Propietario duenio, int propietarioId, boolean estado, String imagen) {
        this.id = id;
        this.direccion = direccion;
        this.uso = uso;
        this.tipo = tipo;
        this.cantAmbientes = cantAmbientes;
        this.coordenadaE = coordenadaE;
        this.coordenadaN = coordenadaN;
        this.precio = precio;
        this.duenio = duenio;
        this.propietarioId = propietarioId;
        this.estado = estado;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantAmbientes() {
        return cantAmbientes;
    }

    public void setCantAmbientes(int cantAmbientes) {
        this.cantAmbientes = cantAmbientes;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Propietario getDuenio() {
        return duenio;
    }

    public void setDuenio(Propietario duenio) {
        this.duenio = duenio;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getCoordenadaE() {
        return coordenadaE;
    }

    public void setCoordenadaE(double coordenadaE) {
        this.coordenadaE = coordenadaE;
    }

    public double getCoordenadaN() {
        return coordenadaN;
    }

    public void setCoordenadaN(double coordenadaN) {
        this.coordenadaN = coordenadaN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inmueble inmueble = (Inmueble) o;
        return id == inmueble.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
