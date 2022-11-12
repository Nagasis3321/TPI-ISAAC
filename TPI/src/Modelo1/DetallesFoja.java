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
@Table(name = "detalles foja")
@NamedQueries({
    @NamedQuery(name = "DetallesFoja.findAll", query = "SELECT d FROM DetallesFoja d"),
    @NamedQuery(name = "DetallesFoja.findByIdDetallesfoja", query = "SELECT d FROM DetallesFoja d WHERE d.idDetallesfoja = :idDetallesfoja"),
    @NamedQuery(name = "DetallesFoja.findByTotalAnterior", query = "SELECT d FROM DetallesFoja d WHERE d.totalAnterior = :totalAnterior"),
    @NamedQuery(name = "DetallesFoja.findByTotalMes", query = "SELECT d FROM DetallesFoja d WHERE d.totalMes = :totalMes"),
    @NamedQuery(name = "DetallesFoja.findByTotalAcumulado", query = "SELECT d FROM DetallesFoja d WHERE d.totalAcumulado = :totalAcumulado")})
public class DetallesFoja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDetalles foja")
    private Integer idDetallesfoja;
    @Column(name = "totalAnterior")
    private Integer totalAnterior;
    @Column(name = "totalMes")
    private Integer totalMes;
    @Column(name = "totalAcumulado")
    private Integer totalAcumulado;
    @JoinColumn(name = "Foja_idFoja", referencedColumnName = "idFoja")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Foja fojaidFoja;
    @JoinColumn(name = "Item_idItem", referencedColumnName = "idItem")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Item itemidItem;

    public DetallesFoja() {
    }

    public DetallesFoja(Integer idDetallesfoja) {
        this.idDetallesfoja = idDetallesfoja;
    }

    public Integer getIdDetallesfoja() {
        return idDetallesfoja;
    }

    public void setIdDetallesfoja(Integer idDetallesfoja) {
        this.idDetallesfoja = idDetallesfoja;
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

    public void setTotalAcumulado(Integer totalAcumulado) {
        this.totalAcumulado = totalAcumulado;
    }

    public Foja getFojaidFoja() {
        return fojaidFoja;
    }

    public void setFojaidFoja(Foja fojaidFoja) {
        this.fojaidFoja = fojaidFoja;
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
        hash += (idDetallesfoja != null ? idDetallesfoja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetallesFoja)) {
            return false;
        }
        DetallesFoja other = (DetallesFoja) object;
        if ((this.idDetallesfoja == null && other.idDetallesfoja != null) || (this.idDetallesfoja != null && !this.idDetallesfoja.equals(other.idDetallesfoja))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo1.DetallesFoja[ idDetallesfoja=" + idDetallesfoja + " ]";
    }
    
}
