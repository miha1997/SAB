package student;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import operations.OrderOperations;
import student.jdbc.DB;

public class om160076_OrderOperations implements OrderOperations {

	@Override
	public int addArticle(int orderId, int articleId, int count) {
		Connection connection=DB.getInstance().getConnection();
		String getCount="select NaStanju, Cena, IdProdavnica from Artikal where IdArtikal = ?";
		String getDiscount="select Popust from Prodavnica where IdProdavnica = ?";
		String getItemId="select IdStavka from Stavka where IdArtikal = ? and IdNarudzbina = ?";
		String updateArticle="update Artikal set NaStanju = NaStanju - ? where IdArtikal = ?";
		String updateOrder="update Narudzbina set Suma = Suma + ? where IdNarudzbina = ?";
		String updateItem="update Stavka set Kolicina = Kolicina + ?, Suma = Suma + ? where IdArtikal = ? and IdNarudzbina = ?";		
        String insertItem="insert into Stavka values(?,?,?,?)";
       
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psGetCount=connection.prepareStatement(getCount);
        	PreparedStatement psGetDiscount=connection.prepareStatement(getDiscount);
        	PreparedStatement psUpdateArticle=connection.prepareStatement(updateArticle);
        	PreparedStatement psUpdateItem=connection.prepareStatement(updateItem);
        	PreparedStatement psItemId=connection.prepareStatement(getItemId);
        	PreparedStatement psUpdateOrder=connection.prepareStatement(updateOrder);
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
            
            double price = rs.getDouble(2);
            int idShop = rs.getInt(3);
            
            //get discount
            psGetDiscount.setInt(1, idShop);
            rs = psGetDiscount.executeQuery();
            
            rs.next();
            double discount = rs.getDouble(1);
            
            discount = 100 - discount;
            discount /= 100;
            
            price *= discount*count;
            
            //update article
            psUpdateArticle.setInt(1, count);
            psUpdateArticle.setInt(2, articleId);
            psUpdateArticle.executeUpdate();
           	
            //update order
            psUpdateOrder.setDouble(1, price);
            psUpdateOrder.setInt(2, orderId);
            psUpdateOrder.executeUpdate();
            
            //update item if exits
            psUpdateItem.setInt(1, count);
            psUpdateItem.setDouble(2, price);
            psUpdateItem.setInt(3, articleId);
            psUpdateItem.setInt(4, orderId);
            
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
           		psInsertItem.setDouble(4, price);
           		psInsertItem.executeUpdate();
                rs = psInsertItem.getGeneratedKeys();
                
                rs.next();
                return rs.getInt(1);
           	}
            
        } catch (SQLException ex) {
            Logger.getLogger(om160076_OrderOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
	}

	@Override
	public int removeArticle(int orderId, int articleId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> getItems(int orderId) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Calendar getSentTime(int orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Calendar getRecievedTime(int orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBuyer(int orderId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLocation(int orderId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
