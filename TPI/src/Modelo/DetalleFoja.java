/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Objects;

/**
 *
 * @author USUARIO
 */
public class DetalleFoja {
    private Integer id;
    private Integer totalAnterior;
    private Integer totalMes;
    private Integer totalAcumulado;
    private Item item;
    private Foja foja;

    public DetalleFoja(Integer id, Integer totalAnterior, Integer totalMes, Integer totalAcumulado, Item item, Foja foja) {
        this.id = id;
        this.totalAnterior = totalAnterior;
        this.totalMes = totalMes;
        this.totalAcumulado = totalAcumulado;
        this.item = item;
        this.foja = foja;
    }

    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalAnterior() {
        return totalAnterior;
    }

    public void setTotalAnterior(Integer totalAnterior) {
        this.totalAnterior = totalAnterior;
    }

    public Integer getTotalMes() {
        return totalMes;
    }

    public void setTotalMes(Integer totalMes) {
        this.totalMes = totalMes;
    }

    public Integer getTotalAcumulado() {
        return totalAcumulado;
    }

    public void setTotalAlucmulado(Integer totalAlcumulado) {
        this.totalAcumulado = totalAlcumulado;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Foja getFoja() {
        return foja;
    }

    public void setFoja(Foja foja) {
        this.foja = foja;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final DetalleFoja other = (DetalleFoja) obj;
        return Objects.equals(this.id, other.id);
    }

    public DetalleFoja(Integer id, Item item, Foja foja) {
        this.id = id;
        this.item = item;
        this.foja = foja;
    }
    
    
}
