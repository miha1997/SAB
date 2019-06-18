/**
 * 
 */
package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import operations.GeneralOperations;
import student.helper.Graph;
import student.helper.Timer;
import student.jdbc.DB;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author Mihailo
 *
 */
public class om160076_GeneralOperations implements GeneralOperations {

	@Override
	public void setInitialTime(Calendar time) {
		Timer timer = Timer.getTimer();
		
		timer.setTime(time);
		
		Connection connection=DB.getInstance().getConnection();
        String setDate="insert into TrenutniDatum values (?)";
        String setSum="insert into ProfitSistema values (0)";
        
        try(Statement statement = connection.createStatement();
        	PreparedStatement pUpdate = connection.prepareStatement(setDate);){
        	
        	statement.executeUpdate(setSum);       	
        	pUpdate.setDate(1, new java.sql.Date(timer.getTime().getTimeInMillis()));
        	pUpdate.executeUpdate();
        	
        	
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Calendar time(int days) {
		Timer timer = Timer.getTimer();
		
		for(int i = 0; i < days; i++)
			timer.passDay();
		
		return timer.getTime();
	}

	@Override
	public Calendar getCurrentTime() {
		Timer timer = Timer.getTimer();
		
		return timer.getTime();
	}

	@Override
	public void eraseAll() {
		Connection connection=DB.getInstance().getConnection();
        String deleteTransactionBuyer="delete from NaplataKupac";
        String deleteTransactionShop="delete from NaplataProdavnica";
        String deleteAcc="delete from Racun";
        String deleteItem="delete from Stavka";
        String deleteArticle="delete from Artikal";
        
        String deleteOrder="delete from Narudzbina";
        String deletePath="delete from Put";
        String deleteBuyer="delete from Kupac";
        String deleteShop="delete from Prodavnica";
        String deleteCity="delete from Grad";
        String deleteT="delete from Transakcija";
        
        String deleteDatum="delete from TrenutniDatum";
        String setProfit="update ProfitSistema set Suma = 0";
        
        String seed="DBCC CHECKIDENT ('Grad', RESEED, 0); DBCC CHECKIDENT ('NaplataKupac', RESEED, 0);" +
        		"DBCC CHECKIDENT ('NaplataProdavnica', RESEED, 0);DBCC CHECKIDENT ('Stavka', RESEED, 0);" +
        		"DBCC CHECKIDENT ('Artikal', RESEED, 0); DBCC CHECKIDENT ('Narudzbina', RESEED, 0);" +
        		"DBCC CHECKIDENT ('Transakcija', RESEED, 0);" +
        		"DBCC CHECKIDENT ('Kupac', RESEED, 0);DBCC CHECKIDENT ('Prodavnica', RESEED, 0);";
        
        
        
        Graph graph = Graph.getGraph();
        graph.eraseGraph();
        
        try ( Statement statement=connection.createStatement();){        	            
        	statement.executeUpdate(deleteTransactionBuyer);
        	statement.executeUpdate(deleteTransactionShop);
        	statement.executeUpdate(deleteAcc);
        	statement.executeUpdate(deleteItem);
        	statement.executeUpdate(deleteArticle);
        	statement.executeUpdate(deleteT);
        	statement.executeUpdate(deleteDatum);
        	
        	statement.executeUpdate(deleteOrder);
        	statement.executeUpdate(deletePath);
        	statement.executeUpdate(deleteBuyer);
        	statement.executeUpdate(deleteShop);
        	statement.executeUpdate(deleteCity);
        	statement.executeUpdate(setProfit);
        	
        	statement.executeUpdate(seed);
	
        } catch (SQLException ex) {
            Logger.getLogger(om160076_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
           
        }

	}

}
