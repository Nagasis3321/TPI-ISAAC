/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;
import Vistas.CreadorObra;
import Controlador.*;
/**
 *
 * @author USUARIO
 */
public class TPI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
        
        Controlador con = new Controlador();
        CreadorObra v1 = new CreadorObra(con);
        
    }
    
}
