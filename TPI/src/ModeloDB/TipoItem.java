/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDB;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author matia
 */
@Entity
@Table(name = "tipoitem")
@NamedQueries({
    @NamedQuery(name = "TipoItem.findAll", query = "SELECT t FROM TipoItem t"),
    @NamedQuery(name = "TipoItem.findByIdTipoItem", query = "SELECT t FROM TipoItem t WHERE t.idTipoItem = :idTipoItem"),
    @NamedQuery(name = "TipoItem.findByDenominacion", query = "SELECT t FROM TipoItem t WHERE t.denominacion = :denominacion"),
    @NamedQuery(name = "TipoItem.findByImpuesto", query = "SELECT t FROM TipoItem t WHERE t.impuesto = :impuesto")})
public class TipoItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipoItem")
    private Integer idTipoItem;
    @Basic(optional = false)
    @Column(name = "denominacion")
    private String denominacion;
    @Basic(optional = false)
    @Column(name = "impuesto")
    private int impuesto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoItem")
    private Collection<Item> itemCollection;

    public TipoItem() {
    }

    public TipoItem(Integer idTipoItem) {
        this.idTipoItem = idTipoItem;
    }
    
    public TipoItem(String denominacion, int impuesto) {
        this.denominacion = denominacion;
        this.impuesto = impuesto;
    }

    public TipoItem(Integer idTipoItem, String denominacion, int impuesto) {
        this.idTipoItem = idTipoItem;
        this.denominacion = denominacion;
        this.impuesto = impuesto;
    }

    public Integer getIdTipoItem() {
        return idTipoItem;
    }

    public void setIdTipoItem(Integer idTipoItem) {
        this.idTipoItem = idTipoItem;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public int getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(int impuesto) {
        this.impuesto = impuesto;
    }

    public Collection<Item> getItemCollection() {
        return itemCollection;
    }

    public void setItemCollection(Collection<Item> itemCollection) {
        this.itemCollection = itemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoItem != null ? idTipoItem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoItem)) {
            return false;
        }
        TipoItem other = (TipoItem) object;
        if ((this.idTipoItem == null && other.idTipoItem != null) || (this.idTipoItem != null && !this.idTipoItem.equals(other.idTipoItem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ModeloDB.TipoItem[ idTipoItem=" + idTipoItem + " ]";
    }
    
}
