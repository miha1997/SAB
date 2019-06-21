package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import operations.ArticleOperations;
import student.jdbc.DB;

public class om160076_ArticleOperations implements ArticleOperations {

	@Override
	public int createArticle(int shopId, String articleName, int articlePrice) {
		Connection connection=DB.getInstance().getConnection();
        String insertQuery="insert into Artikal values(?,?,?,?)";
        
        try (PreparedStatement psInsert=connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);){
        	
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
        	//ex.printStackTrace();
            return -1;
        }
	}

}
