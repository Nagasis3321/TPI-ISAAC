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
@Table(name = "empresa")
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e"),
    @NamedQuery(name = "Empresa.findByCuit", query = "SELECT e FROM Empresa e WHERE e.cuit = :cuit"),
    @NamedQuery(name = "Empresa.findByRazonSocial", query = "SELECT e FROM Empresa e WHERE e.razonSocial = :razonSocial"),
    @NamedQuery(name = "Empresa.findByDireccion", query = "SELECT e FROM Empresa e WHERE e.direccion = :direccion"),
    @NamedQuery(name = "Empresa.findByRepresentateLegal", query = "SELECT e FROM Empresa e WHERE e.representateLegal = :representateLegal"),
    @NamedQuery(name = "Empresa.findByRepresentateTecnico", query = "SELECT e FROM Empresa e WHERE e.representateTecnico = :representateTecnico")})
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cuit")
    private Integer cuit;
    @Column(name = "razonSocial")
    private String razonSocial;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "representateLegal")
    private String representateLegal;
    @Column(name = "representateTecnico")
    private String representateTecnico;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresacuit", fetch = FetchType.LAZY)
    private List<Obra> obraList;

    public Empresa() {
    }

    public Empresa(Integer cuit) {
        this.cuit = cuit;
    }

    public Integer getCuit() {
        return cuit;
    }

    public void setCuit(Integer cuit) {
        this.cuit = cuit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRepresentateLegal() {
        return representateLegal;
    }

    public void setRepresentateLegal(String representateLegal) {
        this.representateLegal = representateLegal;
    }

    public String getRepresentateTecnico() {
        return representateTecnico;
    }

    public void setRepresentateTecnico(String representateTecnico) {
        this.representateTecnico = representateTecnico;
    }

    public List<Obra> getObraList() {
        return obraList;
    }

    public void setObraList(List<Obra> obraList) {
        this.obraList = obraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cuit != null ? cuit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.cuit == null && other.cuit != null) || (this.cuit != null && !this.cuit.equals(other.cuit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo1.Empresa[ cuit=" + cuit + " ]";
    }
    
}
