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
@Table(name = "foja")
@NamedQueries({
    @NamedQuery(name = "Foja.findAll", query = "SELECT f FROM Foja f"),
    @NamedQuery(name = "Foja.findByIdFoja", query = "SELECT f FROM Foja f WHERE f.idFoja = :idFoja"),
    @NamedQuery(name = "Foja.findByFechaRealizacion", query = "SELECT f FROM Foja f WHERE f.fechaRealizacion = :fechaRealizacion")})
public class Foja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFoja")
    private Integer idFoja;
    @Column(name = "fechaRealizacion")
    private String fechaRealizacion;
    @JoinColumn(name = "Obra_idObra", referencedColumnName = "idObra")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Obra obraidObra;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fojaidFoja", fetch = FetchType.LAZY)
    private List<CertificadoPago> certificadoPagoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fojaidFoja", fetch = FetchType.LAZY)
    private List<DetallesFoja> detallesFojaList;

    public Foja() {
    }

    public Foja(Integer idFoja) {
        this.idFoja = idFoja;
    }

    public Integer getIdFoja() {
        return idFoja;
    }

    public void setIdFoja(Integer idFoja) {
        this.idFoja = idFoja;
    }

    public String getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(String fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public Obra getObraidObra() {
        return obraidObra;
    }

    public void setObraidObra(Obra obraidObra) {
        this.obraidObra = obraidObra;
    }

    public List<CertificadoPago> getCertificadoPagoList() {
        return certificadoPagoList;
    }

    public void setCertificadoPagoList(List<CertificadoPago> certificadoPagoList) {
        this.certificadoPagoList = certificadoPagoList;
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
        hash += (idFoja != null ? idFoja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Foja)) {
            return false;
        }
        Foja other = (Foja) object;
        if ((this.idFoja == null && other.idFoja != null) || (this.idFoja != null && !this.idFoja.equals(other.idFoja))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo1.Foja[ idFoja=" + idFoja + " ]";
    }
    
}
