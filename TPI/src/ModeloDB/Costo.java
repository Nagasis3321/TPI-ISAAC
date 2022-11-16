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
@Table(name = "costo")
@NamedQueries({
    @NamedQuery(name = "Costo.findAll", query = "SELECT c FROM Costo c"),
    @NamedQuery(name = "Costo.findByIdCosto", query = "SELECT c FROM Costo c WHERE c.idCosto = :idCosto"),
    @NamedQuery(name = "Costo.findByValor", query = "SELECT c FROM Costo c WHERE c.valor = :valor")})
public class Costo implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "costo")
    private Collection<DetalleCertificadoPago> detalleCertificadoPagoCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCosto")
    private Integer idCosto;
    @Basic(optional = false)
    @Column(name = "valor")
    private int valor;
    @JoinColumn(name = "certificadoFinVigencia", referencedColumnName = "idCertificadoPago")
    @ManyToOne
    private CertificadoPago certificadoFinVigencia;
    @JoinColumn(name = "item", referencedColumnName = "idItem")
    @ManyToOne(optional = false)
    private Item item;

    public Costo() {
    }

    public Costo(Integer valor) {
        this.valor = valor;
    }

    public Costo(Integer idCosto, int valor) {
        this.idCosto = idCosto;
        this.valor = valor;
    }

    public Integer getIdCosto() {
        return idCosto;
    }

    public void setIdCosto(Integer idCosto) {
        this.idCosto = idCosto;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public CertificadoPago getCertificadoFinVigencia() {
        return certificadoFinVigencia;
    }

    public void setCertificadoFinVigencia(CertificadoPago certificadoFinVigencia) {
        this.certificadoFinVigencia = certificadoFinVigencia;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCosto != null ? idCosto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Costo)) {
            return false;
        }
        Costo other = (Costo) object;
        if ((this.idCosto == null && other.idCosto != null) || (this.idCosto != null && !this.idCosto.equals(other.idCosto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ModeloDB.Costo[ idCosto=" + idCosto + " ]";
    }

    public Collection<DetalleCertificadoPago> getDetalleCertificadoPagoCollection() {
        return detalleCertificadoPagoCollection;
    }

    public void setDetalleCertificadoPagoCollection(Collection<DetalleCertificadoPago> detalleCertificadoPagoCollection) {
        this.detalleCertificadoPagoCollection = detalleCertificadoPagoCollection;
    }
    
}
