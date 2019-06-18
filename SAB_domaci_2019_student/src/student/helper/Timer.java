package student.helper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import java.util.logging.Level;
import java.util.logging.Logger;

import student.jdbc.DB;

public class Timer {
	
	private Calendar current;
	private static Timer timer = null;
	
	private Timer() {
		
	}
	
	public static Timer getTimer() {
		if(timer == null)
			timer = new Timer();
		
		return timer;
	}
	
	public void setTime(Calendar init) {
		current = (Calendar) init.clone();
	}
	
	public void updateCurCity(Calendar cur) {
		Connection connection=DB.getInstance().getConnection();
		String getOrders="select n.IdNarudzbina, n.TrenutniGrad, n.Progres, k.IdGrad, n.DatumSastavljanja from Narudzbina as n, Kupac as k where  k.IdKupac = n.IdKupac and n.Status = 'sent'";
		String updateQuery="update Narudzbina set Status = ?, TrenutniGrad = ?, Progres = ? where IdNarudzbina = ?";
		String updateTime ="update Narudzbina set TrenutniDatum = ?";
		
        try ( Statement statement=connection.createStatement();
        	PreparedStatement psUpdate=connection.prepareStatement(updateQuery);
        	PreparedStatement psTime=connection.prepareStatement(updateTime);
        	PreparedStatement psSelect=connection.prepareStatement(getOrders);){
        	
        	java.util.Date now = cur.getTime();
        	Date nowSql = new Date(now.getTime());
        	psTime.setDate(1, nowSql);
        	
            ResultSet rs = psSelect.executeQuery();
            
            while(rs.next()) {
            	int orderId = rs.getInt(1);	
            	int curCity = rs.getInt(2);	
            	int progres = rs.getInt(3);	
            	int buyerCity = rs.getInt(4);
            	Date date = rs.getDate(5);
            	java.util.Date curDate = cur.getTime();
            	Date curSqlDate = new Date(curDate.getTime());
            	
            	if(! curSqlDate.after(date))
            		continue;
            	
            	Graph graph = Graph.getGraph();
            	int res [] = graph.checkProgress(curCity, progres, buyerCity);
            	
            	progres = res[0];
            	curCity = res[1];
            	
            	String status = "";
            	if(curCity != buyerCity)
            		status ="sent";
            	else
            		status = "arrived";
            	
            	psUpdate.setString(1, status);
            	psUpdate.setInt(2, curCity);
            	psUpdate.setInt(3, progres);
            	psUpdate.setInt(4, orderId);
            	
            	psUpdate.executeUpdate();
            }
            

        } catch (SQLException ex) {
            Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
	}
	
	//update states of orders
	public void passDay() {
		current.add(Calendar.DATE, 1); 
		
		Connection connection=DB.getInstance().getConnection();
        String setDate="update TrenutniDatum set Datum = ?";
        
        try(PreparedStatement pUpdate = connection.prepareStatement(setDate);){
        	
        	pUpdate.setDate(1, new java.sql.Date(timer.getTime().getTimeInMillis()));
        	pUpdate.executeUpdate();
        	
        	
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		updateCurCity(current);
		
	}
	
	public Calendar getTime() {
		return current;
	}
}
