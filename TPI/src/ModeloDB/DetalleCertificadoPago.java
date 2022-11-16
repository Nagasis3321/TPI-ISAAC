/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDB;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author matia
 */
@Entity
@Table(name = "detallecertificadopago")
@NamedQueries({
    @NamedQuery(name = "DetalleCertificadoPago.findAll", query = "SELECT d FROM DetalleCertificadoPago d"),
    @NamedQuery(name = "DetalleCertificadoPago.findByIdDetalleCertificadoPago", query = "SELECT d FROM DetalleCertificadoPago d WHERE d.idDetalleCertificadoPago = :idDetalleCertificadoPago")})
public class DetalleCertificadoPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDetalleCertificadoPago")
    private Integer idDetalleCertificadoPago;
    @JoinColumn(name = "certificadoPago", referencedColumnName = "idCertificadoPago")
    @ManyToOne(optional = false)
    private CertificadoPago certificadoPago;
    @JoinColumn(name = "costo", referencedColumnName = "idCosto")
    @ManyToOne(optional = false)
    private Costo costo;
    @JoinColumn(name = "detalleFoja", referencedColumnName = "idDetalleFoja")
    @ManyToOne(optional = false)
    private DetalleFoja detalleFoja;

    public DetalleCertificadoPago() {
    }

    public DetalleCertificadoPago(Integer idDetalleCertificadoPago) {
        this.idDetalleCertificadoPago = idDetalleCertificadoPago;
    }

    public Integer getIdDetalleCertificadoPago() {
        return idDetalleCertificadoPago;
    }

    public void setIdDetalleCertificadoPago(Integer idDetalleCertificadoPago) {
        this.idDetalleCertificadoPago = idDetalleCertificadoPago;
    }

    public CertificadoPago getCertificadoPago() {
        return certificadoPago;
    }

    public void setCertificadoPago(CertificadoPago certificadoPago) {
        this.certificadoPago = certificadoPago;
    }

    public Costo getCosto() {
        return costo;
    }

    public void setCosto(Costo costo) {
        this.costo = costo;
    }

    public DetalleFoja getDetalleFoja() {
        return detalleFoja;
    }

    public void setDetalleFoja(DetalleFoja detalleFoja) {
        this.detalleFoja = detalleFoja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleCertificadoPago != null ? idDetalleCertificadoPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleCertificadoPago)) {
            return false;
        }
        DetalleCertificadoPago other = (DetalleCertificadoPago) object;
        if ((this.idDetalleCertificadoPago == null && other.idDetalleCertificadoPago != null) || (this.idDetalleCertificadoPago != null && !this.idDetalleCertificadoPago.equals(other.idDetalleCertificadoPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ModeloDB.DetalleCertificadoPago[ idDetalleCertificadoPago=" + idDetalleCertificadoPago + " ]";
    }
    
}
