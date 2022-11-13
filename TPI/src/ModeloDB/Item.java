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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author matia
 */
@Entity
@Table(name = "item")
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
    @NamedQuery(name = "Item.findByIdItem", query = "SELECT i FROM Item i WHERE i.idItem = :idItem"),
    @NamedQuery(name = "Item.findByIncidencia", query = "SELECT i FROM Item i WHERE i.incidencia = :incidencia"),
    @NamedQuery(name = "Item.findByOrden", query = "SELECT i FROM Item i WHERE i.orden = :orden"),
    @NamedQuery(name = "Item.findByDenominacion", query = "SELECT i FROM Item i WHERE i.denominacion = :denominacion")})
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idItem")
    private Integer idItem;
    @Basic(optional = false)
    @Column(name = "incidencia")
    private int incidencia;
    @Basic(optional = false)
    @Column(name = "orden")
    private int orden;
    @Basic(optional = false)
    @Column(name = "denominacion")
    private String denominacion;
    @JoinColumn(name = "obra", referencedColumnName = "idObra")
    @ManyToOne(optional = false)
    private Obra obra;
    @JoinColumn(name = "tipoItem", referencedColumnName = "idTipoItem")
    @ManyToOne(optional = false)
    private TipoItem tipoItem;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
    private Collection<Costo> costoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
    private Collection<DetalleFoja> detalleFojaCollection;

    public Item() {
    }

    public Item(Integer idItem) {
        this.idItem = idItem;
    }

    public Item(Integer idItem, int incidencia, int orden, String denominacion) {
        this.idItem = idItem;
        this.incidencia = incidencia;
        this.orden = orden;
        this.denominacion = denominacion;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public int getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(int incidencia) {
        this.incidencia = incidencia;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public TipoItem getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(TipoItem tipoItem) {
        this.tipoItem = tipoItem;
    }

    public Collection<Costo> getCostoCollection() {
        return costoCollection;
    }

    public void setCostoCollection(Collection<Costo> costoCollection) {
        this.costoCollection = costoCollection;
    }

    public Collection<DetalleFoja> getDetalleFojaCollection() {
        return detalleFojaCollection;
    }

    public void setDetalleFojaCollection(Collection<DetalleFoja> detalleFojaCollection) {
        this.detalleFojaCollection = detalleFojaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idItem != null ? idItem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.idItem == null && other.idItem != null) || (this.idItem != null && !this.idItem.equals(other.idItem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ModeloDB.Item[ idItem=" + idItem + " ]";
    }
    
}
