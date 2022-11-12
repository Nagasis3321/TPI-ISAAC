/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;


import Modelo1.*;

import Persistencia.*;
/**
 *
 * @author USUARIO
 */
public class ControladorCreadorBD {

    private EmpresaJpaController JpaEmpresa = new EmpresaJpaController();
    private ObraJpaController JpaObra = new ObraJpaController();
    private ItemJpaController JpaItem = new ItemJpaController();
    private TipoItemJpaController JpaTipoItem = new TipoItemJpaController();
    private CostoJpaController JpaCosto = new CostoJpaController();
    private CertificadoPagoJpaController JpaCertificadoPago = new CertificadoPagoJpaController();
    private DetallesFojaJpaController JpaDetallesFoja = new DetallesFojaJpaController();    
    private FojaJpaController JpaFoja = new FojaJpaController(); 
    
    
    public void AgregarEmpresa(Empresa E) throws Exception{
        JpaEmpresa.create(E);
    }
    
    public void AgregarObra(Obra O) throws Exception{
        JpaObra.create(O);
    }
    
     public void AgregarItem(Item I) throws Exception{
        JpaItem.create(I);
    }
    
    public void AgregarTipoItem(TipoItem I) throws Exception{
        JpaTipoItem.create(I);
    }
   
    public void AgregarCosto (Costo C) throws Exception{
        JpaCosto.create(C);
    }   
    
    public void AgregarCertificadoPago (CertificadoPago C) throws Exception{
        JpaCertificadoPago.create(C);
    }  

    public void AgregarDetalleFoja (DetallesFoja F) throws Exception{
        JpaDetallesFoja.create(F);
    }    

    public void AgregarFoja (Foja F) throws Exception{
        JpaFoja.create(F);
    }        


    
}
