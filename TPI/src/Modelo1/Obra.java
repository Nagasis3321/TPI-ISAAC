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
@Table(name = "obra")
@NamedQueries({
    @NamedQuery(name = "Obra.findAll", query = "SELECT o FROM Obra o"),
    @NamedQuery(name = "Obra.findByIdObra", query = "SELECT o FROM Obra o WHERE o.idObra = :idObra"),
    @NamedQuery(name = "Obra.findByEmpresa", query = "SELECT o FROM Obra o WHERE o.empresa = :empresa"),
    @NamedQuery(name = "Obra.findByPlazo", query = "SELECT o FROM Obra o WHERE o.plazo = :plazo"),
    @NamedQuery(name = "Obra.findByInicio", query = "SELECT o FROM Obra o WHERE o.inicio = :inicio"),
    @NamedQuery(name = "Obra.findByFinanza", query = "SELECT o FROM Obra o WHERE o.finanza = :finanza")})
public class Obra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idObra")
    private Integer idObra;
    @Column(name = "empresa")
    private String empresa;
    @Column(name = "plazo")
    private String plazo;
    @Column(name = "inicio")
    private String inicio;
    @Column(name = "finanza")
    private String finanza;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "obraidObra", fetch = FetchType.LAZY)
    private List<Foja> fojaList;
    @JoinColumn(name = "Empresa_cuit", referencedColumnName = "cuit")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empresa empresacuit;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idObra", fetch = FetchType.LAZY)
    private List<Item> itemList;

    public Obra() {
    }

    public Obra(Integer idObra) {
        this.idObra = idObra;
    }

    public Integer getIdObra() {
        return idObra;
    }

    public void setIdObra(Integer idObra) {
        this.idObra = idObra;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFinanza() {
        return finanza;
    }

    public void setFinanza(String finanza) {
        this.finanza = finanza;
    }

    public List<Foja> getFojaList() {
        return fojaList;
    }

    public void setFojaList(List<Foja> fojaList) {
        this.fojaList = fojaList;
    }

    public Empresa getEmpresacuit() {
        return empresacuit;
    }

    public void setEmpresacuit(Empresa empresacuit) {
        this.empresacuit = empresacuit;
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
        return "Modelo1.Obra[ idObra=" + idObra + " ]";
    }
    
}
