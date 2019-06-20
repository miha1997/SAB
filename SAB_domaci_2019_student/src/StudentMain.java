import operations.*;
import student.om160076_ArticleOperations;
import student.om160076_BuyerOperations;
import student.om160076_GeneralOperations;
import student.om160076_OrderOperations;
import student.om160076_ShopOperations;
import student.om160076_TransactionOperations;
import student.om16076_CityOperations;
import student.helper.Graph;
import student.helper.Timer;

import java.math.BigDecimal;
import java.util.Calendar;
import tests.TestHandler;
import tests.TestRunner;


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
	
	private static void miniTest(
			ArticleOperations articleOperations,
	        BuyerOperations buyerOperations,
	        CityOperations cityOperations,
	        GeneralOperations generalOperations,
	        OrderOperations orderOperations,
	        ShopOperations shopOperations,
	        TransactionOperations transactionOperations){
		
		generalOperations.eraseAll();
		
        Calendar initialTime = Calendar.getInstance();
        initialTime.clear();
        initialTime.set(2018, Calendar.JANUARY, 1);
        generalOperations.setInitialTime(initialTime);
        Calendar receivedTime = Calendar.getInstance();
        receivedTime.clear();
        receivedTime.set(2018, Calendar.JANUARY, 22);
        
        //System.out.println(generalOperations.getCurrentTime().getTime());
        
        //make network
        int cityA = cityOperations.createCity("A");
        int cityB = cityOperations.createCity("B");
        
        cityOperations.connectCities(cityB, cityA, 4);
        int shopB = shopOperations.createShop("shopB", "B");
        
        int laptop = articleOperations.createArticle(shopB, "laptop", 1000);
        shopOperations.increaseArticleCount(laptop, 10);

        int buyer = buyerOperations.createBuyer("kupac", cityA);
        buyerOperations.increaseCredit(buyer, new BigDecimal("20000"));
        int order = buyerOperations.createOrder(buyer);
        orderOperations.addArticle(order, laptop, 5);
        orderOperations.completeOrder(order);
        
        for(int i = 0; i < 5; i++) {
        	System.out.println("grad " +  orderOperations.getLocation(order));
        	System.out.println("dan " + Timer.getTimer().getTime().get(Calendar.DAY_OF_MONTH));
        	System.out.println();
        	Timer.getTimer().passDay();
        }
        
	}
	
	private static void test(
			ArticleOperations articleOperations,
	        BuyerOperations buyerOperations,
	        CityOperations cityOperations,
	        GeneralOperations generalOperations,
	        OrderOperations orderOperations,
	        ShopOperations shopOperations,
	        TransactionOperations transactionOperations){
		
		generalOperations.eraseAll();
		
        Calendar initialTime = Calendar.getInstance();
        initialTime.clear();
        initialTime.set(2018, Calendar.JANUARY, 1);
        generalOperations.setInitialTime(initialTime);
        Calendar receivedTime = Calendar.getInstance();
        receivedTime.clear();
        receivedTime.set(2018, Calendar.JANUARY, 22);
        
        //System.out.println(generalOperations.getCurrentTime().getTime());
        
        //make network
        int cityB = cityOperations.createCity("B");
        int cityC1 = cityOperations.createCity("C1");
        int cityA = cityOperations.createCity("A");
        int cityC2 = cityOperations.createCity("C2");
        int cityC3 = cityOperations.createCity("C3");
        int cityC4 = cityOperations.createCity("C4");
        int cityC5 = cityOperations.createCity("C5");

        cityOperations.connectCities(cityB, cityC1, 8);
        cityOperations.connectCities(cityC1, cityA, 10);
        cityOperations.connectCities(cityA, cityC2, 3);
        cityOperations.connectCities(cityC2, cityC3, 2);
        cityOperations.connectCities(cityC3, cityC4, 1);
        cityOperations.connectCities(cityC4, cityA, 3);
        cityOperations.connectCities(cityA, cityC5, 15);
        cityOperations.connectCities(cityC5, cityB, 2);

        //make shops, buyer and articles
        int shopA = shopOperations.createShop("shopA", "A");
        int shopC2 = shopOperations.createShop("shopC2", "C2");
        int shopC3 = shopOperations.createShop("shopC3", "C3");

        shopOperations.setDiscount(shopA, 20);
        shopOperations.setDiscount(shopC2, 50);

        int laptop = articleOperations.createArticle(shopA, "laptop", 1000);
        int monitor = articleOperations.createArticle(shopC2, "monitor", 200);
        int stolica = articleOperations.createArticle(shopC3, "stolica", 100);
        int sto = articleOperations.createArticle(shopC3, "sto", 200);

        shopOperations.increaseArticleCount(laptop, 10);
        shopOperations.increaseArticleCount(monitor, 10);
        shopOperations.increaseArticleCount(stolica, 10);
        shopOperations.increaseArticleCount(sto, 10);

        int buyer = buyerOperations.createBuyer("kupac", cityB);
        buyerOperations.increaseCredit(buyer, new BigDecimal("20000"));
        int order = buyerOperations.createOrder(buyer);

        orderOperations.addArticle(order, laptop, 5);
        orderOperations.addArticle(order, monitor, 4);
        orderOperations.addArticle(order, stolica, 10);
        orderOperations.addArticle(order, sto, 4);
        
        int i = 1;
        
        if(orderOperations.getSentTime(order) == null) {
        	System.out.println("1 radi");
        }
        
        if("created".equals(orderOperations.getState(order))) {
        	System.out.println("2 radi");
        }
        
        orderOperations.completeOrder(order);
        
        if("sent".equals(orderOperations.getState(order))) {
        	System.out.println("3 radi");
        }

        int buyerTransactionId = transactionOperations.getTransationsForBuyer(buyer).get(0);
        
        if(initialTime.equals(transactionOperations.getTimeOfExecution(buyerTransactionId))) {
        	System.out.println("4 radi");
        }
        
        if(transactionOperations.getTransationsForShop(shopA) == null) {
        	System.out.println("5 radi");
        }

        //calculate ammounts - begin
        BigDecimal shopAAmount = new BigDecimal("5").multiply(new BigDecimal("1000")).setScale(3);
        BigDecimal shopAAmountWithDiscount = new BigDecimal("0.8").multiply(shopAAmount).setScale(3);
        BigDecimal shopC2Amount = new BigDecimal("4").multiply(new BigDecimal("200")).setScale(3);
        BigDecimal shopC2AmountWithDiscount = new BigDecimal("0.5").multiply(shopC2Amount).setScale(3);
        BigDecimal shopC3Amount = (new BigDecimal("10").multiply(new BigDecimal("100")))
                .add(new BigDecimal("4").multiply(new BigDecimal("200"))).setScale(3);
        BigDecimal shopC3AmountWithDiscount = shopC3Amount;

        BigDecimal amountWithoutDiscounts = shopAAmount.add(shopC2Amount).add(shopC3Amount).setScale(3);
        BigDecimal amountWithDiscounts = shopAAmountWithDiscount.add(shopC2AmountWithDiscount).add(shopC3AmountWithDiscount).setScale(3);

        BigDecimal systemProfit = amountWithDiscounts.multiply(new BigDecimal("0.05")).setScale(3);
        BigDecimal shopAAmountReal = shopAAmountWithDiscount.multiply(new BigDecimal("0.95")).setScale(3);
        BigDecimal shopC2AmountReal = shopC2AmountWithDiscount.multiply(new BigDecimal("0.95")).setScale(3);
        BigDecimal shopC3AmountReal = shopC3AmountWithDiscount.multiply(new BigDecimal("0.95")).setScale(3);
        //calculate ammounts - end
        
        if(amountWithDiscounts.equals(orderOperations.getFinalPrice(order))) {
        	System.out.println("6 radi");
        }
        
        if(amountWithoutDiscounts.subtract(amountWithDiscounts).equals(orderOperations.getDiscountSum(order))) {
        	System.out.println("7 radi");
        }
        
        if(amountWithDiscounts.equals(transactionOperations.getBuyerTransactionsAmmount(buyer))) {
        	System.out.println("8 radi");
        }
        
        if(transactionOperations.getShopTransactionsAmmount(shopA).equals(new BigDecimal("0").setScale(3))) {
        	System.out.println("9 radi");
        }
        
        if(transactionOperations.getShopTransactionsAmmount(shopC2).equals(new BigDecimal("0").setScale(3))) {
        	System.out.println("10 radi");
        }
        
        if(transactionOperations.getShopTransactionsAmmount(shopC3).equals(new BigDecimal("0").setScale(3))) {
        	System.out.println("11 radi");
        }
        
        if(new BigDecimal("0").setScale(3).equals(transactionOperations.getSystemProfit())) {
        	System.out.println("12 radi");
        }
        

        generalOperations.time(2);
        
        if(initialTime.equals(orderOperations.getSentTime(order))) {
        	System.out.println("13 radi");
        }
        
        if(orderOperations.getRecievedTime(order) == null) {
        	System.out.println("14 radi");
        }
        
        if(orderOperations.getLocation(order) == cityA) {
        	System.out.println("15 radi");
        }

        generalOperations.time(9);
        if(orderOperations.getLocation(order) == cityA) {
        	System.out.println("16 radi");
        }

        generalOperations.time(8);
        if(orderOperations.getLocation(order) == cityC5) {
        	System.out.println("17 radi");
        }

        generalOperations.time(5);
        if(orderOperations.getLocation(order) == cityB) {
        	System.out.println("18 radi");
        }
        
        if(receivedTime.equals(orderOperations.getRecievedTime(order))) {
        	System.out.println("19 radi");
        }
        
        if(shopAAmountReal.equals(transactionOperations.getShopTransactionsAmmount(shopA))) {
        	System.out.println("20 radi");
        }
        
        if(shopC2AmountReal.equals(transactionOperations.getShopTransactionsAmmount(shopC2))) {
        	System.out.println("21 radi");
        }
        
        if(shopC3AmountReal.equals(transactionOperations.getShopTransactionsAmmount(shopC3))) {
        	System.out.println("22 radi");
        }
        
        if(systemProfit.equals(transactionOperations.getSystemProfit())) {
        	System.out.println("23 radi");
        }

        int shopATransactionId = transactionOperations.getTransactionForShopAndOrder(order, shopA);
        
        if(shopATransactionId != -1) {
        	System.out.println("24 radi");
        }
        
        if(receivedTime.equals(transactionOperations.getTimeOfExecution(shopATransactionId))) {
        	System.out.println("25 radi");
        }
        
        System.out.println(i);

    }
	
	
	
    public static void main(String[] args) {

        ArticleOperations articleOperations = new om160076_ArticleOperations(); 
        BuyerOperations buyerOperations = new om160076_BuyerOperations();
        CityOperations cityOperations = new om16076_CityOperations();
        GeneralOperations generalOperations = new om160076_GeneralOperations();
        OrderOperations orderOperations = new om160076_OrderOperations();
        ShopOperations shopOperations = new om160076_ShopOperations();
        TransactionOperations transactionOperations = new om160076_TransactionOperations();
        
        //miniTest(articleOperations, buyerOperations, cityOperations, generalOperations, orderOperations, shopOperations, transactionOperations);
        
        //test(articleOperations, buyerOperations, cityOperations, generalOperations, orderOperations, shopOperations, transactionOperations);
        //generalOperations.time(21);
        

        /*for(int i = 0; i < 25; i ++) {
        	generalOperations.time(1);
        	
        	System.out.println(orderOperations.getLocation(1));
        	//System.out.println(generalOperations.getCurrentTime().getTime());
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
