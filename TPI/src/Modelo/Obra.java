/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author USUARIO
 */
public class Obra {
    private  ArrayList<Foja> fojas;
    private  ArrayList<Item> items;
    private Empresa empresa;
    private Integer id;
    private String denominacion;

    public Obra(ArrayList<Foja> fojas, ArrayList<Item> items, Empresa empresa, Integer id, String denominacion) {
        this.fojas = fojas;
        this.items = items;
        this.empresa = empresa;
        this.id = id;
        this.denominacion = denominacion;
    }

    public Obra() {
    }
    
    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    
    
    public ArrayList<Foja> getFojas() {
        return fojas;
    }

    public void setFojas(ArrayList<Foja> fojas) {
        this.fojas = fojas;
    }
    
    public void setFoja(Foja fojas) {
        this.fojas.add(fojas);
    }
    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
    
    public void setItem(Item items) {
        this.items.add(items);
    }


    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final Obra other = (Obra) obj;
        return Objects.equals(this.id, other.id);
    }

    public Obra(ArrayList<Item> items, Empresa empresa, Integer id) {
        this.items = items;
        this.empresa = empresa;
        this.id = id;
    }

    public Obra(Empresa empresa, Integer id) {
        this.empresa = empresa;
        this.id = id;
    }

}
