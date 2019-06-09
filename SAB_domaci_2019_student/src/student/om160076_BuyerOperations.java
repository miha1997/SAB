package student;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import operations.BuyerOperations;
import student.jdbc.DB;

public class om160076_BuyerOperations implements BuyerOperations {

	@Override
	public int createBuyer(String name, int cityId) {
		Connection connection=DB.getInstance().getConnection();
        String insertBuyer="insert into Kupac values(?,?)";
        String insertAcc="insert into Racun values(?,?)";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psAcc=connection.prepareStatement(insertAcc);
            PreparedStatement psBuyer=connection.prepareStatement(insertBuyer, PreparedStatement.RETURN_GENERATED_KEYS);){
        	            
        	//insert new buyer
        	psBuyer.setString(1, name);
        	psBuyer.setInt(2, cityId);
        	psBuyer.executeUpdate();
            ResultSet rs = psBuyer.getGeneratedKeys();
            
            rs.next();
            int buyerId = rs.getInt(1);
            
            psAcc.setInt(1, buyerId);
            psAcc.setFloat(2, 0);
        	psAcc.executeUpdate();
            
        	return buyerId;
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_BuyerOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
	}

	@Override
	public int setCity(int buyerId, int cityId) {
		Connection connection=DB.getInstance().getConnection();
        String updateQuery="update Kupac set IdGrad = ? where IdKupac = ?";
        
        try ( Statement statement=connection.createStatement();
            PreparedStatement psUpdate=connection.prepareStatement(updateQuery);){
            
            //update buyer
            psUpdate.setInt(1, cityId);
            psUpdate.setInt(2, buyerId);
            
           	int updated = psUpdate.executeUpdate();
           	
           	if(updated == 0)
           		return -1;
           	return 1;
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_BuyerOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
	}

	@Override
	public int getCity(int buyerId) {
		Connection connection=DB.getInstance().getConnection();
		String getCity="select IdGrad from Kupac where IdKupac = ?";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getCity);){
        	
        	psSelect.setInt(1, buyerId);
            ResultSet rs = psSelect.executeQuery();
            
            //if there is no such city
            if(! rs.next())
            	return -1;

            return rs.getInt(1);
            
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_BuyerOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
	}

	@Override
	public BigDecimal increaseCredit(int buyerId, BigDecimal credit) {	
		if(credit.floatValue() < 0)
			return new BigDecimal(-1).setScale(3);
		
		Connection connection=DB.getInstance().getConnection();
		String getCount="select Stanje from Racun where IdKupac = ?";
        String updateQuery="update Racun set Stanje = Stanje + ? where IdKupac = ?";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getCount);
            PreparedStatement psUpdate=connection.prepareStatement(updateQuery);){
            
           	psUpdate.setDouble(1, credit.floatValue());
            psUpdate.setInt(2, buyerId);
            
           	int updated = psUpdate.executeUpdate();

           	//if there is no acc
           	if(updated == 0)
           		return new BigDecimal(-1).setScale(3);
           	
           	psSelect.setInt(1, buyerId);
           	ResultSet rs = psSelect.executeQuery();
           	
           	rs.next();
			return BigDecimal.valueOf(rs.getFloat(1)).setScale(3);
            
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_BuyerOperations.class.getName()).log(Level.SEVERE, null, ex);
            return new BigDecimal(-1).setScale(3);
        }
	}

	@Override
	public int createOrder(int buyerId) {
		Connection connection=DB.getInstance().getConnection();
        String insertOrder="insert into Narudzbina values('created',?,null,null,null,-1,null,null)";
        
        try ( Statement statement=connection.createStatement();
            PreparedStatement psOrder=connection.prepareStatement(insertOrder, PreparedStatement.RETURN_GENERATED_KEYS);){
        	            
        	//insert new order
        	psOrder.setInt(1, buyerId);
        	psOrder.executeUpdate();
            ResultSet rs = psOrder.getGeneratedKeys();
            
            rs.next();
            return rs.getInt(1);
            
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_BuyerOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
	}

	@Override
	public List<Integer> getOrders(int buyerId) {
		Connection connection=DB.getInstance().getConnection();
        String getAllOrders="select IdNarudzbina from Narudzbina where IdKupac = ?";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getAllOrders);){
        	
        	psSelect.setInt(1, buyerId);
        	ResultSet rs = psSelect.executeQuery();
        	
        	List<Integer> list = new LinkedList<Integer>();
        			        			
            while(rs.next()){
            	list.add(rs.getInt(1));
            }
            
            return list;           
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_BuyerOperations.class.getName()).log(Level.SEVERE, null, ex);
        	return new LinkedList<Integer>();
        }
	}

	@Override
	public BigDecimal getCredit(int buyerId) {		
		Connection connection=DB.getInstance().getConnection();
		String getCount="select Stanje from Racun where IdKupac = ?";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getCount);){
           	
           	psSelect.setInt(1, buyerId);
           	ResultSet rs = psSelect.executeQuery();
           	
           	rs.next();
			return BigDecimal.valueOf(rs.getFloat(1)).setScale(3);
            
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_BuyerOperations.class.getName()).log(Level.SEVERE, null, ex);
            return new BigDecimal(-1).setScale(3);
        }
	}

}
