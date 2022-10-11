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
public class Empresa {
    private String razonSocial;
    private Integer cuit;
    private String direccion;
    private String representanteLegal;
    private String representanteTecnico;
    private ArrayList<Obra> obras = new ArrayList();

    public Empresa(String razonSocial, Integer cuit) {
        this.razonSocial = razonSocial;
        this.cuit = cuit;
    }

    public Empresa(String razonSocial, Integer cuit, String direccion) {
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.direccion = direccion;
    }

    
    
    
    
    
    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Integer getCuit() {
        return cuit;
    }

    public void setCuit(Integer cuit) {
        this.cuit = cuit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    public String getRepresentanteTecnico() {
        return representanteTecnico;
    }

    public void setRepresentanteTecnico(String representanteTecnico) {
        this.representanteTecnico = representanteTecnico;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.cuit);
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
        final Empresa other = (Empresa) obj;
        return Objects.equals(this.cuit, other.cuit);
    }
}