/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.*;

/**
 *
 * @author USUARIO
 */
public class Item {
    private String denominacion;
    private Integer orden;
    private Integer incidencia;
    private Integer costoBase;
    private Integer id;
    private Integer porcentajeFlete, porcentajeGastos, porcentajeUtilidad;
    private TipoItem tipo;
    private ArrayList<Costo> costos = new ArrayList();

    public TipoItem getTipo() {
        return tipo;
    }

    public void setTipo(TipoItem tipo) {
        this.tipo = tipo;
    }

    public Item(String denominacion, Integer id, TipoItem tipo) {
        this.denominacion = denominacion;
        this.id = id;
        this.tipo = tipo;
        this.costos = null;
        
    }

    public Item(String denominacion, Integer orden, Integer incidencia, Integer costoBase, Integer id, Integer porcentajeFlete, Integer porcentajeGastos, Integer porcentajeUtilidad, TipoItem tipo) {
        this.denominacion = denominacion;
        this.orden = orden;
        this.incidencia = incidencia;
        this.costoBase = costoBase;
        this.id = id;
        this.porcentajeFlete = porcentajeFlete;
        this.porcentajeGastos = porcentajeGastos;
        this.porcentajeUtilidad = porcentajeUtilidad;
        this.tipo = tipo;
        this.costos = null;
    }

    public Integer getPorcentajeFlete() {
        return porcentajeFlete;
    }

    public void setPorcentajeFlete(Integer porcentajeFlete) {
        this.porcentajeFlete = porcentajeFlete;
    }

    public Integer getPorcentajeGastos() {
        return porcentajeGastos;
    }

    public void setPorcentajeGastos(Integer porcentajeGastos) {
        this.porcentajeGastos = porcentajeGastos;
    }

    public Integer getPorcentajeUtilidad() {
        return porcentajeUtilidad;
    }

    public void setPorcentajeUtilidad(Integer porcentajeUtilidad) {
        this.porcentajeUtilidad = porcentajeUtilidad;
    }
    
    
    
    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(Integer incidencia) {
        this.incidencia = incidencia;
    }

    public Integer getCostoBase() {
        return costoBase;
    }

    public void setCostoBase(Integer costoBase) {
        this.costoBase = costoBase;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Item(Integer id) {
        this.id = id;
    }

    public Item(String denominacion, Integer id) {
        this.denominacion = denominacion;
        this.id = id;
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        return Objects.equals(this.id, other.id);
    } 
}
