package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import operations.CityOperations;
import student.helper.Graph;
import student.jdbc.DB;


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
            //Logger.getLogger(om16076_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
	}

	@Override
	public List<Integer> getCities() {
		Connection connection=DB.getInstance().getConnection();
        String getAllCities="select IdGrad from Grad";
        
        try ( Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(getAllCities);){
        	List<Integer> list = new LinkedList<Integer>();
        			        			
            while(resultSet.next()){
            	list.add(resultSet.getInt(1));
            }
            
            if(list.size() == 0)
            	return null;
            
            return list;           
        } catch (SQLException ex) {
            //Logger.getLogger(om16076_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
		return null;
	}

	@Override
	public int connectCities(int cityId1, int cityId2, int distance) {
		if(cityId1 == cityId2)
			return -1;
		
		Connection connection=DB.getInstance().getConnection();
        String insertQuery="insert into Put values(?, ?, ?)";
        String checkRoad="select count(*) from Put where \r\n" + 
        		"(IdGradDo = ? and IdGradOd = ?) or (IdGradDo = ? and IdGradOd = ?)";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(checkRoad);
            PreparedStatement psInsert=connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);){
        	
        	//check if road already exists
        	psSelect.setInt(1, cityId1);
        	psSelect.setInt(2, cityId2);
        	psSelect.setInt(3, cityId2);
        	psSelect.setInt(4, cityId1);
            ResultSet rs = psSelect.executeQuery();
            
            rs.next();
            int temp = rs.getInt(1);
            if(temp != 0)
            	return -1;
            
            //insert new road
            psInsert.setInt(1, cityId1);
            psInsert.setInt(2, cityId2);
            psInsert.setInt(3, distance);
            psInsert.executeUpdate();
            rs = psInsert.getGeneratedKeys();
            
            rs.next();
            return rs.getInt(1);
            
        } catch (SQLException ex) {
            //Logger.getLogger(om16076_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
	}

	@Override
	public List<Integer> getConnectedCities(int cityId) {
		Graph graph = Graph.getGraph();
		
		return graph.getConnectedCities(cityId);
	}

	@Override
	public List<Integer> getShops(int cityId) {
		Connection connection=DB.getInstance().getConnection();
        String getAllShops="select IdProdavnica from Prodavnica where IdGrad = ?";
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getAllShops);){
        	
        	psSelect.setInt(1, cityId);
        	ResultSet rs = psSelect.executeQuery();
        	
        	List<Integer> list = new LinkedList<Integer>();
        	
            while(rs.next()){
            	list.add(rs.getInt(1));
            }
            
            if(list.size() == 0)
            	return null;
            
            return list;           
        } catch (SQLException ex) {
            //Logger.getLogger(om16076_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
		return null;
	}

}
