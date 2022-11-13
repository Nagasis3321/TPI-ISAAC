/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Controlador;
import ModeloDB.*;
import ControladorClasesJPA.*;
import ControladorClasesJPA.exceptions.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author matia
 */
public class Sistema {
    
    /*private ArrayList<Obra> obras = new ArrayList();
    private ArrayList<Foja> fojas = new ArrayList();
    private ArrayList<DetalleFoja> detallesFojas = new ArrayList();
    private ArrayList<Costo> costos = new ArrayList();*/
    

    /**
     * @param args the command line arguments
     */
    public Sistema(){           //Constructor
        EmpresaJpaController empresaCtrl = new EmpresaJpaController();
        Empresa empresa = new Empresa(43214342, "Californa", "Santa Catalina", "Anibal", "Edgardo");
        try{
            empresaCtrl.create(empresa);
        }
        catch(Exception e){
            System.err.println("Excepción: " + e.getMessage());
        }
        
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
    
    void generarFoja(Obra obra){
        Scanner escaner = new Scanner(System.in);
        ArrayList<Foja> fojasDeObra = new ArrayList(obra.getFojaCollection());
        ArrayList<DetalleFoja> detallesNuevaFoja = new ArrayList();
        int totalAnterior;
        int totalMes;
        int totalAcumulado;
        if(fojasDeObra.size() != 0){
            Foja ultimaFoja = fojasDeObra.get(fojasDeObra.size()-1);
            int iteracion = 0;
            ArrayList<Item> itemsObra = new ArrayList(obra.getItemCollection());
            for(Item item : itemsObra){
                ArrayList<DetalleFoja> detallesUltimaFoja = new ArrayList(ultimaFoja.getDetalleFojaCollection());
                totalAnterior = detallesUltimaFoja.get(iteracion).getTotalMes(); //Se obtiene el totalMes del item en el mismo orden, de la foja anterior.                               
                System.out.println("Ingrese avance para el item: " + itemsObra.get(iteracion).getDenominacion());
                totalMes = escaner.nextInt();
                escaner.nextLine();
                totalAcumulado = totalAnterior + totalMes;

                DetalleFoja nuevoDetalle = new DetalleFoja(iteracion+1, totalAnterior, totalMes, totalAcumulado);
                nuevoDetalle.setItem(item);
                detallesNuevaFoja.add(nuevoDetalle);
                iteracion++;
            }

            Foja nuevaFoja = new Foja(ultimaFoja.getIdFoja()+1, String.valueOf(java.time.LocalDate.now()));
            nuevaFoja.setObra(obra);
            nuevaFoja.setDetalleFojaCollection(detallesNuevaFoja);
            for(DetalleFoja detalle : detallesNuevaFoja){
                detalle.setFoja(nuevaFoja);
            }
            
            obra.getFojaCollection().add(nuevaFoja);
            
        } else {
            
            int iteracion = 0;
            ArrayList<Item> itemsObra = new ArrayList(obra.getItemCollection());
            for(Item item : itemsObra){
                totalAnterior = 0; //Se obtiene el totalMes del item en el mismo orden, de la foja anterior.                               
                System.out.println("Ingrese avance para el item: " + itemsObra.get(iteracion).getDenominacion());
                totalMes = escaner.nextInt();
                escaner.nextLine();
                totalAcumulado = totalAnterior + totalMes;

                DetalleFoja nuevoDetalle = new DetalleFoja(iteracion+1, totalAnterior, totalMes, totalAcumulado);
                nuevoDetalle.setItem(item);
                detallesNuevaFoja.add(nuevoDetalle);
                iteracion++;
            }

            Foja nuevaFoja = new Foja(1, String.valueOf(java.time.LocalDate.now()));
            nuevaFoja.setObra(obra);
            nuevaFoja.setDetalleFojaCollection(detallesNuevaFoja);
            for(DetalleFoja detalle : detallesNuevaFoja){
                detalle.setFoja(nuevaFoja);
            }
            
            obra.getFojaCollection().add(nuevaFoja);
        }
    }
    
    void generarCertificadoPago(int idFoja, int idObra){
        Foja fojaEncontrada = null;
        ObraJpaController obraCtrl = new ObraJpaController();
        for(Obra o : obraCtrl.findObraEntities()){
            if(idObra == o.getIdObra()){
                for(Foja f : o.getFojaCollection()){
                    if(idFoja == f.getIdFoja()){
                        fojaEncontrada = f;
                    }
                }
            }
        }
        
        
        CertificadoPago nuevoCertificado = new CertificadoPago(fojaEncontrada.getIdFoja());
        //nuevoCertificado.setFoja(fojaEncontrada);
        
    }
    
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
