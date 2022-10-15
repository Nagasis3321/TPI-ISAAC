/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;
import Controlador.*;
import Modelo1.Empresa;
import Modelo1.*;
import Persistencia.EmpresaJpaController;


import java.util.Date;
/**
 *
 * @author USUARIO
 */
public class TPI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
            ControladorCreadorBD cont = new ControladorCreadorBD();
            Empresa e1 = new Empresa();
            e1.setRazonSocial("MSR.SRL");
            e1.setDireccion("Jaureche 2405");
            e1.setRepresentateLegal("Marcos Rojo");
            e1.setRepresentateTecnico("Marcos Negros");
            e1.setCuit(2043332674);
            cont.AgregarEmpresa(e1);
            
    }
    
}
