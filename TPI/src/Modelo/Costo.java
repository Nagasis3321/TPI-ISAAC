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
public class Costo {
    private Integer id;
    private Integer valor;
    private Integer certificadoFinVigencia;
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Integer getCertificadoFinVigencia() {
        return certificadoFinVigencia;
    }

    public void setCertificadoFinVigencia(Integer certificadoFinVigencia) {
        this.certificadoFinVigencia = certificadoFinVigencia;
    }

    public Costo(Integer id, Integer costo, Item item) {
        this.id = id;
        this.valor = valor;
        this.item = item;
    }

    public Costo(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.id);
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
        final Costo other = (Costo) obj;
        return Objects.equals(this.id, other.id);
    }
}
