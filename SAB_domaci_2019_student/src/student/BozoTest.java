package student;
 
import java.math.BigDecimal;
import java.util.Calendar;
 
import operations.ArticleOperations;
import operations.BuyerOperations;
import operations.CityOperations;
import operations.GeneralOperations;
import operations.OrderOperations;
import operations.ShopOperations;
import operations.TransactionOperations;
 
public class BozoTest {
 
    public static void main(String[] args) {
        int score = 0;
        int failed = 0;
 
        GeneralOperations generalOperations = new om160076_GeneralOperations();
        CityOperations cityOperations = new om16076_CityOperations();
        ShopOperations shopOperations = new om160076_ShopOperations();
        ArticleOperations articleOperations = new om160076_ArticleOperations();
        BuyerOperations buyerOperations = new om160076_BuyerOperations();
        OrderOperations orderOperations = new om160076_OrderOperations();
        TransactionOperations transactionOperations = new om160076_TransactionOperations();
 
        generalOperations.eraseAll();
 
        Calendar initialTime = Calendar.getInstance();
        initialTime.clear();
        initialTime.set(2018, Calendar.JANUARY, 1);
 
        generalOperations.setInitialTime(initialTime);
        
        int br = 0;
        //0
        int vrsac = cityOperations.createCity("Vrsac");
        if (vrsac != -1) {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
        
        //1
        if (cityOperations.createCity("Vrsac") == -1)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
        int zrenjanin = cityOperations.createCity("Zrenjanin");
        int novisad = cityOperations.createCity("NoviSad");
        int beograd = cityOperations.createCity("Beograd");
        int sombor = cityOperations.createCity("Sombor");
        int kula = cityOperations.createCity("Kula");
        int subotica = cityOperations.createCity("Subotica");
        int petrovaradin = cityOperations.createCity("Petrovaradin");
        int ruma = cityOperations.createCity("Ruma");
        int pozarevac = cityOperations.createCity("Pozarevac");
        int kragujevac = cityOperations.createCity("Kragujevac");
        int cacak = cityOperations.createCity("Cacak");
        int zajecar = cityOperations.createCity("Zajecar");
        
        //2
        if (cityOperations.getCities().size() == 13)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
 
        cityOperations.connectCities(vrsac, sombor, 18);
        cityOperations.connectCities(zrenjanin, vrsac, 3);
        cityOperations.connectCities(vrsac, beograd, 4);
        cityOperations.connectCities(vrsac, novisad, 1);
        cityOperations.connectCities(novisad, kula, 18);
        cityOperations.connectCities(kula, petrovaradin, 3);
        cityOperations.connectCities(novisad, petrovaradin, 14);
        cityOperations.connectCities(petrovaradin, ruma, 2);
        cityOperations.connectCities(novisad, ruma, 8);
        cityOperations.connectCities(petrovaradin, kragujevac, 1);
        cityOperations.connectCities(kragujevac, cacak, 5);
        cityOperations.connectCities(ruma, kragujevac, 2);
        cityOperations.connectCities(beograd, novisad, 6);
        cityOperations.connectCities(ruma, pozarevac, 4);
        cityOperations.connectCities(pozarevac, kragujevac, 6);
        cityOperations.connectCities(zajecar, pozarevac, 3);
        cityOperations.connectCities(pozarevac, subotica, 11);
        cityOperations.connectCities(beograd, subotica, 3);
        cityOperations.connectCities(zrenjanin, subotica, 10);
        cityOperations.connectCities(subotica, sombor, 7);
        
        //3
        if (cityOperations.connectCities(kragujevac, ruma, 3) == -1)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
        //4
        //mada ja mislim da trebaju svi povezani, tj 12
        int cnt = cityOperations.getConnectedCities(novisad).size();
        if (cityOperations.getConnectedCities(novisad).size() == 5)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
        
        //5
        if (cityOperations.getConnectedCities(ruma).contains(petrovaradin))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
 
        int dada = shopOperations.createShop("Dada", "Vrsac");
        int pekara = shopOperations.createShop("Pekara", "Zrenjanin");
        int gigatron = shopOperations.createShop("Gigatron", "Beograd");
        int maxi = shopOperations.createShop("Maxi", "Ruma");
        int idea = shopOperations.createShop("Idea", "Kula");
        
        //6
        if (shopOperations.createShop("Dada", "Beograd") == -1)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
        
        //7
        if (shopOperations.createShop("Prsutara", "Bor") == -1)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
        
        //8
        if (cityOperations.getShops(cacak) == null)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
        
        //9
        if (cityOperations.getShops(kula).size() == 1 && cityOperations.getShops(kula).contains(idea))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
        
        //10
        if(shopOperations.getCity(dada)==vrsac)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        int kifla = articleOperations.createArticle(pekara, "Kifla", 20);
        int sendvic = articleOperations.createArticle(pekara, "Sendvic", 150);
        int hleb = articleOperations.createArticle(dada, "Hleb", 100);
        int pasteta = articleOperations.createArticle(dada, "Pasteta", 100);
        int kupus = articleOperations.createArticle(idea, "Kupus", 50);
        int krompir = articleOperations.createArticle(idea, "Krompir", 20);
        int laptop = articleOperations.createArticle(gigatron, "Laptop", 5000);
        int klima = articleOperations.createArticle(gigatron, "Klima", 2000);
        int parlament = articleOperations.createArticle(maxi, "Parlament", 400);
        int cokolada = articleOperations.createArticle(maxi, "Cokolada", 50);
        
        
        //11
        if (kifla == -1 || sendvic == -1 || hleb == -1 || pasteta == -1 || kupus == -1 || krompir == -1 || laptop == -1
                || klima == -1 || parlament == -1 || cokolada == -1)
            {failed++;br++;}
        else
            {System.out.println("radi " + br);br++;score++;}
        
        //12
        if (shopOperations.setCity(idea, "Bor") == -1)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
 
        shopOperations.setDiscount(dada, 50);
        shopOperations.setDiscount(maxi, 10);
        shopOperations.setDiscount(idea, 20);
        
        //13
        if (shopOperations.getDiscount(gigatron) == 0 && shopOperations.getDiscount(maxi) == 10)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
        
        //14
        if (shopOperations.getArticles(dada).size() == 2 && shopOperations.getArticles(dada).contains(pasteta))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
 
        shopOperations.increaseArticleCount(kifla, 10);
        shopOperations.increaseArticleCount(sendvic, 2);
        shopOperations.increaseArticleCount(hleb, 50);
        shopOperations.increaseArticleCount(pasteta, 20);
        shopOperations.increaseArticleCount(laptop, 3);
        shopOperations.increaseArticleCount(klima, 10);
        shopOperations.increaseArticleCount(kupus, 15);
        shopOperations.increaseArticleCount(krompir, 20);
        shopOperations.increaseArticleCount(parlament, 100);
        shopOperations.increaseArticleCount(cokolada, 200);
        
        //15
        if (shopOperations.getArticleCount(kupus) == 15 && shopOperations.getArticleCount(cokolada) == 200)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        int stevica=buyerOperations.createBuyer("Stevica", beograd);
        int perica = buyerOperations.createBuyer("Perica", petrovaradin);
        int verica =buyerOperations.createBuyer("Verica", zajecar);
       
        //16
        if(buyerOperations.getCredit(stevica).equals(new BigDecimal(0).setScale(3)))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        BigDecimal verica_credit = buyerOperations.increaseCredit(verica, new BigDecimal(2000).setScale(3));
        BigDecimal stevica_credit = buyerOperations.increaseCredit(stevica, new BigDecimal(20000).setScale(3));
        BigDecimal perica_credit = buyerOperations.increaseCredit(perica, new BigDecimal(5000).setScale(3));
        
        //17
        if(verica_credit.equals(buyerOperations.getCredit(verica)))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
        //18
        if(buyerOperations.getOrders(stevica)==null)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
        //19
        if(buyerOperations.getCity(perica)==petrovaradin)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        int stevica_ord1=buyerOperations.createOrder(stevica);
       
        //20
        if(buyerOperations.getOrders(stevica).size()==1 && buyerOperations.getOrders(stevica).contains(stevica_ord1))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        //21
        if(orderOperations.completeOrder(stevica_ord1)==-1)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
        //22
        if(orderOperations.getFinalPrice(stevica_ord1).equals(new BigDecimal(-1).setScale(3)))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
        //23
        if(orderOperations.addArticle(stevica_ord1, klima, 12)==-1)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        //24
        if(orderOperations.addArticle(stevica_ord1, klima, 6)!=-1)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
           
        orderOperations.addArticle(stevica_ord1, kifla, 5);
        //25
        if(orderOperations.removeArticle(stevica_ord1, kifla)==1)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
        //26
        if(orderOperations.completeOrder(stevica_ord1)==1)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
        
        //27
        BigDecimal big = transactionOperations.getAmmountThatBuyerPayedForOrder(stevica_ord1);
        if(transactionOperations.getAmmountThatBuyerPayedForOrder(stevica_ord1).equals(new BigDecimal(12000).setScale(3)))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
        
        //28
        if(transactionOperations.getTimeOfExecution(transactionOperations.getTransactionForBuyersOrder(stevica_ord1)).equals(initialTime))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        //29
        if(initialTime.equals(transactionOperations.getTimeOfExecution(transactionOperations.getTransactionForShopAndOrder(stevica_ord1, gigatron))))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
        stevica_credit = stevica_credit.subtract(new BigDecimal(12000).setScale(3));
       
        //30
        if(stevica_credit.equals(buyerOperations.getCredit(stevica)))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        BigDecimal current_sys_profit = new BigDecimal(600).setScale(3);
        //31
        if(current_sys_profit.equals(transactionOperations.getSystemProfit()))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
           
        initialTime=generalOperations.time(2);
        //32
        if(shopOperations.getArticleCount(klima)==4)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        
        //33
        if(orderOperations.getDiscountSum(stevica_ord1).equals(new BigDecimal(0).setScale(3)))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        int perica_ord1 = buyerOperations.createOrder(perica);
       
        orderOperations.addArticle(perica_ord1, kupus, 5);
        orderOperations.addArticle(perica_ord1, kupus,5);
        orderOperations.addArticle(perica_ord1, sendvic, 1);
        orderOperations.addArticle(perica_ord1, parlament, 5);
        orderOperations.addArticle(perica_ord1, hleb, 3);
       
        BigDecimal perica_zrenjanin = new BigDecimal(150).setScale(3);
        BigDecimal perica_kula_bp = new BigDecimal(500).setScale(3);
        BigDecimal perica_kula_sp = perica_kula_bp.subtract(new BigDecimal(100).setScale(3));
        BigDecimal perica_ruma_bp = new BigDecimal(2000).setScale(3);
        BigDecimal perica_ruma_sp = perica_ruma_bp.subtract(new BigDecimal(200).setScale(3));
        BigDecimal perica_vrsac_bp = new BigDecimal(300).setScale(3);
        BigDecimal perica_vrsac_sp = perica_vrsac_bp.subtract(new BigDecimal(150).setScale(3));
       
        BigDecimal perica_ukupno_bp = perica_kula_bp.add(perica_zrenjanin).add(perica_ruma_bp).add(perica_vrsac_bp);
        BigDecimal perica_ukupno_sp = perica_zrenjanin.add(perica_kula_sp).add(perica_ruma_sp).add(perica_vrsac_sp);
       
        BigDecimal perica_popust  = perica_ukupno_bp.subtract(perica_ukupno_sp);
       
       
        orderOperations.completeOrder(perica_ord1);
       
        //34
        if(orderOperations.getDiscountSum(perica_ord1).equals(perica_popust))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        //35
        if(orderOperations.getLocation(perica_ord1)==ruma)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        //36
        if(transactionOperations.getSystemProfit().equals(current_sys_profit))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        //37
        if(transactionOperations.getAmmountThatBuyerPayedForOrder(perica_ord1).equals(transactionOperations.getTransactionAmount(transactionOperations.getTransactionForBuyersOrder(perica_ord1))))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        //38
        if(transactionOperations.getTimeOfExecution(transactionOperations.getTransactionForBuyersOrder(perica_ord1)).equals(initialTime))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
       
        initialTime=generalOperations.time(10);
       
        //39
        if(orderOperations.getLocation(perica_ord1)==ruma)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        int verica_ord1 = buyerOperations.createOrder(verica);
       
        orderOperations.addArticle(verica_ord1, pasteta, 10);
        orderOperations.addArticle(verica_ord1, krompir, 10);
        orderOperations.addArticle(verica_ord1, pasteta, 10);
        orderOperations.addArticle(verica_ord1, krompir, 10);
        orderOperations.addArticle(verica_ord1, kifla, 2);
        orderOperations.addArticle(verica_ord1, kifla, 3);
        orderOperations.addArticle(verica_ord1, kupus, 3);
       
       
        BigDecimal verica_zrenjanin = new BigDecimal(100).setScale(3);
        BigDecimal verica_kula_bp = new BigDecimal(550).setScale(3);
        BigDecimal verica_kula_sp = verica_kula_bp.subtract(new BigDecimal(110).setScale(3));
        BigDecimal verica_vrsac_bp = new BigDecimal(2000).setScale(3);
        BigDecimal verica_vrsac_sp = verica_vrsac_bp.subtract(new BigDecimal(1000).setScale(3));
       
       
        BigDecimal verica_ukupno_bp = verica_kula_bp.add(verica_zrenjanin).add(verica_vrsac_bp);
        BigDecimal verica_ukupno_sp = verica_zrenjanin.add(verica_kula_sp).add(verica_vrsac_sp);
       
        BigDecimal verica_popust  = verica_ukupno_bp.subtract(verica_ukupno_sp);
       
       
        orderOperations.completeOrder(verica_ord1);
       
        //40
        if(orderOperations.getDiscountSum(verica_ord1).equals(verica_popust))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        //41
        if(orderOperations.getLocation(verica_ord1)==kula)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        //42
        if(transactionOperations.getSystemProfit().equals(current_sys_profit))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        //43
        if(transactionOperations.getAmmountThatBuyerPayedForOrder(verica_ord1).equals(transactionOperations.getTransactionAmount(transactionOperations.getTransactionForBuyersOrder(verica_ord1))))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        //44
        if(transactionOperations.getTimeOfExecution(transactionOperations.getTransactionForBuyersOrder(verica_ord1)).equals(initialTime))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
       
        initialTime=generalOperations.time(10);
       
        //45
        if(transactionOperations.getAmmountThatShopRecievedForOrder(pekara, perica_ord1).equals(perica_zrenjanin.multiply(new BigDecimal("0.950").setScale(3)).setScale(3)))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        //46
        if(transactionOperations.getAmmountThatShopRecievedForOrder(idea, perica_ord1).equals(perica_kula_sp.multiply(new BigDecimal("0.950").setScale(3)).setScale(3)))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        //47
        if(transactionOperations.getAmmountThatShopRecievedForOrder(maxi, perica_ord1).equals(perica_ruma_sp.multiply(new BigDecimal("0.950").setScale(3)).setScale(3)))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        //48
        if(transactionOperations.getAmmountThatShopRecievedForOrder(dada, perica_ord1).equals(perica_vrsac_sp.multiply(new BigDecimal("0.950").setScale(3)).setScale(3)))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        perica_credit = perica_credit.subtract(perica_ukupno_sp);
       
        //49
        if(perica_credit.equals(buyerOperations.getCredit(perica)))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
       
        current_sys_profit=current_sys_profit.add(perica_zrenjanin.multiply(new BigDecimal("0.050").setScale(3)).setScale(3)).add(perica_kula_sp.multiply(new BigDecimal("0.050").setScale(3)).setScale(3)).add(perica_ruma_sp.multiply(new BigDecimal("0.050").setScale(3)).setScale(3)).add(perica_vrsac_sp.multiply(new BigDecimal("0.050").setScale(3)).setScale(3));
       
        //50
        if(transactionOperations.getSystemProfit().equals(current_sys_profit))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        int stevica_ord2 = buyerOperations.createOrder(stevica);
       
        orderOperations.addArticle(stevica_ord2, parlament, 10);
       
       
        orderOperations.completeOrder(stevica_ord2);
       
        BigDecimal stevica_ruma_bp = new BigDecimal(4000).setScale(3);
        BigDecimal stevica_ruma_sp = new BigDecimal(3600).setScale(3);
        BigDecimal stevica_ruma_ss = stevica_ruma_sp.multiply(new BigDecimal("0.980")).setScale(3);
       
        //51
        if(stevica_ruma_ss.equals(transactionOperations.getAmmountThatBuyerPayedForOrder(stevica_ord2)))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        //52
        if(stevica_ruma_bp.subtract(stevica_ruma_ss).equals(orderOperations.getDiscountSum(stevica_ord2)))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        initialTime=generalOperations.time(10);
       
        //53
        if(orderOperations.getLocation(verica_ord1)==petrovaradin)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        //54
        if(orderOperations.getLocation(stevica_ord2)==vrsac)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        initialTime=generalOperations.time(4);
       
        //55
        if(orderOperations.getLocation(verica_ord1)==ruma)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
   
       
        initialTime=generalOperations.time(5);
       
 
       
        //56
        if(orderOperations.getLocation(verica_ord1)==zajecar)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        //57
        if(orderOperations.getLocation(stevica_ord2)==beograd)
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
       
       
        //58
        if(transactionOperations.getAmmountThatShopRecievedForOrder(maxi, stevica_ord2).equals(stevica_ruma_sp.multiply(new BigDecimal("0.950").setScale(3)).setScale(3)))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
        current_sys_profit=current_sys_profit.add(verica_ukupno_sp.multiply(new BigDecimal("0.050").setScale(3)).setScale(3));
        current_sys_profit=current_sys_profit.add(stevica_ruma_sp.multiply(new BigDecimal("0.030").setScale(3)).setScale(3));
       
        //59
        if(current_sys_profit.equals(transactionOperations.getSystemProfit()))
            {System.out.println("radi " + br);br++;score++;}
        else
            {failed++;br++;}
       
       
        System.out.println("Passed: "+score);
        System.out.println("Failed: "+failed);
       
       
    }
}