package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

import operations.ArticleOperations;
import student.jdbc.DB;

public class om160076_ArticleOperations implements ArticleOperations {

	@Override
	public int createArticle(int shopId, String articleName, int articlePrice) {
		Connection connection=DB.getInstance().getConnection();
        String insertQuery="insert into Artikal values(?,?,?,?)";
        
        try ( Statement statement=connection.createStatement();
            PreparedStatement psInsert=connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);){
        	
        	//insert new article
            psInsert.setString(1, articleName);
            psInsert.setFloat(2, articlePrice);
            psInsert.setInt(3, 0);
            psInsert.setInt(4, shopId);
            psInsert.executeUpdate();
            
            ResultSet rs = psInsert.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException ex) {
            //Logger.getLogger(om160076_ArticleOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
	}

}
