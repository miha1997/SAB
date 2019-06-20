package student.helper;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import javax.security.auth.Destroyable;

import student.jdbc.DB;

public class Graph {
	//number of cities
	private int cnt = 0;
	
	//no connection
	private int MAX = 10000;
	
	//city ids
	private Vector<Integer> ids = new Vector<>();
	
	//flag to show when graph is ready
	private boolean calculated = false;
	
	//graph representation
	private Vector<Vector<Integer>> W;
	
	//distances
	private int [][] D;
	//previous city for destination
	private Vector<Vector<Integer>> T;
	
	private static Graph graph = null;
	
	private Graph() {
		//loads graph from database if exists
		loadGraph();
	}
	
	//gets city index in array
	private int getIndex(int city) {
		
		for(int i = 0; i < ids.size(); i++) 
			if(ids.get(i) == city)
				return i;
		
		return -1;
		
	}
	
	public static Graph getGraph() {
		if(graph == null)
			graph = new Graph();
		
		return graph;
	}
	
	private void destroy() {
		calculated = false;
		D = null;
	}
	
	public void eraseGraph() {
		cnt = 0;
		calculated = false;
		
		ids = new Vector<>();
	}
	
	private void calculate() {
		calculated = true;
		
		D = new int[cnt][cnt];
		
		//initialize matrix
		for(int i = 0 ; i < cnt; i++)
			for(int j = 0; j < cnt; j++)
				D[i][j] = W.get(i).get(j);
		
		//Floyd's algorithm
		for(int k = 0 ; k < cnt; k++) 
			for(int i = 0 ; i < cnt; i++)
				for(int j = 0; j < cnt; j++) 
					if(D[i][j] > D[i][k] + D[k][j]) {
						//T[i][j] = T[k][j]
						T.get(i).set(j, T.get(k).get(j));
						
						D[i][j] = D[i][k] + D[k][j];
					}
		
	}
	
	public List<Integer> getConnectedCities(int cityId){
		if(! calculated)
			calculate();
		
		List<Integer> list = new LinkedList<Integer>();
		
		int i = 0;
		//get index from ids
		for(int k = 0; k < cnt; k++) {
			if(ids.get(k) == cityId)
				i = k;
		}
		
		for(int j = 0; j < cnt; j++)
			if(T.get(i).get(j) != - 1) {
				list.add(ids.get(j));
			}
		
		return list;
	}
	
	public int[] checkProgress(int curCity, int progress, int buyerCity) {
		int cur = getIndex(curCity);
		int buyer = getIndex(buyerCity);
		
		int nextCity = T.get(buyer).get(cur);
		
		//if it is same city
		if(cur == buyer)
			nextCity = cur;
		int distance = D[cur][nextCity];		
		
		nextCity = ids.get(nextCity);
		
		progress++;
		if(progress == distance) {
			progress = 0;
		}
		else {
			nextCity = curCity;
		}
		
		int res [] = new int[2];
		res[0] = progress;
		res[1] = nextCity;
		
		return res;
	}
	
	private void loadGraph() {
		Connection connection=DB.getInstance().getConnection();
        String getCities="select IdGrad from Grad";
        String getRoads="select IdGradOd, IdGradDo, Cena from Put";
        
        try (Statement psCities = connection.createStatement();
        	Statement psPaths = connection.createStatement();){
        	
        	ResultSet cities = psCities.executeQuery(getCities);
        	ResultSet roads = psPaths.executeQuery(getRoads);
        	
        	while(cities.next()) {
        		int city = cities.getInt(1);
        		
        		addCity(city);
        	}
        	
        	while(roads.next()) {
        		int city1 = roads.getInt(1);
        		int city2 = roads.getInt(2);
        		int price = roads.getInt(3);
        		
        		addPath(city1, city2, price);
        	}
        	
        
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int[] prepareArticles(LinkedList<Integer> cityList, int orderId) {
		Connection connection=DB.getInstance().getConnection();
        String getMyCity="select Kupac.IdGrad from Narudzbina, Kupac where Narudzbina.IdNarudzbina = ? and Narudzbina.IdKupac = Kupac.IdKupac";
        
        if(! calculated)
        	calculate();
        
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psSelect=connection.prepareStatement(getMyCity);){
        	
        	psSelect.setInt(1, orderId);
        	ResultSet rs = psSelect.executeQuery();
        	
        	rs.next();
        	int myCity = rs.getInt(1);
        	int i = getIndex(myCity);
        	
        	//find nearest city
        	int shopCity = -1, minD = MAX;
        	for(Integer city : cityList) {
        		int j = getIndex(city);
        		
        		if(D[i][j] < minD) {
        			shopCity = j;
        			minD = D[i][j];
        		}
        	}
        	
        	//find critical distance to it
        	i = shopCity;
        	int maxD = 0;
        	for(Integer city : cityList) {
        		int j = getIndex(city);
        		
        		if(D[i][j] > maxD) 
        			maxD = D[i][j];
        		
        	}
        	
        	int res [] = new int[2];
        	res[0] = maxD;
        	res[1] = ids.get(shopCity);
        	
        	return res;
        } catch (SQLException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        	return new int[2];
        }
	}
	
	public void addCity(int cityId) {
		destroy();
		
		ids.add(cityId);
		cnt++;
		
		if(cnt == 1) {
			W = new Vector<Vector<Integer>>();
			T = new Vector<Vector<Integer>>();
		}
		
		//resize matrix
		for(int i = 0; i < cnt - 1; i++) {
			W.get(i).add(MAX);
			T.get(i).add(-1);
		}
		
		//add new vertex
		W.add(new Vector<Integer>());
		T.add(new Vector<Integer>());
		for(int i = 0; i < cnt - 1; i++) {
			W.get(cnt - 1).add(MAX);
			T.get(cnt - 1).add(-1);
		}
		W.get(cnt - 1).add(0);
		T.get(cnt - 1).add(-1);
	}
	
	public void addPath(int cityId1, int cityId2, int d) {
		destroy();
		
		int i = 0, j = 0;
		//get index from ids
		for(int k = 0; k < cnt; k++) {
			if(ids.get(k) == cityId1)
				i = k;
			
			if(ids.get(k) == cityId2)
				j = k;
		}
		
		
		W.get(i).set(j, d);
		W.get(j).set(i, d);
		
		T.get(i).set(j, i);
		T.get(j).set(i, j);
	}
	
	public void printT() {
		System.out.println("T");
		
		for(int i = 0; i < cnt; i++) {
			for(int j = 0; j < cnt; j++)
				System.out.print(T.get(i).get(j)  + " ");
		
			System.out.println();
		}
	}
	
	private void printPath(int i, int j) {
		if(i == j) {
			System.out.print(i + " ");
			return;
		}
		else {
			if(T.get(i).get(j) == -1)
				System.out.println("x ");
			else {
				printPath(i, T.get(i).get(j));
				System.out.print(j + " ");
			}
		}
	}
	
	public void print() {
		if(! calculated)
			calculate();
		
		System.out.println("W");
		
		for(int i = 0; i < cnt; i++) {
			for(int j = 0; j < cnt; j++)
				if(W.get(i).get(j) == MAX)
					System.out.print("x ");
				else
					System.out.print(W.get(i).get(j) + " ");
		
			System.out.println();
		}
		
		System.out.println("D");
		
		for(int i = 0; i < cnt; i++) {
			for(int j = 0; j < cnt; j++)
				System.out.print(D[i][j] + " ");
		
			System.out.println();
		}
		
		System.out.println("T");
		
		for(int i = 0; i < cnt; i++) {
			for(int j = 0; j < cnt; j++)
				System.out.print(T.get(i).get(j)  + " ");
		
			System.out.println();
		}
		
		System.out.println("Shortest paths");
		
		for(int i = 0; i < cnt; i++) 
			for(int j = 0; j < cnt; j++) {
					System.out.print(i + " " + j + " -> ");
					printPath(i, j);
					System.out.println();
				}
	}
}
