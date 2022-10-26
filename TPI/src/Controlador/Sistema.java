/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Controlador;
import Modelo.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author matia
 */
public class Sistema {
    
    private ArrayList<Obra> obras = new ArrayList();
    private ArrayList<Foja> fojas = new ArrayList();
    private ArrayList<DetalleFoja> detallesFojas = new ArrayList();
    private ArrayList<Costo> costos = new ArrayList();
    

    /**
     * @param args the command line arguments
     */
    public Sistema(){           //Constructor (se puede sacar)
        
    }
    
    public void informarObrasCompletas(){
        ArrayList<Foja> fojasDeObra = new ArrayList();
        Foja ultimaFoja;
        boolean obraIncompleta = false;
        boolean existenCompletas = false;
        for(Obra obra : obras){
            fojasDeObra = obra.getFojas();               //Esto es necesario porque "size" es privado.
            ultimaFoja = fojasDeObra.get(fojasDeObra.size()-1);

            for(DetalleFoja detalle : ultimaFoja.getDetallesFoja()){
                if(detalle.getTotalAcumulado() != 100){
                    obraIncompleta = true;
                }
            }
            if(!obraIncompleta){
                existenCompletas = true;
                System.out.println("-  Obra Nro. " + obra.getId() + ", denominacion: " + obra.getDenominacion());
            } else {
                obraIncompleta = false;
            }
        }
        if(!existenCompletas){
            System.out.println("No existen obras completas");
        }
    } 
    
    
    void calcularImporteContrato(Obra obra){
        int importeTotal = 0;
        int importeFlete;
        int importeGastos;
        for(Item item : obra.getItems()){
            importeFlete = item.getCostoBase() + (item.getCostoBase() * item.getPorcentajeFlete());
            importeGastos = importeFlete + (importeFlete * item.getPorcentajeGastos());
            importeTotal += importeGastos + (importeGastos * item.getPorcentajeUtilidad());
        }
        System.out.println("Total a pagar en contrato: " + importeTotal);
    }
    
    void generarFoja(Obra obra){
        Scanner escaner = new Scanner(System.in);
        ArrayList<Foja> fojasDeObra = new ArrayList();
        fojasDeObra = obra.getFojas();
        ArrayList<DetalleFoja> detallesNuevaFoja = new ArrayList();
        int totalAnterior;
        int totalMes;
        int totalAcumulado;
        if(fojasDeObra.size() != 0){
            Foja ultimaFoja = fojasDeObra.get(fojasDeObra.size()-1);
            int iteracion = 0;
            for(Item item : obra.getItems()){
                totalAnterior = ultimaFoja.getDetallesFoja().get(iteracion).getTotalMes(); //Se obtiene el totalMes del item en el mismo orden, de la foja anterior.                               
                System.out.println("Ingrese avance para el item: " + obra.getItems().get(iteracion).getDenominacion());
                totalMes = escaner.nextInt();
                escaner.nextLine();
                totalAcumulado = totalAnterior + totalMes;

                DetalleFoja nuevoDetalle = new DetalleFoja(iteracion+1, totalAnterior, totalMes, totalAcumulado, item, null);
                detallesNuevaFoja.add(nuevoDetalle);
                iteracion++;
            }

            Foja nuevaFoja = new Foja(ultimaFoja.getId()+1, obra, String.valueOf(java.time.LocalDate.now()), detallesNuevaFoja, null);
            
            for(DetalleFoja detalle : detallesNuevaFoja){
                detalle.setFoja(nuevaFoja);
            }
            
            obra.getFojas().add(nuevaFoja);
            
        } else {
            
            int iteracion = 0;
            for(Item item : obra.getItems()){
                totalAnterior = 0; //Se obtiene el totalMes del item en el mismo orden, de la foja anterior.                               
                System.out.println("Ingrese avance para el item: " + obra.getItems().get(iteracion).getDenominacion());
                totalMes = escaner.nextInt();
                escaner.nextLine();
                totalAcumulado = totalAnterior + totalMes;

                DetalleFoja nuevoDetalle = new DetalleFoja(iteracion+1, totalAnterior, totalMes, totalAcumulado, item, null);
                detallesNuevaFoja.add(nuevoDetalle);
                iteracion++;
            }

            Foja nuevaFoja = new Foja(1, obra, String.valueOf(java.time.LocalDate.now()), detallesNuevaFoja, null);
            
            for(DetalleFoja detalle : detallesNuevaFoja){
                detalle.setFoja(nuevaFoja);
            }
            
            obra.getFojas().add(nuevaFoja);
        }
    }
    
    void generarCertificadoPago(int idFoja, int idObra){
        Foja fojaEncontrada = null;
        for(Obra o : obras){
            if(idObra == o.getId()){
                for(Foja f : o.getFojas()){
                    if(idFoja == f.getId()){
                        fojaEncontrada = f;
                    }
                }
            }
        }
        
        
        CertificadoPago nuevoCertificado = new CertificadoPago(fojaEncontrada.getId());
        nuevoCertificado.setFoja(fojaEncontrada);
        
    }
    
    void imprimirCertificadoPago(int idFoja, int idObra){
        Foja fojaEncontrada = null;
        int valorCostoActual;
        
        for(Obra o : obras){
            if(idObra == o.getId()){
                for(Foja f : o.getFojas()){
                    if(idFoja == f.getId()){
                        //Imprimir número de certificado.
                        System.out.println(f.getId());
                        //Imprimir número de obra.
                        System.out.println(f.getObra().getId());
                        //Imprimir denominación obra.
                        System.out.println(f.getObra().getDenominacion());
                        for(DetalleFoja d : f.getDetallesFoja()){
                            
                            //Imprimir orden de item.
                            System.out.println(d.getItem().getOrden());
                            //Imprimir denominación de item.
                            System.out.println(d.getItem().getDenominacion());
                            //Imprimir costo actual.
                            valorCostoActual = d.getItem().getCostos().get((d.getItem().getCostos().size()) - 1).getValor();
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
        for(Obra o : obras){
            if(o.getId() == idObra){
                for(DetalleFoja d : o.getFojas().get((o.getFojas().size()) - 1).getDetallesFoja()){
                    
                    porcentajeCompleto += d.getItem().getIncidencia() * (d.getTotalAcumulado() / 100);
                    
                    dineroFaltante += (100 - d.getTotalAcumulado()) * d.getItem().getCostos().get((d.getItem().getCostos().size()) - 1).getValor();
                }
                //Imprimir progreso de obra.
                System.out.println("Progreso de obra: " + porcentajeCompleto + "/100%");
                
                //Imprimir dinero faltante de obra.
                System.out.println("Dinero faltante para completar: " + dineroFaltante);
            }
        }
    }
}
