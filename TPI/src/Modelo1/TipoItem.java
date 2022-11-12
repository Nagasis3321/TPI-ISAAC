/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo1;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Isaac
 */
@Entity
@Table(name = "tipo item")
@NamedQueries({
    @NamedQuery(name = "TipoItem.findAll", query = "SELECT t FROM TipoItem t"),
    @NamedQuery(name = "TipoItem.findByIdTipoitem", query = "SELECT t FROM TipoItem t WHERE t.idTipoitem = :idTipoitem"),
    @NamedQuery(name = "TipoItem.findByDenominacion", query = "SELECT t FROM TipoItem t WHERE t.denominacion = :denominacion")})
public class TipoItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipo item")
    private Integer idTipoitem;
    @Column(name = "denominacion")
    private String denominacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoitem", fetch = FetchType.LAZY)
    private List<Item> itemList;

    public TipoItem() {
    }

    public TipoItem(Integer idTipoitem) {
        this.idTipoitem = idTipoitem;
    }

    public Integer getIdTipoitem() {
        return idTipoitem;
    }

    public void setIdTipoitem(Integer idTipoitem) {
        this.idTipoitem = idTipoitem;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoitem != null ? idTipoitem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoItem)) {
            return false;
        }
        TipoItem other = (TipoItem) object;
        if ((this.idTipoitem == null && other.idTipoitem != null) || (this.idTipoitem != null && !this.idTipoitem.equals(other.idTipoitem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo1.TipoItem[ idTipoitem=" + idTipoitem + " ]";
    }
    
}
