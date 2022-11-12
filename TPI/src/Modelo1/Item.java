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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Isaac
 */
@Entity
@Table(name = "item")
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
    @NamedQuery(name = "Item.findByIdItem", query = "SELECT i FROM Item i WHERE i.idItem = :idItem"),
    @NamedQuery(name = "Item.findByIncidencia", query = "SELECT i FROM Item i WHERE i.incidencia = :incidencia"),
    @NamedQuery(name = "Item.findByCostoBase", query = "SELECT i FROM Item i WHERE i.costoBase = :costoBase"),
    @NamedQuery(name = "Item.findByPorcentajeFlete", query = "SELECT i FROM Item i WHERE i.porcentajeFlete = :porcentajeFlete"),
    @NamedQuery(name = "Item.findByPorcentajeGastos", query = "SELECT i FROM Item i WHERE i.porcentajeGastos = :porcentajeGastos"),
    @NamedQuery(name = "Item.findByPorcentajeUtilidad", query = "SELECT i FROM Item i WHERE i.porcentajeUtilidad = :porcentajeUtilidad")})
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idItem")
    private Integer idItem;
    @Column(name = "incidencia")
    private Integer incidencia;
    @Column(name = "costoBase")
    private Integer costoBase;
    @Column(name = "porcentajeFlete")
    private Integer porcentajeFlete;
    @Column(name = "porcentajeGastos")
    private Integer porcentajeGastos;
    @Column(name = "porcentajeUtilidad")
    private Integer porcentajeUtilidad;
    @JoinColumn(name = "idObra", referencedColumnName = "idObra")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Obra idObra;
    @JoinColumn(name = "idTipoitem", referencedColumnName = "idTipo item")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoItem idTipoitem;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemidItem", fetch = FetchType.LAZY)
    private List<Costo> costoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemidItem", fetch = FetchType.LAZY)
    private List<DetallesFoja> detallesFojaList;

    public Item() {
    }

    public Item(Integer idItem) {
        this.idItem = idItem;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
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

    public Obra getIdObra() {
        return idObra;
    }

    public void setIdObra(Obra idObra) {
        this.idObra = idObra;
    }

    public TipoItem getIdTipoitem() {
        return idTipoitem;
    }

    public void setIdTipoitem(TipoItem idTipoitem) {
        this.idTipoitem = idTipoitem;
    }

    public List<Costo> getCostoList() {
        return costoList;
    }

    public void setCostoList(List<Costo> costoList) {
        this.costoList = costoList;
    }

    public List<DetallesFoja> getDetallesFojaList() {
        return detallesFojaList;
    }

    public void setDetallesFojaList(List<DetallesFoja> detallesFojaList) {
        this.detallesFojaList = detallesFojaList;
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
        return "Modelo1.Item[ idItem=" + idItem + " ]";
    }
    
}
