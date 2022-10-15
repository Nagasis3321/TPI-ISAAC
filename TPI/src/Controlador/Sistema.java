/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Controlador;
import Modelo.Item;
import Modelo.Foja;
import Modelo.DetalleFoja;
import Modelo.Obra;
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
}
