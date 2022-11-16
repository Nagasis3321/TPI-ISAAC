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
@Table(name = "certificadopago")
@NamedQueries({
    @NamedQuery(name = "CertificadoPago.findAll", query = "SELECT c FROM CertificadoPago c"),
    @NamedQuery(name = "CertificadoPago.findByIdCertificadoPago", query = "SELECT c FROM CertificadoPago c WHERE c.idCertificadoPago = :idCertificadoPago"),
    @NamedQuery(name = "CertificadoPago.findByFechaRealizacion", query = "SELECT c FROM CertificadoPago c WHERE c.fechaRealizacion = :fechaRealizacion")})
public class CertificadoPago implements Serializable {

    @JoinColumn(name = "foja", referencedColumnName = "idFoja")
    @ManyToOne(optional = false)
    private Foja foja;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "certificadoPago")
    private Collection<DetalleCertificadoPago> detalleCertificadoPagoCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCertificadoPago")
    private Integer idCertificadoPago;
    @Basic(optional = false)
    @Column(name = "fechaRealizacion")
    private String fechaRealizacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "certificadoPago")
    private Collection<Foja> fojaCollection;
    @OneToMany(mappedBy = "certificadoFinVigencia")
    private Collection<Costo> costoCollection;

    public CertificadoPago() {
    }

    public CertificadoPago(Integer idCertificadoPago) {
        this.idCertificadoPago = idCertificadoPago;
    }

    public CertificadoPago(Integer idCertificadoPago, String fechaRealizacion) {
        this.idCertificadoPago = idCertificadoPago;
        this.fechaRealizacion = fechaRealizacion;
    }

    public Integer getIdCertificadoPago() {
        return idCertificadoPago;
    }

    public void setIdCertificadoPago(Integer idCertificadoPago) {
        this.idCertificadoPago = idCertificadoPago;
    }

    public String getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(String fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public Collection<Foja> getFojaCollection() {
        return fojaCollection;
    }

    public void setFojaCollection(Collection<Foja> fojaCollection) {
        this.fojaCollection = fojaCollection;
    }

    public Collection<Costo> getCostoCollection() {
        return costoCollection;
    }

    public void setCostoCollection(Collection<Costo> costoCollection) {
        this.costoCollection = costoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCertificadoPago != null ? idCertificadoPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CertificadoPago)) {
            return false;
        }
        CertificadoPago other = (CertificadoPago) object;
        if ((this.idCertificadoPago == null && other.idCertificadoPago != null) || (this.idCertificadoPago != null && !this.idCertificadoPago.equals(other.idCertificadoPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ModeloDB.CertificadoPago[ idCertificadoPago=" + idCertificadoPago + " ]";
    }

    public Foja getFoja() {
        return foja;
    }

    public void setFoja(Foja foja) {
        this.foja = foja;
    }

    public Collection<DetalleCertificadoPago> getDetalleCertificadoPagoCollection() {
        return detalleCertificadoPagoCollection;
    }

    public void setDetalleCertificadoPagoCollection(Collection<DetalleCertificadoPago> detalleCertificadoPagoCollection) {
        this.detalleCertificadoPagoCollection = detalleCertificadoPagoCollection;
    }
    
}
