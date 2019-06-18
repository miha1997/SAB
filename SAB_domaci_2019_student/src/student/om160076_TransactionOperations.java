package student;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import operations.TransactionOperations;
import student.jdbc.DB;

public class om160076_TransactionOperations implements TransactionOperations {

	@Override
	public BigDecimal getBuyerTransactionsAmmount(int buyerId) {
		Connection connection=DB.getInstance().getConnection();
        String getSum="select sum(t.Iznos) from NaplataKupac as n, Transakcija as t where IdRacun = ?  and n.IdNaplata = t.IdTransakcija";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getSum);){
        	
        	psSelect.setInt(1, buyerId);
        	ResultSet rs = psSelect.executeQuery();
        	
        	if(!rs.next())
        		return new BigDecimal(0).setScale(3);
        	
			return BigDecimal.valueOf(rs.getFloat(1)).setScale(3);         
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        	return new BigDecimal(-1).setScale(3);
        }
	}

	@Override
	public BigDecimal getShopTransactionsAmmount(int shopId) {
		Connection connection=DB.getInstance().getConnection();
        String getSum="select sum(t.Iznos) from NaplataProdavnica as n, Transakcija as t where IdProdavnica = ? and t.IdTransakcija = n.IdNaplata";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getSum);){
        	
        	psSelect.setInt(1, shopId);
        	ResultSet rs = psSelect.executeQuery();
        	
        	if(!rs.next())
        		return new BigDecimal(0).setScale(3);
			return BigDecimal.valueOf(rs.getFloat(1)).setScale(3);         
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
            return new BigDecimal(0).setScale(3);
        }
	}

	@Override
	public List<Integer> getTransationsForBuyer(int buyerId) {
		Connection connection=DB.getInstance().getConnection();
        String getAll="select IdNaplata from NaplataKupac where IdRacun = ?";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getAll);){
        	
        	psSelect.setInt(1, buyerId);
        	ResultSet rs = psSelect.executeQuery();
        	
        	List<Integer> list = new LinkedList<Integer>();
        			        			
            while(rs.next()){
            	list.add(rs.getInt(1));
            }
            
            return list;           
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        	return null;
        }
	}

	@Override
	public int getTransactionForBuyersOrder(int orderId) {
		Connection connection=DB.getInstance().getConnection();
        String getT="select IdNaplata from NaplataKupac where IdNarudzbina = ?";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getT);){
        	
        	psSelect.setInt(1, orderId);
        	ResultSet rs = psSelect.executeQuery();
        	        			        			
            if(!rs.next())
            	return -1;
            return rs.getInt(1);
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        	return -1;
        }
	}

	@Override
	public int getTransactionForShopAndOrder(int orderId, int shopId) {
		Connection connection=DB.getInstance().getConnection();
        String getT="select IdNaplata from NaplataProdavnica where IdNarudzbina = ? and IdProdavnica = ?";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getT);){
        	
        	psSelect.setInt(1, orderId);
        	psSelect.setInt(2, shopId);
        	ResultSet rs = psSelect.executeQuery();
        	        			        			
            if(!rs.next())
            	return -1;
            return rs.getInt(1);
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        	return -1;
        }
	}

	@Override
	public List<Integer> getTransationsForShop(int shopId) {
		Connection connection=DB.getInstance().getConnection();
        String getAll="select IdNaplata from NaplataProdavnica where IdProdavnica = ?";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getAll);){
        	
        	psSelect.setInt(1, shopId);
        	ResultSet rs = psSelect.executeQuery();
        	
        	List<Integer> list = new LinkedList<Integer>();
        			        			
            while(rs.next()){
            	list.add(rs.getInt(1));
            }
            
            if(list.size() == 0)
            	return null;
            return list;           
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        	return null;
        }
	}

	@Override
	public Calendar getTimeOfExecution(int transactionId) {
		Connection connection=DB.getInstance().getConnection();
        String getDate="select Datum from Transakcija where IdTransakcija = ?";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getDate);){
        	
        	psSelect.setInt(1, transactionId);
        	ResultSet rs = psSelect.executeQuery();
        	
        	rs.next();
        	
        	Calendar cal = Calendar.getInstance();
        	
        	if(rs.getDate(1) == null)
        		return null;
        	
        	cal.setTime(rs.getDate(1));
        	return cal;
         
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
        	return null;
        }
	}

	@Override
	public BigDecimal getAmmountThatBuyerPayedForOrder(int orderId) {
		Connection connection=DB.getInstance().getConnection();
        String getSum="select t.Iznos from NaplataKupaca as n, Transakcija as t where IdNarudzbina = ? and n.IdNaplata = t.IdTransakcija";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getSum);){
        	
        	psSelect.setInt(1, orderId);
        	ResultSet rs = psSelect.executeQuery();
        	
        	if(!rs.next())
        		return new BigDecimal(0).setScale(3);
        	
			return BigDecimal.valueOf(rs.getFloat(1)).setScale(3);         
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        	return new BigDecimal(-1).setScale(3);
        }
	}

	@Override
	public BigDecimal getAmmountThatShopRecievedForOrder(int shopId, int orderId) {
		Connection connection=DB.getInstance().getConnection();
        String getSum="select t.Iznos from NaplataProdavnica as n, Transakcija as t where n.IdNarudzbina = ? and n.IdProdavnica = ? and n.IdNaplata = t.IdTransakcija";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getSum);){
        	
        	psSelect.setInt(1, orderId);
        	psSelect.setInt(2, shopId);
        	ResultSet rs = psSelect.executeQuery();
        	
        	if(!rs.next())
        		return new BigDecimal(0).setScale(3);
        	
			return BigDecimal.valueOf(rs.getFloat(1)).setScale(3);         
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        	return new BigDecimal(-1).setScale(3);
        }
	}

	@Override
	public BigDecimal getTransactionAmount(int transactionId) {
		Connection connection=DB.getInstance().getConnection();
        String getSum="select Iznos from Transakcija where IdTransakcija = ?";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getSum);){
        	
        	psSelect.setInt(1, transactionId);
        	ResultSet rs = psSelect.executeQuery();
        	
        	if(!rs.next())
        		return new BigDecimal(0).setScale(3);
        	
			return BigDecimal.valueOf(rs.getFloat(1)).setScale(3);         
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        	return new BigDecimal(-1).setScale(3);
        }
	}

	@Override
	public BigDecimal getSystemProfit() {
		Connection connection=DB.getInstance().getConnection();
        String getSum="select Suma from ProfitSistema";
        
        try ( Statement statement=connection.createStatement();){
        	

        	ResultSet rs = statement.executeQuery(getSum);
        	
        	if(!rs.next())
        		return new BigDecimal(0).setScale(3);
        	
			return BigDecimal.valueOf(rs.getFloat(1)).setScale(3);         
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_TransactionOperations.class.getName()).log(Level.SEVERE, null, ex);
        	return new BigDecimal(-1).setScale(3);
        }
	}

}
