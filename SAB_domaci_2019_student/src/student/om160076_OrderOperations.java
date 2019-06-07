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

import operations.OrderOperations;
import student.jdbc.DB;

public class om160076_OrderOperations implements OrderOperations {

	@Override
	public int addArticle(int orderId, int articleId, int count) {
		Connection connection=DB.getInstance().getConnection();
		String getCount="select NaStanju from Artikal where IdArtikal = ?";
		String getItemId="select IdStavka from Stavka where IdArtikal = ? and IdNarudzbina = ?";
		String updateArticle="update Artikal set NaStanju = NaStanju - ? where IdArtikal = ?";
		String updateItem="update Stavka set Kolicina = Kolicina + ? where IdArtikal = ? and IdNarudzbina = ?";		
        String insertItem="insert into Stavka values(?,?,?)";
       
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psGetCount=connection.prepareStatement(getCount);
        	PreparedStatement psUpdateArticle=connection.prepareStatement(updateArticle);
        	PreparedStatement psUpdateItem=connection.prepareStatement(updateItem);
        	PreparedStatement psItemId=connection.prepareStatement(getItemId);
            PreparedStatement psInsertItem=connection.prepareStatement(insertItem, PreparedStatement.RETURN_GENERATED_KEYS);){
        	
        	//check if there are enough items
        	psGetCount.setInt(1, articleId);
            ResultSet rs = psGetCount.executeQuery();
            
            //if there is no such article
            if(! rs.next())
            	return -1;
            
            //not enough items
            if( rs.getInt(1) < count)
            	return -1;
            
            //update article
            psUpdateArticle.setInt(1, count);
            psUpdateArticle.setInt(2, articleId);
            psUpdateArticle.executeUpdate();
            
            //update item if exits
            psUpdateItem.setInt(1, count);
            psUpdateItem.setInt(2, articleId);
            psUpdateItem.setInt(3, orderId);
            
           	int updated = psUpdateItem.executeUpdate();
           	//if exits
           	if(updated == 1){
           		psItemId.setInt(1, articleId);
           		psItemId.setInt(2, orderId);
                rs = psItemId.executeQuery();
                
                rs.next();
                return rs.getInt(1);
           		
           	}else {
           		psInsertItem.setInt(1, orderId);
           		psInsertItem.setInt(2, articleId);
           		psInsertItem.setInt(3, count);
           		psInsertItem.executeUpdate();
                rs = psInsertItem.getGeneratedKeys();
                
                rs.next();
                return rs.getInt(1);
           	}
            
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
	}

	@Override
	public int removeArticle(int orderId, int articleId) {
		Connection connection=DB.getInstance().getConnection();
		String deleteItem="delete from Stavka where IdArtikal = ? and IdNarudzbina = ?";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psDeleteItem=connection.prepareStatement(deleteItem);){
        	
        	psDeleteItem.setInt(1, articleId);
        	psDeleteItem.setInt(2, orderId);       	
            int deleted = psDeleteItem.executeUpdate();
            
            if(deleted == 0)
            	return -1;
            
            return 1;
            
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
	}

	@Override
	public List<Integer> getItems(int orderId) {
		Connection connection=DB.getInstance().getConnection();
        String getAllItems="select IdStavka from Stavka where IdNarudzbina = ?";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getAllItems);){
        	
        	psSelect.setInt(1, orderId);
        	ResultSet rs = psSelect.executeQuery();
        	
        	List<Integer> list = new LinkedList<Integer>();
        			        			
            while(rs.next()){
            	list.add(rs.getInt(1));
            }
            
            return list;           
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
        	return new LinkedList<Integer>();
        }
	}

	@Override
	public int completeOrder(int orderId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getFinalPrice(int orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getDiscountSum(int orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getState(int orderId) {
		Connection connection=DB.getInstance().getConnection();
        String getStatus="select Status from Narudzbina where IdNarudzbina = ?";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getStatus);){
        	
        	psSelect.setInt(1, orderId);
        	ResultSet rs = psSelect.executeQuery();
        	
        	rs.next();
        	return rs.getString(1);
         
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
        	return "";
        }
	}

	@Override
	public Calendar getSentTime(int orderId) {
		Connection connection=DB.getInstance().getConnection();
        String getDate="select DatumSlanja from Narudzbina where IdNarudzbina = ?";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getDate);){
        	
        	psSelect.setInt(1, orderId);
        	ResultSet rs = psSelect.executeQuery();
        	
        	rs.next();
        	
        	Calendar cal = Calendar.getInstance();
        	cal.setTime(rs.getDate(1));
        	return cal;
         
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
        	return null;
        }
	}

	@Override
	public Calendar getRecievedTime(int orderId) {
		Connection connection=DB.getInstance().getConnection();
        String getDate="select DatumStizanja from Narudzbina where IdNarudzbina = ?";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getDate);){
        	
        	psSelect.setInt(1, orderId);
        	ResultSet rs = psSelect.executeQuery();
        	
        	rs.next();
        	
        	Calendar cal = Calendar.getInstance();
        	cal.setTime(rs.getDate(1));
        	return cal;
         
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
        	return null;
        }
	}

	@Override
	public int getBuyer(int orderId) {
		Connection connection=DB.getInstance().getConnection();
		String getBuyer="select IdKupac from Narudzbina where IdNarudzbina = ?";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getBuyer);){
        	
        	psSelect.setInt(1, orderId);
            ResultSet rs = psSelect.executeQuery();
            
            //if there is no such order
            if(! rs.next())
            	return -1;

            return rs.getInt(1);
            
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
	}

	@Override
	public int getLocation(int orderId) {
		Connection connection=DB.getInstance().getConnection();
		String getCity="select TrenutniGrad from Narudzbina where IdNarudzbina = ?";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getCity);){
        	
        	psSelect.setInt(1, orderId);
            ResultSet rs = psSelect.executeQuery();
            
            //if there is no such order
            if(! rs.next())
            	return -2;

            return rs.getInt(1);
            
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -2;
        }
	}

}
