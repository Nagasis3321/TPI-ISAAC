/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conectar;
import java.sql.DriverManager;
import java.sql.*;
import com.mysql.jdbc.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Isaac
 */
public class Conectar {
    Connection con;
    String user = "root";
    String pass="65uhm0px";
    String driver="com.msyql.jdbc.Driver";
    String url="jdbc:mysql://localhost:3306/mydb";
    
    
    
    public  void Conector(){
        con=null;
        
        try {
            Class.forName(driver);
            con=(Connection) DriverManager.getConnection(url,user,pass);
        } catch (SQLException ex) {
            
            Logger.getLogger(Conectar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conectar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
