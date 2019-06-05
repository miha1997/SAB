/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tasha
 */
public class JDBC {
	
	private static JDBC jdbc = null;
	
	private JDBC() {
		
	}
	
	public static JDBC get() {
		if(jdbc == null)
			jdbc = new JDBC();
		
		return jdbc;
	}
	
    public int dodajGrad(){
        Connection connection=DB.getInstance().getConnection();
        String insertQuery="insert into Grad values('Gornji Milanovac')";
        
        try ( Statement statement=connection.createStatement();
            PreparedStatement ps=connection.prepareStatement(insertQuery);){
                       
            //ps.setInt(1, brLk);
            //ps.setString(2, "Tamara");

            return ps.executeUpdate();           
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public static int dodavanjeRadnikaAutoincrement(){
        Connection connection=DB.getInstance().getConnection();
        String insertQuery="insert into Radnik values(?,?,?,1)";
        ResultSet rs=null;
        try (PreparedStatement ps=connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1, "Tamara");
            ps.setString(2, "Sekularac");
            ps.setString(3, "0642914***");
            rs=ps.getGeneratedKeys();
            rs.next();        
            return rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally { 
            if(rs!=null)
                try {rs.close();} catch (SQLException ex1){}
        }
        return 0;
    }
    
    public static int brRadnikaSaImenom(String ime)
    {
        String sql="{ call SPBrojRadnikaSaImenom (?,?) }";
        Connection connection=DB.getInstance().getConnection();
        try (CallableStatement cs= connection.prepareCall(sql)){
            cs.setString(1, ime);
            cs.registerOutParameter(2, java.sql.Types.INTEGER);
            cs.execute();
            return cs.getInt(2);
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
        
    public static void radniciSaImenom(String ime)
    {
        Connection connection=DB.getInstance().getConnection();
        String sql="{ call SPRadniciSaImenom (?) }";
        try (CallableStatement cs= connection.prepareCall(sql)){     
            cs.setString(1, ime);
            try (ResultSet rs=cs.executeQuery()){
                System.out.println("---------------------------------");
                System.out.println("Radnici sa imenom "+ime);
                while(rs.next())
                    System.out.println("BrLK:"+rs.getInt("BrLK")+
                            " prezime:"+rs.getString("Prezime"));
            } catch (SQLException ex) {
                Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        
    public static void ispisSpiskaTabela(){
        Connection connection=DB.getInstance().getConnection();
        String tableQuery="select TABLE_NAME from INFORMATION_SCHEMA.TABLES";
        String columnQuery="select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS  where TABLE_NAME=?";
        try (Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(tableQuery);
            PreparedStatement columnStmt=connection.prepareStatement(columnQuery)){
            while(resultSet.next()){
                System.out.println(resultSet.getString("TABLE_NAME"));
                columnStmt.setString(1, resultSet.getString("TABLE_NAME"));
                ResultSet columnRS=columnStmt.executeQuery();
                while(columnRS.next())
                    System.out.println("    -"+columnRS.getString("COLUMN_NAME"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void ispisSpiskaProceduraIFunkcija(){
        Connection connection=DB.getInstance().getConnection();
        String functionQuery="SELECT NAME FROM sys.all_objects "
                    + "where type in ('FN','AF','FS','FT','IF','TF') and is_ms_shipped=0";
        String procedureQuery="SELECT NAME FROM sys.all_objects where type='P' and is_ms_shipped=0";
        Statement statement=null;
        ResultSet resultSet=null;
        try {
            System.out.println("Funkcije:");
            statement=connection.createStatement();
            resultSet=statement.executeQuery(functionQuery);
            while(resultSet.next()){
                System.out.println("    -"+resultSet.getString("NAME"));   
            }
            System.out.println("Procedure:");
            statement=connection.createStatement();
            resultSet=statement.executeQuery(procedureQuery);
            while(resultSet.next()){
                System.out.println("    -"+resultSet.getString("NAME"));   
            }
        } catch (SQLException ex) {          
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            if(statement!=null)
                try {statement.close();} catch (SQLException ex1){}
            if(resultSet!=null)
                try {resultSet.close();} catch (SQLException ex1){}
        }
    }
    
    /*public static void main(String[] args) {
        //if(dodavanjeRadnika()>0)
         //   System.out.println("Uspesno smo dodali Radnika");
        radniciSaImenom("Tamara");
        
        System.out.println("broj radnika sa imenom Tamara:"+brRadnikaSaImenom("Tamara"));
        
        System.out.println("-----------------------");
        ispisSpiskaTabela();
        System.out.println("-----------------------");
        ispisSpiskaProceduraIFunkcija();
    }*/
    
}
