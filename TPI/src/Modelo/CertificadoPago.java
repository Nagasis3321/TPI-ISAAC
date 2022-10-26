/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author USUARIO
 */
public class CertificadoPago {
    private Integer id;
    private Foja foja;
    private String fecha;

    public CertificadoPago(Integer id, Foja foja) {
        this.id = id;
        this.foja = foja;
        this.fecha = "XX/XX/XXXX";   //PONER ACÁ EL GETFECHAACTUAL() DE LA LIBRERÍA DE TERLESKI
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Foja getFoja() {
        return foja;
    }

    public void setFoja(Foja foja) {
        this.foja = foja;
    }

    public CertificadoPago(Integer id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
}
