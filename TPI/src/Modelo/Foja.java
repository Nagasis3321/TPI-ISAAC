/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.*;

/**
 *
 * @author USUARIO
 */
public class Foja {
    private Integer id;
    private Obra obra;
    private String fechaRealizacion;
    private ArrayList<DetalleFoja> detallesFoja = new ArrayList();
    private CertificadoPago certificadoPago;

    public Foja(Integer id, Obra obra, String fechaRealizacion, ArrayList<DetalleFoja> detallesFoja, CertificadoPago certificadoPago) {
        this.id = id;
        this.obra = obra;
        this.fechaRealizacion = fechaRealizacion;
        this.detallesFoja = detallesFoja;
        this.certificadoPago = certificadoPago;
    }
    

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(String fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Foja other = (Foja) obj;
        return Objects.equals(this.id, other.id);
    }

    public Foja(Integer id, String fechaRealizacion) {
        this.id = id;
        this.fechaRealizacion = fechaRealizacion;
    }

    public Foja(Integer id) {
        this.id = id;
    }

    public ArrayList<DetalleFoja> getDetallesFoja() {
        return detallesFoja;
    }

    public void setDetallesFoja(ArrayList<DetalleFoja> detallesFoja) {
        this.detallesFoja = detallesFoja;
    }
    
    
}