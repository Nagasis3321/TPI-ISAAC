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
@Table(name = "obra")
@NamedQueries({
    @NamedQuery(name = "Obra.findAll", query = "SELECT o FROM Obra o"),
    @NamedQuery(name = "Obra.findByIdObra", query = "SELECT o FROM Obra o WHERE o.idObra = :idObra"),
    @NamedQuery(name = "Obra.findByPlazo", query = "SELECT o FROM Obra o WHERE o.plazo = :plazo"),
    @NamedQuery(name = "Obra.findByFechaInicio", query = "SELECT o FROM Obra o WHERE o.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Obra.findByFinanciacion", query = "SELECT o FROM Obra o WHERE o.financiacion = :financiacion"),
    @NamedQuery(name = "Obra.findByDenominacion", query = "SELECT o FROM Obra o WHERE o.denominacion = :denominacion")})
public class Obra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idObra")
    private Integer idObra;
    @Basic(optional = false)
    @Column(name = "plazo")
    private String plazo;
    @Basic(optional = false)
    @Column(name = "fechaInicio")
    private String fechaInicio;
    @Basic(optional = false)
    @Column(name = "financiacion")
    private String financiacion;
    @Basic(optional = false)
    @Column(name = "denominacion")
    private String denominacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "obra")
    private Collection<Foja> fojaCollection;
    @JoinColumn(name = "cuitEmpresa", referencedColumnName = "cuit")
    @ManyToOne(optional = false)
    private Empresa cuitEmpresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "obra")
    private Collection<Item> itemCollection;

    public Obra() {
    }

    public Obra(Integer idObra) {
        this.idObra = idObra;
    }

    public Obra(String plazo, String fechaInicio, String financiacion, String denominacion) {
        this.plazo = plazo;
        this.fechaInicio = fechaInicio;
        this.financiacion = financiacion;
        this.denominacion = denominacion;
    }

    public Integer getIdObra() {
        return idObra;
    }

    public void setIdObra(Integer idObra) {
        this.idObra = idObra;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFinanciacion() {
        return financiacion;
    }

    public void setFinanciacion(String financiacion) {
        this.financiacion = financiacion;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public Collection<Foja> getFojaCollection() {
        return fojaCollection;
    }

    public void setFojaCollection(Collection<Foja> fojaCollection) {
        this.fojaCollection = fojaCollection;
    }

    public Empresa getCuitEmpresa() {
        return cuitEmpresa;
    }

    public void setCuitEmpresa(Empresa cuitEmpresa) {
        this.cuitEmpresa = cuitEmpresa;
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
        hash += (idObra != null ? idObra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Obra)) {
            return false;
        }
        Obra other = (Obra) object;
        if ((this.idObra == null && other.idObra != null) || (this.idObra != null && !this.idObra.equals(other.idObra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ModeloDB.Obra[ idObra=" + idObra + " ]";
    }
    
}
