

package clas;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
//CONEXION Y NADA MAS
public class Conectar {
      private Connection cone;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String pass = "";
    private static final String url = "jdbc:mysql://localhost:3306/personas";
    
    public Conectar(){
        cone = null;
        try{
            Class.forName(driver);
            cone = (Connection) DriverManager.getConnection(url, user, pass);
            if(cone != null){
               // System.out.println("Conexion ok");
            }
        }catch(Exception e){
             System.out.println("Error Conexion..."+e);
        }
    }
     public Connection conexion(){
         return cone;
    }  
}
