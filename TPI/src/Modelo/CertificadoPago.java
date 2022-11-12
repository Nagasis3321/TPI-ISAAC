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

    public CertificadoPago(Integer id, Foja foja) {
        this.id = id;
        this.foja = foja;
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
}
