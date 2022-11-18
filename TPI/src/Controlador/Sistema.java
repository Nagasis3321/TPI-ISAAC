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
    
    public ArrayList<Object> obtenerCertificadoPago(int nroObra, int nroCertificado){
        ObraJpaController obraCtrl = new ObraJpaController();
        ArrayList<Object> retorno = new ArrayList();
        
        try{
            Obra obra = obraCtrl.findObra(nroObra);
            ArrayList<Foja> fojasObra = new ArrayList(obra.getFojaCollection());
            Foja foja = fojasObra.get(nroCertificado - 1);
            ArrayList<CertificadoPago> certificadosFoja = new ArrayList(foja.getCertificadoPagoCollection());
            if(certificadosFoja.size() == 0){
                return null;
            }
            CertificadoPago certificado = certificadosFoja.get(0);
            
            ArrayList<DetalleFoja> detalles = new ArrayList(foja.getDetalleFojaCollection());
        
            Object[][] tuplas = new Object[detalles.size()][8];

            //Formato para decimales con dos dígitos
            DecimalFormat df = new DecimalFormat("$ 0.00");

            int i = 0;      
            for(DetalleFoja d : detalles){

                tuplas[i][0] = d.getItem().getOrden();
                tuplas[i][1] = d.getItem().getDenominacion();
                tuplas[i][2] = d.getItem().getIncidencia();
                tuplas[i][3] = d.getItem().getTipoItem().getDenominacion();

                ArrayList<DetalleCertificadoPago> detallesCertificado = new ArrayList(d.getDetalleCertificadoPagoCollection());
                DetalleCertificadoPago detCertificado = detallesCertificado.get(0);
                float costo = detCertificado.getCosto().getValor();
                tuplas[i][4] = df.format(costo);

                tuplas[i][5] = df.format(((float) d.getTotalAnterior() / 100) * costo);
                tuplas[i][6] = df.format(((float) d.getTotalMes() / 100) * costo);
                tuplas[i][7] = df.format(((float) d.getTotalAcumulado() / 100) * costo);

                i++;
            }

            retorno.add(tuplas);
            retorno.add(certificado.getFechaRealizacion());
            return retorno;
        }
        catch(Exception e){
            System.err.println("Excepción: " + e.getMessage());
            return null;
        }
    }
    
    public Object[][] obtenerObrasEmpresa(int cuitEmpresa){
        EmpresaJpaController empCtrl = new EmpresaJpaController();
        Empresa empresa = null;
        
        try{
            empresa = empCtrl.findEmpresa(cuitEmpresa);

            Object[][] tuplas = new Object[empresa.getObraCollection().size()][7];
            int i = 0;

            for(Obra o : empresa.getObraCollection()){
                tuplas[i][0] = o.getIdObra();
                tuplas[i][1] = o.getDenominacion();
                tuplas[i][2] = o.getFinanciacion();
                tuplas[i][3] = o.getFechaInicio();
                tuplas[i][4] = o.getPlazo();
                int cantFojas = o.getFojaCollection().size();
                tuplas[i][5] = cantFojas;
                int cantCertificados = 0;
                for(Foja f : o.getFojaCollection()){
                    if(f.getCertificadoPagoCollection().size() > 0){
                        cantCertificados++;
                    }
                }
                tuplas[i][6] = cantCertificados;

                i++;
            }
            return tuplas;
        }
        catch(Exception e){
            System.err.println("Excepción: " + e.getMessage());
            return null;
        }
    }
    
    public ArrayList<Object> obtenerFoja(int nroObra, int nroFoja){
        ObraJpaController obraCtrl = new ObraJpaController();
        Obra obra = null;
        ArrayList<Object> retorno = new ArrayList();
        
        try{
            obra = obraCtrl.findObra(nroObra);
            
            ArrayList<Foja> fojasObra = new ArrayList(obra.getFojaCollection());
            Foja foja = fojasObra.get(nroFoja - 1);

            Object[][] tuplas = new Object[foja.getDetalleFojaCollection().size()][5];
            int i = 0;

            for(DetalleFoja d : foja.getDetalleFojaCollection()){
                tuplas[i][0] = d.getItem().getOrden();
                tuplas[i][1] = d.getItem().getDenominacion();
                tuplas[i][2] = d.getTotalAnterior();
                tuplas[i][3] = d.getTotalMes();
                tuplas[i][4] = d.getTotalAcumulado();
                
                i++;
            }
            retorno.add(tuplas);
            retorno.add(foja.getFechaRealizacion());
            return retorno;
        }
        catch(Exception e){
            System.err.println("Excepción: " + e.getMessage());
            return null;
        }
    }
    
    public boolean aniadirCostos(int nroObra, Object[][] tuplas){
        ObraJpaController obraCtrl = new ObraJpaController();
        Obra obra = obraCtrl.findObra(nroObra);
        Costo nuevoCosto = null;
        
        ArrayList<Item> itemsObra = new ArrayList(obra.getItemCollection());
        for(int i = 0; i < tuplas.length; i++){
            System.out.println("i: " + i);
            Item item = itemsObra.get(Integer.parseInt(tuplas[i][0].toString()) - 1);
            nuevoCosto = crearCosto(item, Float.parseFloat(tuplas[i][1].toString()));
            if(nuevoCosto == null){
                return false;
            }
        }
        
        return true;
    }
    
    public Object[][] obtenerItemsObraAniadirCosto(int nroObra){
        ObraJpaController obraCtrl = new ObraJpaController();
        Obra obra = obraCtrl.findObra(nroObra);
        
        //Formato para precios
        DecimalFormat df = new DecimalFormat("$ 0.00");
        DecimalFormat incf = new DecimalFormat("0.00");
        
        int ord, tipo, ta;
        String cos, inc;
        int fila = 0;
        String den;
        Object[][] tuplas = new Object[obra.getItemCollection().size()][6];        

        for(Item item : obra.getItemCollection()){
            ord = item.getOrden();
            den = item.getDenominacion();
            inc = incf.format(item.getIncidencia());
            tipo = item.getTipoItem().getIdTipoItem();
            cos = df.format(item.getUltimoCosto().getValor());

            tuplas[fila][0] = ord;
            tuplas[fila][1] = den;
            tuplas[fila][2] = inc;
            tuplas[fila][3] = tipo;
            tuplas[fila][4] = cos;
            
            //Se deja la última fila en blanco para que el usuario la rellene
            //en la vista

            fila++;
        } 
        
        return tuplas;
    }
    
    public boolean eliminarCertificadosPago(int nroObra){
        ObraJpaController obraCtrl = new ObraJpaController();
        DetalleCertificadoPagoJpaController detCertCtrl = new DetalleCertificadoPagoJpaController();
        CertificadoPagoJpaController certCtrl = new CertificadoPagoJpaController();
        
        try{
            Obra obra = obraCtrl.findObra(nroObra);
            for(Foja f : obra.getFojaCollection()){
                for(CertificadoPago c : f.getCertificadoPagoCollection()){
                    for(DetalleCertificadoPago d : c.getDetalleCertificadoPagoCollection()){
                        detCertCtrl.destroy(d.getIdDetalleCertificadoPago());
                    }
                    certCtrl.destroy(c.getIdCertificadoPago());
                }
            }
        }
        catch(Exception e){
            System.err.println("Excepción: " + e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean eliminarFojas(int nroObra){
        ObraJpaController obraCtrl = new ObraJpaController();
        DetalleFojaJpaController detFojaCtrl = new DetalleFojaJpaController();
        FojaJpaController fojaCtrl = new FojaJpaController();
        DetalleCertificadoPagoJpaController detCertCtrl = new DetalleCertificadoPagoJpaController();
        CertificadoPagoJpaController certCtrl = new CertificadoPagoJpaController();
        
        try{
            Obra obra = obraCtrl.findObra(nroObra);
            for(Foja f : obra.getFojaCollection()){
                for(CertificadoPago c : f.getCertificadoPagoCollection()){
                    for(DetalleCertificadoPago d : c.getDetalleCertificadoPagoCollection()){
                        detCertCtrl.destroy(d.getIdDetalleCertificadoPago());
                    }
                    certCtrl.destroy(c.getIdCertificadoPago());
                }
                
                for(DetalleFoja detFoja : f.getDetalleFojaCollection()){
                    detFojaCtrl.destroy(detFoja.getIdDetalleFoja());
                }
                
                fojaCtrl.destroy(f.getIdFoja());
            }
        }
        catch(Exception e){
            System.err.println("Excepción: " + e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean eliminarEmpresa(int cuit){
        EmpresaJpaController empCtrl = new EmpresaJpaController();
        try{
            Empresa empresa = empCtrl.findEmpresa(cuit);
            for(Obra o : empresa.getObraCollection()){
                eliminarObra(o.getIdObra());
            }       
            empCtrl.destroy(cuit);
        }
        catch(Exception e){
            System.err.println("Excepción: " + e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean eliminarObra(int nroObra){
        ObraJpaController obraCtrl = new ObraJpaController();
        ItemJpaController itemCtrl = new ItemJpaController();
        CostoJpaController costoCtrl = new CostoJpaController();
        DetalleFojaJpaController detFojaCtrl = new DetalleFojaJpaController();
        FojaJpaController fojaCtrl = new FojaJpaController();
        DetalleCertificadoPagoJpaController detCertCtrl = new DetalleCertificadoPagoJpaController();
        CertificadoPagoJpaController certCtrl = new CertificadoPagoJpaController();
        
        
        try{
            Obra obra = obraCtrl.findObra(nroObra);
            
            for(Foja f : obra.getFojaCollection()){
                for(CertificadoPago cert : f.getCertificadoPagoCollection()){
                    for(DetalleCertificadoPago detCert : cert.getDetalleCertificadoPagoCollection()){
                        detCertCtrl.destroy(detCert.getIdDetalleCertificadoPago());
                    }
                    certCtrl.destroy(cert.getIdCertificadoPago());
                }
            }
            for(Item i : obra.getItemCollection()){
                for(Costo c : i.getCostoCollection()){
                    costoCtrl.destroy(c.getIdCosto());
                }
            }
            for(Foja f : obra.getFojaCollection()){
                for(DetalleFoja detFoja : f.getDetalleFojaCollection()){
                    detFojaCtrl.destroy(detFoja.getIdDetalleFoja());
                }
                fojaCtrl.destroy(f.getIdFoja());
            }
            for(Item i : obra.getItemCollection()){
                itemCtrl.destroy(i.getIdItem());                
            }
            
            obraCtrl.destroy(nroObra);
        }
        catch(Exception e){
            System.err.println("Excepción: " + e.getMessage());
            return false;
        }
        return true;
    }
    
    public ArrayList<Object> obtenerSaldosRestantes(int nroObra){
        ObraJpaController obraCtrl = new ObraJpaController();
        Obra obra = obraCtrl.findObra(nroObra);
        
        ArrayList<Item> itemsObra = new ArrayList(obra.getItemCollection());
        
        Object[][] tuplas = new Object[obra.getItemCollection().size()][8];
        DecimalFormat df = new DecimalFormat("$ 0.00");
        DecimalFormat incf = new DecimalFormat("0.00");
        
        ArrayList<Object> retorno = new ArrayList();
        
        float saldoTotalRestante = 0;
        float progresoRestante = 0;
        
        int i = 0;
        
        for(Item item : itemsObra){
            tuplas[i][0] = item.getOrden();
            tuplas[i][1] = item.getDenominacion();
            tuplas[i][2] = item.getTipoItem().getDenominacion();
            float inc = item.getIncidencia();
            tuplas[i][3] = incf.format(inc);
            tuplas[i][4] = df.format(item.getCostoBase().getValor());
            tuplas[i][5] = df.format(item.getUltimoCosto().getValor());
            
            ArrayList<DetalleFoja> detallesDeItem = new ArrayList(item.getDetalleFojaCollection());
            int avanceAcumulado = detallesDeItem.get(detallesDeItem.size() - 1).getTotalAcumulado();
            tuplas[i][6] = avanceAcumulado;
            
            float floatAvance = (float)avanceAcumulado/100;
            float floatValor = item.getUltimoCosto().getValor();
            tuplas[i][7] = df.format(floatValor - (floatAvance * floatValor));
            
            saldoTotalRestante += (floatValor - (floatAvance * floatValor));
            
            progresoRestante += (inc - ((float) avanceAcumulado * inc / 100));
            
            i++;
        }
        
        retorno.add(tuplas);
        retorno.add(incf.format(progresoRestante));
        retorno.add(df.format(saldoTotalRestante));
        
        return retorno;
    }
    
    public Object[][] obtenerImportesContrato(int nroObra, float flete, float gastos, float utilidad){
        ObraJpaController obraCtrl = new ObraJpaController();
        Obra obra = obraCtrl.findObra(nroObra);
        if(obra == null){
            return null;
        }
        Object[][] tuplas = new Object[obra.getItemCollection().size()][8];
        
        //Formato para precios
        DecimalFormat df = new DecimalFormat("$ 0.00");
        
        ArrayList<Item> itemsObra = new ArrayList(obra.getItemCollection());
        
        int i = 0;
        
        for(Item item : itemsObra){
            tuplas[i][0] = item.getOrden();
            tuplas[i][1] = item.getDenominacion();
            tuplas[i][2] = item.getTipoItem().getDenominacion();
            tuplas[i][3] = df.format((float) item.getCostoBase().getValor());
            float sumaImpuesto = (((float) item.getTipoItem().getImpuesto() / 100 ) * (float) item.getCostoBase().getValor()) + (float) item.getCostoBase().getValor();
            tuplas[i][4] = df.format(sumaImpuesto);
            float sumaFlete = sumaImpuesto * (flete / 100) + sumaImpuesto;
            tuplas[i][5] = df.format(sumaFlete);
            float sumaGastos = sumaFlete * (gastos / 100) + sumaFlete;
            tuplas[i][6] = df.format(sumaGastos);
            float sumaUtilidad = sumaGastos * (utilidad/ 100) + sumaGastos;
            tuplas[i][7] = df.format(sumaUtilidad);
            i++;
        }
        return tuplas;
    }
    
    public Object[][] buscarObrasCompletas(){
        ObraJpaController obraCtrl = new ObraJpaController();
        ArrayList<Obra> obras = new ArrayList(obraCtrl.findObraEntities());
        boolean completa = true;
        int i = 0;
        Object[][] tuplas = new Object[obras.size()][6];
        
        for(Obra o : obras){
            ArrayList<Foja> fojasObra = new ArrayList(o.getFojaCollection());
            ArrayList<DetalleFoja> detallesFoja = new ArrayList(fojasObra.get(fojasObra.size()-1).getDetalleFojaCollection());
            for(DetalleFoja d : detallesFoja){
                if(d.getTotalAcumulado() < 100){
                    completa = false;
                }
            }
            if(completa == true){
                tuplas[i][0] = o.getCuitEmpresa().getCuit();
                tuplas[i][1] = o.getIdObra();
                tuplas[i][2] = o.getDenominacion();
                tuplas[i][3] = o.getFechaInicio();
                tuplas[i][4] = o.getFinanciacion();
                tuplas[i][5] = o.getPlazo();
            }
            else{
                completa = true;
            }
            
            i++;
        }
        
        return tuplas;
    }
    
    public Object[][] generarVistaPreviaCertificadoPago(int nroObra, int nroFoja){
        ObraJpaController obraCtrl = new ObraJpaController();
        Obra obra = obraCtrl.findObra(nroObra);
        ArrayList<Foja> fojasObra = new ArrayList(obra.getFojaCollection());
        Foja fojaParaCertificado = null;
        try{
            fojaParaCertificado = fojasObra.get(nroFoja - 1);
            ArrayList<DetalleFoja> detalles = new ArrayList(fojaParaCertificado.getDetalleFojaCollection());
        
            Object[][] tuplas = new Object[detalles.size()][8];

            //Formato para decimales con dos dígitos
            DecimalFormat df = new DecimalFormat("$ 0.00");

            int i = 0;      
            for(DetalleFoja d : detalles){

                tuplas[i][0] = d.getItem().getOrden();
                tuplas[i][1] = d.getItem().getDenominacion();
                tuplas[i][2] = d.getItem().getIncidencia();
                tuplas[i][3] = d.getItem().getTipoItem().getDenominacion();

                float costoActual = d.getItem().getUltimoCosto().getValor();
                tuplas[i][4] = df.format(costoActual);



                tuplas[i][5] = df.format(((float) d.getTotalAnterior() / 100) * costoActual);
                tuplas[i][6] = df.format(((float) d.getTotalMes() / 100) * costoActual);
                tuplas[i][7] = df.format(((float) d.getTotalAcumulado() / 100) * costoActual);

                i++;
            }

            return tuplas;
        }
        catch(Exception e){
            return null;
        }
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
    
    public Foja crearFoja(int numeroObra, Object[][] tuplas){
        ObraJpaController obraCtrl = new ObraJpaController();
        Obra obra = obraCtrl.findObra(numeroObra);
        ArrayList<Item> itemsObra = new ArrayList(obra.getItemCollection());
        
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

            DetalleFoja nuevoDetalle = crearDetalle(Integer.parseInt(tuplas[i][5].toString()), Integer.parseInt(tuplas[i][6].toString()), Integer.parseInt(tuplas[i][7].toString()), itemsObra.get(j), nuevaFoja);
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
    
    public Item crearItem(Obra obra, int orden, String denominacion, float incidencia, int tipo, float costo){
        ItemJpaController itemCtrl = new ItemJpaController();
        //Formato de incidencia
        DecimalFormat incf = new DecimalFormat("0.00");
        Item item = new Item(orden, denominacion, Float.parseFloat(incf.format(incidencia).replace(',', '.')));
        
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
    
    public Costo crearCosto(Item item, float valor){
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
    
    public Object[][] obtenerItemsObraConAvances(int idObra){
        ObraJpaController obraCtrl = new ObraJpaController();
        Obra obra = obraCtrl.findObra(idObra);
        ArrayList<Foja> fojasObra = new ArrayList(obra.getFojaCollection());
        
        //Formato para precios
        DecimalFormat df = new DecimalFormat("$ 0.00");
        DecimalFormat incf = new DecimalFormat("0.00");
        
        Foja ultimaFoja;
        Item itemDetalle;
        int ord, tipo, ta;
        String cos, inc;
        int fila = 0;
        String den;
        Object[][] tuplas = new Object[obra.getItemCollection().size()][7];        
        
        if(!fojasObra.isEmpty()){
            ultimaFoja = fojasObra.get(fojasObra.size() - 1);
            
            for(DetalleFoja d : ultimaFoja.getDetalleFojaCollection()){
                itemDetalle = d.getItem();
                ord = itemDetalle.getOrden();
                den = itemDetalle.getDenominacion();
                inc = incf.format(itemDetalle.getIncidencia());
                tipo = itemDetalle.getTipoItem().getIdTipoItem();
                cos = df.format(itemDetalle.getUltimoCosto().getValor());
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
                inc = incf.format(i.getIncidencia());
                tipo = i.getTipoItem().getIdTipoItem();
                cos = df.format(i.getUltimoCosto().getValor());;
                                
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
    
    /*
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
*/
}
