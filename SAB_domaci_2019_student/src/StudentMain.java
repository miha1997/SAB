import operations.*;
import student.om160076_ArticleOperations;
import student.om160076_BuyerOperations;
import student.om160076_GeneralOperations;
import student.om160076_OrderOperations;
import student.om160076_ShopOperations;
import student.om160076_TransactionOperations;
import student.om16076_CityOperations;
import student.helper.Graph;
import student.jdbc.DB;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import tests.TestHandler;
import tests.TestRunner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentMain {

	private static void tryGraph() {
		Graph graph = Graph.getGraph();
		
		
		graph.addCity(0);
		graph.addCity(1);
		graph.addCity(2);
		graph.addCity(3);
		
		graph.addPath(0, 2, -2);
		
		graph.addPath(1, 0, 4);
		graph.addPath(1, 2, 3);
		
		graph.addPath(2, 3, 2);
		
		graph.addPath(3, 1, -1);
		
		graph.print();
	}
	
    public static void main(String[] args) {

        ArticleOperations articleOperations = new om160076_ArticleOperations(); // Change this for your implementation (points will be negative if interfaces are not implemented).
        BuyerOperations buyerOperations = new om160076_BuyerOperations();
        CityOperations cityOperations = new om16076_CityOperations();
        GeneralOperations generalOperations = new om160076_GeneralOperations();
        OrderOperations orderOperations = new om160076_OrderOperations();
        ShopOperations shopOperations = new om160076_ShopOperations();
        TransactionOperations transactionOperations = new om160076_TransactionOperations();
        
        /*generalOperations.eraseAll();
        
        int cityId = cityOperations.createCity("Kragujevac");
        int buyerId = buyerOperations.createBuyer("Pera", cityId);

        BigDecimal credit1 = new BigDecimal("1000.000").setScale(3);

        BigDecimal creditReturned = buyerOperations.increaseCredit(buyerId, credit1);
        
        System.out.println(credit1.doubleValue() + " " + creditReturned.doubleValue());
        
        if(credit1.equals(creditReturned)) {
        	System.out.println("x");
        }

        BigDecimal credit2 = new BigDecimal("500");
        buyerOperations.increaseCredit(buyerId, credit2).setScale(3);

        creditReturned = buyerOperations.getCredit(buyerId);
        if(credit1.add(credit2).setScale(3).equals(creditReturned)) {
        	System.out.println("x");
        }*/
        

        
        //tryGraph();
               
//
//        Calendar c = Calendar.getInstance();
//        c.clear();
//        c.set(2010, Calendar.JANUARY, 01);
//
//
//        Calendar c2 = Calendar.getInstance();
//        c2.clear();
//        c2.set(2010, Calendar.JANUARY, 01);
//
//        if(c.equals(c2)) System.out.println("jednako");
//        else System.out.println("nije jednako");

        TestHandler.createInstance(
                articleOperations,
                buyerOperations,
                cityOperations,
                generalOperations,
                orderOperations,
                shopOperations,
                transactionOperations
        );

        TestRunner.runTests();
    }
}
