/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo1;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Isaac
 */
@Entity
@Table(name = "costo")
@NamedQueries({
    @NamedQuery(name = "Costo.findAll", query = "SELECT c FROM Costo c"),
    @NamedQuery(name = "Costo.findByIdCosto", query = "SELECT c FROM Costo c WHERE c.idCosto = :idCosto"),
    @NamedQuery(name = "Costo.findByCosto", query = "SELECT c FROM Costo c WHERE c.costo = :costo"),
    @NamedQuery(name = "Costo.findByCertificadofinVigencia", query = "SELECT c FROM Costo c WHERE c.certificadofinVigencia = :certificadofinVigencia")})
public class Costo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCosto")
    private Integer idCosto;
    @Column(name = "costo")
    private Integer costo;
    @Column(name = "certificadofinVigencia")
    private Integer certificadofinVigencia;
    @JoinColumn(name = "Item_idItem", referencedColumnName = "idItem")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Item itemidItem;

    public Costo() {
    }

    public Costo(Integer idCosto) {
        this.idCosto = idCosto;
    }

    public Integer getIdCosto() {
        return idCosto;
    }

    public void setIdCosto(Integer idCosto) {
        this.idCosto = idCosto;
    }

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    public Integer getCertificadofinVigencia() {
        return certificadofinVigencia;
    }

    public void setCertificadofinVigencia(Integer certificadofinVigencia) {
        this.certificadofinVigencia = certificadofinVigencia;
    }

    public Item getItemidItem() {
        return itemidItem;
    }

    public void setItemidItem(Item itemidItem) {
        this.itemidItem = itemidItem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCosto != null ? idCosto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Costo)) {
            return false;
        }
        Costo other = (Costo) object;
        if ((this.idCosto == null && other.idCosto != null) || (this.idCosto != null && !this.idCosto.equals(other.idCosto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo1.Costo[ idCosto=" + idCosto + " ]";
    }
    
}
