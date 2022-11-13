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
    @Basic(optional = false)
    @Column(name = "fechaRealizacion")
    private String fechaRealizacion;
    @JoinColumn(name = "certificadoPago", referencedColumnName = "idCertificadoPago")
    @ManyToOne(optional = false)
    private CertificadoPago certificadoPago;
    @JoinColumn(name = "obra", referencedColumnName = "idObra")
    @ManyToOne(optional = false)
    private Obra obra;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "foja")
    private Collection<DetalleFoja> detalleFojaCollection;

    public Foja() {
    }

    public Foja(Integer idFoja) {
        this.idFoja = idFoja;
    }

    public Foja(Integer idFoja, String fechaRealizacion) {
        this.idFoja = idFoja;
        this.fechaRealizacion = fechaRealizacion;
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

    public CertificadoPago getCertificadoPago() {
        return certificadoPago;
    }

    public void setCertificadoPago(CertificadoPago certificadoPago) {
        this.certificadoPago = certificadoPago;
    }

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public Collection<DetalleFoja> getDetalleFojaCollection() {
        return detalleFojaCollection;
    }

    public void setDetalleFojaCollection(Collection<DetalleFoja> detalleFojaCollection) {
        this.detalleFojaCollection = detalleFojaCollection;
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
        return "ModeloDB.Foja[ idFoja=" + idFoja + " ]";
    }
    
}
