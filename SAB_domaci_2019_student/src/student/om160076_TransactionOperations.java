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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getShopTransactionsAmmount(int shopId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getTransationsForBuyer(int buyerId) {
		Connection connection=DB.getInstance().getConnection();
        String getAll="select IdNaplata from NaplataKupac where IdKupac = ?";
        
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTransactionForShopAndOrder(int orderId, int shopId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> getTransationsForShop(int shopId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Calendar getTimeOfExecution(int transactionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getAmmountThatBuyerPayedForOrder(int orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getAmmountThatShopRecievedForOrder(int shopId, int orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getTransactionAmount(int transactionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getSystemProfit() {
		// TODO Auto-generated method stub
		return null;
	}

}
