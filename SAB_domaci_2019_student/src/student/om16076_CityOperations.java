package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import operations.CityOperations;
import student.jdbc.DB;
import student.jdbc.JDBC;

public class om16076_CityOperations implements CityOperations {

	@Override
	public int createCity(String name) {
		Connection connection=DB.getInstance().getConnection();
        String insertQuery="insert into Grad values(?)";
        
        try ( Statement statement=connection.createStatement();
            PreparedStatement ps=connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);){

            ps.setString(1, name);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            
            rs.next();
            return rs.getInt(1);
        } catch (SQLException ex) {
            //Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
	}

	@Override
	public List<Integer> getCities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int connectCities(int cityId1, int cityId2, int distance) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> getConnectedCities(int cityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getShops(int cityId) {
		// TODO Auto-generated method stub
		return null;
	}

}
