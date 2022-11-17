/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Controlador;
import ModeloDB.*;
import ControladorClasesJPA.*;
import ControladorClasesJPA.exceptions.*;
import Vistas.*;
import java.text.DecimalFormat;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.*;
/**
 *
 * @author matia
 */
public class Sistema {
    
    private Inicio vista = null;

    /**
     * @param args the command line arguments
     */
    public Sistema(){           //Constructor
        
        
        Inicio vista = new Inicio(this);
        vista.setVisible(true);
        
        /*Creación de tipos de item en BD
        TipoItem vivienda = new TipoItem("Vivienda", 5);
        TipoItem infraestructura = new TipoItem("Infraestructura", 10);
        TipoItemJpaController tipoCtrl = new TipoItemJpaController();
        tipoCtrl.create(vivienda);
        tipoCtrl.create(infraestructura);
        */
        
    }
    
    public Object[][] generarVistaPreviaCertificadoPago(int nroObra, int nroFoja){
        ObraJpaController obraCtrl = new ObraJpaController();
        Obra obra = obraCtrl.findObra(nroObra);
        ArrayList<Foja> fojasObra = new ArrayList(obra.getFojaCollection());
        Foja fojaParaCertificado = null;
        try{
            fojaParaCertificado = fojasObra.get(nroFoja - 1);
        }
        catch(Exception e){
            return null;
        }
        
        ArrayList<DetalleFoja> detalles = new ArrayList(fojaParaCertificado.getDetalleFojaCollection());
        
        Object[][] tuplas = new Object[detalles.size()][8];
        
        //Formato para decimales con dos dígitos
        DecimalFormat df = new DecimalFormat("$ 0.00");
        
        int i = 0;      
        for(DetalleFoja d : detalles){
            
            tuplas[i][0] = d.getItem().getOrden();
            tuplas[i][1] = d.getItem().getDenominacion();
            tuplas[i][2] = d.getItem().getIncidencia();
            
            String tipoItem = null;
            int intTipoItem = d.getItem().getTipoItem().getIdTipoItem();
            switch(intTipoItem){
                case 1:
                    tipoItem = "Vivienda";
                    break;
                case 2:
                    tipoItem = "Infraestructura";
                    break;
            }
            tuplas[i][3] = intTipoItem;
            
            ArrayList<Costo> costosItem = new ArrayList(d.getItem().getCostoCollection());
            int costoActual = costosItem.get(costosItem.size()-1).getValor();
            tuplas[i][4] = costoActual;
            
            
                    
            tuplas[i][5] = df.format(((float) d.getTotalAnterior() / 100) * costoActual);
            tuplas[i][6] = df.format(((float) d.getTotalMes() / 100) * costoActual);
            tuplas[i][7] = df.format(((float) d.getTotalAcumulado() / 100) * costoActual);

            i++;
        }
        
        return tuplas;
    }
    
    public CertificadoPago guardarCertificadoPago(int numeroObra, int numeroFoja){
        CertificadoPago nuevoCertificado = new CertificadoPago();
        ObraJpaController obraCtrl = new ObraJpaController();
        Obra obra = obraCtrl.findObra(numeroObra);
        
        CertificadoPagoJpaController certCtrl = new CertificadoPagoJpaController();
        DetalleCertificadoPagoJpaController detCertCtrl = new DetalleCertificadoPagoJpaController();
        
        ArrayList<Foja> fojasObra = new ArrayList(obra.getFojaCollection());
        Foja ultimaFoja = null;
        try{
            ultimaFoja = fojasObra.get(numeroFoja-1);
        }
        catch(Exception e){
            return null;
        }
        
        ArrayList<CertificadoPago> certificadoFoja = new ArrayList(ultimaFoja.getCertificadoPagoCollection());
        
        if(!certificadoFoja.isEmpty()){
            return null;
        }
        
        ArrayList<DetalleFoja> detallesFoja = new ArrayList(ultimaFoja.getDetalleFojaCollection());
        
        nuevoCertificado.setFechaRealizacion(String.valueOf(java.time.LocalDate.now()));
        nuevoCertificado.setFoja(ultimaFoja);
        
        try{
            certCtrl.create(nuevoCertificado);
        }
        catch(Exception e){
            System.err.println("Excepción: " + e.getMessage());
            return null;
        }
        
        for(DetalleFoja d : detallesFoja){
            DetalleCertificadoPago detalleCertPago = new DetalleCertificadoPago();
            
            ArrayList<Costo> costosItem = new ArrayList(d.getItem().getCostoCollection());
            
            detalleCertPago.setCosto(costosItem.get(costosItem.size()-1));
            detalleCertPago.setDetalleFoja(d);            
            detalleCertPago.setCertificadoPago(nuevoCertificado);
            
            try{
            detCertCtrl.create(detalleCertPago);
            }
            catch(Exception e){
                System.err.println("Excepción: " + e.getMessage());
                return null;
            }
        }
        
        return nuevoCertificado;
    }
    
    public Foja crearFoja(int numeroObra, Object[][] tuplas, int[] totalesAcumulados){
        ObraJpaController obraCtrl = new ObraJpaController();
        Obra obra = obraCtrl.findObra(numeroObra);
        ArrayList<Item> itemsObra = new ArrayList(obra.getItemCollection());
        ArrayList<Foja> fojasObra = new ArrayList(obra.getFojaCollection());
        
        Foja nuevaFoja = new Foja();
        nuevaFoja.setFechaRealizacion(String.valueOf(java.time.LocalDate.now()));
        nuevaFoja.setObra(obra);
        FojaJpaController fojaCtrl = new FojaJpaController();
        try{
            fojaCtrl.create(nuevaFoja);
        }
        catch(Exception e){
            System.err.println("Excepción: " + e.getMessage());
            return null;
        }
        
        for(int i = 0; i < tuplas.length; i++){
            int j = 0;
            
            while(itemsObra.get(j).getOrden() != (int) tuplas[i][0]){
                j++;
            }

            DetalleFoja nuevoDetalle = crearDetalle(Integer.parseInt(tuplas[i][5].toString()), Integer.parseInt(tuplas[i][6].toString()), totalesAcumulados[i], itemsObra.get(j), nuevaFoja);
            if(nuevoDetalle == null){
                return null;
            }
        }        
        
        return nuevaFoja;
    }
    
    public DetalleFoja crearDetalle(int totalAnterior, int totalMes, int totalAcumulado, Item item, Foja foja){
        DetalleFojaJpaController detCtrl = new DetalleFojaJpaController();
        DetalleFoja detalleFoja = new DetalleFoja(totalAnterior, totalMes, totalAcumulado);
        detalleFoja.setItem(item);
        detalleFoja.setFoja(foja);
        
        try{
            detCtrl.create(detalleFoja);
        }
        catch(Exception e){
            System.err.println("Excepción: " + e.getMessage());
            return null;
        }
        
        return detalleFoja;
    }
    
    public Item crearItem(Obra obra, int orden, String denominacion, int incidencia, int tipo, int costo){
        ItemJpaController itemCtrl = new ItemJpaController();
        Item item = new Item(orden, denominacion, incidencia);
        
        TipoItemJpaController tipoCtrl = new TipoItemJpaController();
        TipoItem tipoItem = tipoCtrl.findTipoItem(tipo);
        
        item.setTipoItem(tipoItem);
        item.setObra(obra);

        try{
            itemCtrl.create(item);
        }
        catch(Exception e){
            System.err.println("Excepción: " + e.getMessage());
            return null;
        }
        
        crearCosto(item, costo);
 
        return item;
    }
    
    public Costo crearCosto(Item item, int valor){
        CostoJpaController costoCtrl = new CostoJpaController();
        Costo costo = new Costo();
        costo.setValor(valor);
        costo.setItem(item);
        
        try{
            costoCtrl.create(costo);
        }
        catch(Exception e){
            System.err.println("Excepción: " + e.getMessage());
            return null;
        }
        
        return costo;
        
        
        //Falta añadir la parte de asignar el finVigencia del costo anterior.
    }
    
    public Obra crearObra(int c, String d, String f, String fi, String p){
        ObraJpaController obraCtrl = new ObraJpaController();
        Obra obra = new Obra(p, fi, f, d);
        EmpresaJpaController empCtrl = new EmpresaJpaController();
        Empresa empresa = empCtrl.findEmpresa(c);
        obra.setCuitEmpresa(empresa);
        try{
            obraCtrl.create(obra);
        }
        catch(Exception e){
            System.err.println("Excepción: " + e.getMessage());
            return null;
        }
        return obra;
    }
    
    public boolean crearEmpresa(int cuit, String razonSocial, String direccion, String rl, String rt){
        EmpresaJpaController empresaCtrl = new EmpresaJpaController();
        Empresa empresa = new Empresa(cuit, razonSocial, direccion, rl, rt);
        try{
            empresaCtrl.create(empresa);
        }
        catch(Exception e){
            System.err.println("Excepción: " + e.getMessage());
            return false;
        }
        return true;
    }
    
    public void informarObrasCompletas(){
        ArrayList<Foja> fojasDeObra = new ArrayList();
        Foja ultimaFoja;
        ObraJpaController obraCtrl = new ObraJpaController();
        boolean obraIncompleta = false;
        boolean existenCompletas = false;
        for(Obra obra : obraCtrl.findObraEntities()){
            fojasDeObra = new ArrayList(obra.getFojaCollection()); 
            ultimaFoja = fojasDeObra.get(fojasDeObra.size()-1);

            for(DetalleFoja detalle : ultimaFoja.getDetalleFojaCollection()){
                if(detalle.getTotalAcumulado() != 100){
                    obraIncompleta = true;
                }
            }
            if(!obraIncompleta){
                existenCompletas = true;
                System.out.println("-  Obra Nro. " + obra.getIdObra()+ ", denominacion: " + obra.getDenominacion());
            } else {
                obraIncompleta = false;
            }
        }
        if(!existenCompletas){
            System.out.println("No existen obras completas");
        }
    } 
    
 
    void calcularImporteContrato(Obra obra, int flete, int gastos, int utilidad){
        int importeTotal = 0;
        int importeFlete;
        int importeGastos;
        for(Item item : obra.getItemCollection()){
            ArrayList<Costo> costosItem = new ArrayList(item.getCostoCollection());
            importeFlete = costosItem.get(1).getValor() + (costosItem.get(1).getValor() * flete);
            importeGastos = importeFlete + (importeFlete * gastos);
            importeTotal += importeGastos + (importeGastos * utilidad);
        }
        System.out.println("Total a pagar en contrato: " + importeTotal);
    }
    
    
    public Object[][] obtenerItemsObraConAvances(int idObra){
        ObraJpaController obraCtrl = new ObraJpaController();
        Obra obra = obraCtrl.findObra(idObra);
        ArrayList<Foja> fojasObra = new ArrayList(obra.getFojaCollection());
        Foja ultimaFoja;
        Item itemDetalle;
        int ord, inc, tipo, cos, ta;
        int fila = 0;
        String den;
        Object[][] tuplas = new Object[obra.getItemCollection().size()][7];        
        
        if(!fojasObra.isEmpty()){
            ultimaFoja = fojasObra.get(fojasObra.size() - 1);
            
            for(DetalleFoja d : ultimaFoja.getDetalleFojaCollection()){
                itemDetalle = d.getItem();
                ord = itemDetalle.getOrden();
                den = itemDetalle.getDenominacion();
                inc = itemDetalle.getIncidencia();
                tipo = itemDetalle.getTipoItem().getIdTipoItem();
                cos = itemDetalle.getUltimoCosto().getValor();
                ta = d.getTotalAcumulado();

                tuplas[fila][0] = ord;
                tuplas[fila][1] = den;
                tuplas[fila][2] = inc;
                tuplas[fila][3] = tipo;
                tuplas[fila][4] = cos;
                tuplas[fila][5] = ta;
                
                fila++;
            }
        }
        else{
            for(Item i : obra.getItemCollection()){
                ord = i.getOrden();
                den = i.getDenominacion();
                inc = i.getIncidencia();
                tipo = i.getTipoItem().getIdTipoItem();
                cos = i.getUltimoCosto().getValor();
                                
                tuplas[fila][0] = ord;
                tuplas[fila][1] = den;
                tuplas[fila][2] = inc;
                tuplas[fila][3] = tipo;
                tuplas[fila][4] = cos;
                tuplas[fila][5] = 0;
                
                fila++;
            }
        }
        
        return tuplas;
    }
    
    /*
    void imprimirCertificadoPago(int idFoja, int idObra){
        Foja fojaEncontrada = null;
        int valorCostoActual;
        ObraJpaController obraCtrl = new ObraJpaController();
        
        for(Obra o : obraCtrl.findObraEntities()){
            if(idObra == o.getIdObra()){
                for(Foja f : o.getFojaCollection()){
                    if(idFoja == f.getIdFoja()){
                        //Imprimir número de certificado.
                        System.out.println(f.getIdFoja());
                        //Imprimir número de obra.
                        System.out.println(f.getObra().getIdObra());
                        //Imprimir denominación obra.
                        System.out.println(f.getObra().getDenominacion());
                        for(DetalleFoja d : f.getDetalleFojaCollection()){
                            
                            //Imprimir orden de item.
                            System.out.println(d.getItem().getOrden());
                            //Imprimir denominación de item.
                            System.out.println(d.getItem().getDenominacion());
                            //Imprimir costo actual.
                            ArrayList<Costo> costosItem = new ArrayList(d.getItem().getCostoCollection());
                            valorCostoActual = costosItem.get((costosItem.size()) - 1).getValor();
                            System.out.println(valorCostoActual);
                            //Imprimir total anterior.
                            System.out.println((d.getTotalAnterior() / 100) * valorCostoActual);
                            //Imprimir total mes.
                            System.out.println((d.getTotalMes() / 100) * valorCostoActual);
                            //Imprimir total mes.
                            System.out.println((d.getTotalAcumulado() / 100) * valorCostoActual);
                        }
                    }
                }
            }
        }
    }

*/
    
    
    void calcularProgresoObra(int idObra){
        int porcentajeCompleto = 0;
        int dineroFaltante = 0;
        ObraJpaController obraCtrl = new ObraJpaController();
        
        for(Obra o : obraCtrl.findObraEntities()){
            if(o.getIdObra() == idObra){
                ArrayList<Foja> fojasObra = new ArrayList(o.getFojaCollection());
                for(DetalleFoja d : fojasObra.get((o.getFojaCollection().size()) - 1).getDetalleFojaCollection()){
                    ArrayList<Costo> costosItem = new ArrayList(d.getItem().getCostoCollection());
                    
                    porcentajeCompleto += d.getItem().getIncidencia() * (d.getTotalAcumulado() / 100);
                    
                    dineroFaltante += (100 - d.getTotalAcumulado()) * costosItem.get((costosItem.size()) - 1).getValor();
                }
                //Imprimir progreso de obra.
                System.out.println("Progreso de obra: " + porcentajeCompleto + "/100%");
                
                //Imprimir dinero faltante de obra.
                System.out.println("Dinero faltante para completar: " + dineroFaltante);
            }
        }
    }
}
