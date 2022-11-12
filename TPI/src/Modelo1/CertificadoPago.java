/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo1;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;

/**
 *
 * @author Isaac
 */
@Entity
@Table(name = "certificado pago")
@NamedQueries({
    @NamedQuery(name = "CertificadoPago.findAll", query = "SELECT c FROM CertificadoPago c"),
    @NamedQuery(name = "CertificadoPago.findByIdCertificadopago", query = "SELECT c FROM CertificadoPago c WHERE c.idCertificadopago = :idCertificadopago")})
public class CertificadoPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCertificado pago")
    private Integer idCertificadopago;
    @JoinColumn(name = "Foja_idFoja", referencedColumnName = "idFoja")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Foja fojaidFoja;

    public CertificadoPago() {
    }

    public CertificadoPago(Integer idCertificadopago) {
        this.idCertificadopago = idCertificadopago;
    }

    public Integer getIdCertificadopago() {
        return idCertificadopago;
    }

    public void setIdCertificadopago(Integer idCertificadopago) {
        this.idCertificadopago = idCertificadopago;
    }

    public Foja getFojaidFoja() {
        return fojaidFoja;
    }

    public void setFojaidFoja(Foja fojaidFoja) {
        this.fojaidFoja = fojaidFoja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCertificadopago != null ? idCertificadopago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CertificadoPago)) {
            return false;
        }
        CertificadoPago other = (CertificadoPago) object;
        if ((this.idCertificadopago == null && other.idCertificadopago != null) || (this.idCertificadopago != null && !this.idCertificadopago.equals(other.idCertificadopago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo1.CertificadoPago[ idCertificadopago=" + idCertificadopago + " ]";
    }
    
}
