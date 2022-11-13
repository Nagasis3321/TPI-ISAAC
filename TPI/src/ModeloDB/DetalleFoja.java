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
@Table(name = "detallefoja")
@NamedQueries({
    @NamedQuery(name = "DetalleFoja.findAll", query = "SELECT d FROM DetalleFoja d"),
    @NamedQuery(name = "DetalleFoja.findByIdDetalleFoja", query = "SELECT d FROM DetalleFoja d WHERE d.idDetalleFoja = :idDetalleFoja"),
    @NamedQuery(name = "DetalleFoja.findByTotalAnterior", query = "SELECT d FROM DetalleFoja d WHERE d.totalAnterior = :totalAnterior"),
    @NamedQuery(name = "DetalleFoja.findByTotalMes", query = "SELECT d FROM DetalleFoja d WHERE d.totalMes = :totalMes"),
    @NamedQuery(name = "DetalleFoja.findByTotalAcumulado", query = "SELECT d FROM DetalleFoja d WHERE d.totalAcumulado = :totalAcumulado")})
public class DetalleFoja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDetalleFoja")
    private Integer idDetalleFoja;
    @Basic(optional = false)
    @Column(name = "totalAnterior")
    private int totalAnterior;
    @Basic(optional = false)
    @Column(name = "totalMes")
    private int totalMes;
    @Basic(optional = false)
    @Column(name = "totalAcumulado")
    private int totalAcumulado;
    @JoinColumn(name = "foja", referencedColumnName = "idFoja")
    @ManyToOne(optional = false)
    private Foja foja;
    @JoinColumn(name = "item", referencedColumnName = "idItem")
    @ManyToOne(optional = false)
    private Item item;

    public DetalleFoja() {
    }

    public DetalleFoja(Integer idDetalleFoja) {
        this.idDetalleFoja = idDetalleFoja;
    }

    public DetalleFoja(Integer idDetalleFoja, int totalAnterior, int totalMes, int totalAcumulado) {
        this.idDetalleFoja = idDetalleFoja;
        this.totalAnterior = totalAnterior;
        this.totalMes = totalMes;
        this.totalAcumulado = totalAcumulado;
    }

    public Integer getIdDetalleFoja() {
        return idDetalleFoja;
    }

    public void setIdDetalleFoja(Integer idDetalleFoja) {
        this.idDetalleFoja = idDetalleFoja;
    }

    public int getTotalAnterior() {
        return totalAnterior;
    }

    public void setTotalAnterior(int totalAnterior) {
        this.totalAnterior = totalAnterior;
    }

    public int getTotalMes() {
        return totalMes;
    }

    public void setTotalMes(int totalMes) {
        this.totalMes = totalMes;
    }

    public int getTotalAcumulado() {
        return totalAcumulado;
    }

    public void setTotalAcumulado(int totalAcumulado) {
        this.totalAcumulado = totalAcumulado;
    }

    public Foja getFoja() {
        return foja;
    }

    public void setFoja(Foja foja) {
        this.foja = foja;
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
        hash += (idDetalleFoja != null ? idDetalleFoja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleFoja)) {
            return false;
        }
        DetalleFoja other = (DetalleFoja) object;
        if ((this.idDetalleFoja == null && other.idDetalleFoja != null) || (this.idDetalleFoja != null && !this.idDetalleFoja.equals(other.idDetalleFoja))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ModeloDB.DetalleFoja[ idDetalleFoja=" + idDetalleFoja + " ]";
    }
    
}
