package clas;
//Realiza consulta e inserciones

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class sqlUsuario extends Conectar {
//SE INGRESAN LOS DATOS A LA BASE DE DATOS TRABAJO
    public boolean registrar(usuario usr) {

        PreparedStatement ps = null;
        Conectar ct = new Conectar();
        Connection con = ct.conexion();
        //INSEERTAR DENTRO DE LA TABLA TRABAJO LOS DATOS DE REGISTRO
        String sql = "INSERT INTO trabajo (usuario, password, Email, id_tipo, Nombre) VALUES(?,?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, usr.getUsuario());

            ps.setString(2, usr.getPassword());

            ps.setString(3, usr.getEmail());

            ps.setInt(4, usr.getId_tipo());

            ps.setString(5, usr.getNombre());

            ps.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(sqlUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    //VERIFICAR SI EL USUARIO YA ESTÁ REGISTRADO
    public int usuario_existente(String usuario) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conectar ct = new Conectar();
        Connection con = ct.conexion();

        String sql = "SELECT count(ID) FROM trabajo WHERE usuario = ? ";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 1;

        } catch (SQLException ex) {
            Logger.getLogger(sqlUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
    }
//LOGIN
public boolean login(usuario usr) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conectar ct = new Conectar();
        Connection con = ct.conexion();

        String sql = "SELECT ID,usuario,password,id_tipo,Nombre FROM trabajo WHERE usuario = ? ";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getUsuario());
            rs = ps.executeQuery();
            if (rs.next()) {
                if(usr.getPassword().equals(rs.getString(3))){
                    usr.setID(rs.getInt(1));
                    usr.setNombre(rs.getString(5));
                    usr.setId_tipo(rs.getInt(4));
                    return true;
                }else{
                    return false;
                }
            }
            return false;

        } catch (SQLException ex) {
            Logger.getLogger(sqlUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
