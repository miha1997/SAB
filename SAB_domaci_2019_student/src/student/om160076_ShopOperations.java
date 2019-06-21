package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import operations.ShopOperations;
import student.jdbc.DB;

public class om160076_ShopOperations implements ShopOperations {

	@Override
	public int createShop(String name, String cityName) {
		Connection connection=DB.getInstance().getConnection();
		String getCity="select IdGrad from Grad where Naziv = ?";
        String insertQuery="insert into Prodavnica values(?,?,?)";
        
        try (PreparedStatement psSelect=connection.prepareStatement(getCity);
            PreparedStatement psInsert=connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);){
        	
        	psSelect.setString(1, cityName);
            ResultSet rs = psSelect.executeQuery();
            
            //if there is no such city
            if(! rs.next())
            	return -1;

            int cityId = rs.getInt(1);
            
          //insert new shop
            psInsert.setString(1, name);
            psInsert.setInt(2, cityId);
            psInsert.setFloat(3, 0);
            psInsert.executeUpdate();
            rs = psInsert.getGeneratedKeys();
            
            rs.next();
            return rs.getInt(1);
        } catch (SQLException ex) {
        	//ex.printStackTrace();
            return -1;
        }
	}

	@Override
	public int setCity(int shopId, String cityName) {
		Connection connection=DB.getInstance().getConnection();
		String getCity="select IdGrad from Grad where Naziv = ?";
        String updateQuery="update Prodavnica set IdGrad = ? where IdProdavnica = ?";
        
        try (PreparedStatement psSelect=connection.prepareStatement(getCity);
            PreparedStatement psUpdate=connection.prepareStatement(updateQuery);){
        	
        	psSelect.setString(1, cityName);
            ResultSet rs = psSelect.executeQuery();
            
            //if there is no such city
            if(! rs.next())
            	return -1;

            int cityId = rs.getInt(1);
            
            //update shop
            psUpdate.setInt(1, cityId);
            psUpdate.setInt(2, shopId);
            
           	int updated = psUpdate.executeUpdate();
           	
           	if(updated == 0)
           		return -1;
           	return 1;
        } catch (SQLException ex) {
        	//ex.printStackTrace();
            return -1;
        }
	}

	@Override
	public int getCity(int shopId) {
		Connection connection=DB.getInstance().getConnection();
		String getCity="select IdGrad from Prodavnica where IdProdavnica = ?";
        
        try (PreparedStatement psSelect=connection.prepareStatement(getCity);){
        	
        	psSelect.setInt(1, shopId);
            ResultSet rs = psSelect.executeQuery();
            
            //if there is no such city
            if(! rs.next())
            	return -1;

            return rs.getInt(1);
            
        } catch (SQLException ex) {
        	//ex.printStackTrace();
            return -1;
        }
	}

	@Override
	public int setDiscount(int shopId, int discountPercentage) {
		if(discountPercentage <= 0 || discountPercentage >= 100)
			return -1;
		
		Connection connection=DB.getInstance().getConnection();
        String updateQuery="update Prodavnica set Popust = ? where IdProdavnica = ?";
        
        try (PreparedStatement psUpdate=connection.prepareStatement(updateQuery);){
        	
            psUpdate.setFloat(1, discountPercentage);
            psUpdate.setInt(2, shopId);
            
           	int updated = psUpdate.executeUpdate();
           	
           	if(updated == 0)
           		return -1;
           	return 1;
        } catch (SQLException ex) {
        	//ex.printStackTrace();
            return -1;
        }
	}

	@Override
	public int increaseArticleCount(int articleId, int increment) {
		if(increment <= 0)
			return -1;
		
		Connection connection=DB.getInstance().getConnection();
		String getCount="select NaStanju from Artikal where IdArtikal = ?";
        String updateQuery="update Artikal set NaStanju = NaStanju + ? where IdArtikal = ?";
        
        try (PreparedStatement psSelect=connection.prepareStatement(getCount);
            PreparedStatement psUpdate=connection.prepareStatement(updateQuery);){
            
            //update article
            psUpdate.setInt(1, increment);
            psUpdate.setInt(2, articleId);
            
           	int updated = psUpdate.executeUpdate();
           	
           	//if there is no article
           	if(updated == 0)
           		return -1;
           	
           	psSelect.setInt(1, articleId);
           	ResultSet rs = psSelect.executeQuery();
           	
           	rs.next();
            return rs.getInt(1);
            
        } catch (SQLException ex) {
            //ex.printStackTrace();
            return -1;
        }
	}

	@Override
	public int getArticleCount(int articleId) {
		Connection connection=DB.getInstance().getConnection();
		String getCount="select NaStanju from Artikal where IdArtikal = ?";
        
        try (PreparedStatement psSelect=connection.prepareStatement(getCount);){
        	
        	psSelect.setInt(1, articleId);
           	ResultSet rs = psSelect.executeQuery();
           	
           	if(!rs.next())
           		return -1;
            return rs.getInt(1);
            
        } catch (SQLException ex) {
            //ex.printStackTrace();
            return -1;
        }
	}

	@Override
	public List<Integer> getArticles(int shopId) {
		Connection connection=DB.getInstance().getConnection();
        String getAllArticles="select IdArtikal from Artikal where IdProdavnica = ?";
        
        try (PreparedStatement psSelect=connection.prepareStatement(getAllArticles);){
        	
        	psSelect.setInt(1, shopId);
        	ResultSet rs = psSelect.executeQuery();
        	
        	List<Integer> list = new LinkedList<Integer>();
        			        			
            while(rs.next()){
            	list.add(rs.getInt(1));
            }
            
            return list;           
        } catch (SQLException ex) {
            //ex.printStackTrace();
        	return null;
        }
	}

	@Override
	public int getDiscount(int shopId) {
		Connection connection=DB.getInstance().getConnection();
		String getCity="select Popust from Prodavnica where IdProdavnica = ?";
        
        try (PreparedStatement psSelect=connection.prepareStatement(getCity);){
        	
        	psSelect.setInt(1, shopId);
            ResultSet rs = psSelect.executeQuery();
            
            //if there is no such city
            if(! rs.next())
            	return -1;

            return rs.getInt(1);
            
        } catch (SQLException ex) {
            //ex.printStackTrace();
            return -1;
        }
	}

}
