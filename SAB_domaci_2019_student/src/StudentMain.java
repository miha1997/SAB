import operations.*;
import student.om160076_ArticleOperations;
import student.om160076_BuyerOperations;
import student.om160076_GeneralOperations;
import student.om160076_ShopOperations;
import student.om16076_CityOperations;
import student.jdbc.DB;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import tests.TestHandler;
import tests.TestRunner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentMain {

    public static void main(String[] args) {

        ArticleOperations articleOperations = new om160076_ArticleOperations(); // Change this for your implementation (points will be negative if interfaces are not implemented).
        BuyerOperations buyerOperations = new om160076_BuyerOperations();
        CityOperations cityOperations = new om16076_CityOperations();
        GeneralOperations generalOperations = new om160076_GeneralOperations();
        OrderOperations orderOperations = null;
        ShopOperations shopOperations = new om160076_ShopOperations();
        TransactionOperations transactionOperations = null;
             
        /*int i = buyerOperations.createOrder(3);
        //System.out.println(i);
        
        List<Integer> list = buyerOperations.getOrders(1);
        
        for(Integer li : list)
        	System.out.println(li);
        
        BigDecimal res = buyerOperations.getCredit(1);
        System.out.println(res.floatValue());*/
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
