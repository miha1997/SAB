import operations.*;
import student.om160076_ArticleOperations;
import student.om160076_BuyerOperations;
import student.om160076_ShopOperations;
import student.om16076_CityOperations;
import student.jdbc.DB;
import student.jdbc.JDBC;

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
        GeneralOperations generalOperations = null;
        OrderOperations orderOperations = null;
        ShopOperations shopOperations = new om160076_ShopOperations();
        TransactionOperations transactionOperations = null;
             
        BigDecimal res = buyerOperations.increaseCredit(3, BigDecimal.valueOf(35.7));
        System.out.println(res.toString());
        
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

        /*TestHandler.createInstance(
                articleOperations,
                buyerOperations,
                cityOperations,
                generalOperations,
                orderOperations,
                shopOperations,
                transactionOperations
        );

        TestRunner.runTests();*/
    }
}
