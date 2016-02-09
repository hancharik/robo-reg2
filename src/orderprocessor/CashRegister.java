/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderprocessor;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Mark
 */
public class CashRegister extends Thread {

    Sale sale;
    Return storeReturn;
    Customer currentCustomer;
    ArrayList<Item> inventory;
    ArrayList<Item> cart;
    BusinessAccount till;
    int transactions;// = 0;
    int itemsSold = 0;
    int lostSales = 0;
    Random random;
    int identifier;
    Item i = new Item();

    public CashRegister(int identifier) {

        this.transactions = orderprocessor.OrderProcessor.transactions;
        this.identifier = identifier;
        till = orderprocessor.OrderProcessor.mainAccount;
        inventory = orderprocessor.OrderProcessor.inventory;
        cart = new ArrayList();
        //setInventory(300); // <-- moved this to OrderProcessor.java

      //  roboCashier(10);
      //  checkTillForErrors();
    }

    public void run() {

        roboCashier(25);
        checkTillForErrors();
    }

    public void getCustomer() {

        Customer c = new Customer();
        currentCustomer = c;

    }

    public void roboCashier(int numberOfTransactions) {

        getCustomer();

        transaction(numberOfTransactions);

    }

    //}
    public synchronized void transaction(int numberOfTransactions) {
        random = new Random();
        int randomItemCount;// = 1 + random.nextInt( 1000 );
        for (int i = 0; i < numberOfTransactions; i++) {
            //   if(inventory.size()<1){
            //      System.out.println("out of inventory... the store is closed"); break;
            // System.out.println("out of inventory... lost sale");
            //   }else{
            randomItemCount = 1 + random.nextInt(10);
            int returnChance = 1 + random.nextInt(100);

            // http://abcnews.go.com/Business/online-shopping-transactions-returned/story?id=21312312
            if (returnChance > 33) {

                if (randomItemCount > orderprocessor.OrderProcessor.inventory.size()) {
                    randomItemCount = offerLesserAmount(randomItemCount, orderprocessor.OrderProcessor.inventory.size());
                }
                addItemToCheckout(randomItemCount);
                processTransaction();
                //showValues("" + cart.size());
            } else {
                returnItems(randomItemCount);
            }
        } // end for loop -->  for(int i = 0; i < numberOfTransactions; i++){

    }

    public void addItemToCheckout(int howManyItems) {

        System.out.print("Cashier #" + identifier);// System.out.print("CashRegister.java line 105 - Cashier #" + identifier);
        System.out.print(", Transaction #" + (orderprocessor.OrderProcessor.transactions));
        System.out.println(" items ordered: " + howManyItems + " on hand inventory: " + orderprocessor.OrderProcessor.inventory.size());

        if (howManyItems <= orderprocessor.OrderProcessor.inventory.size()) {
         //   System.out.print("111 - Cashier #" + identifier);
            //   System.out.println(", transaction #" + (orderprocessor.OrderProcessor.transactions + 1) + " allowed to complete"); 

            for (int i = 0; i < howManyItems; i++) {

                cart.add(orderprocessor.OrderProcessor.inventory.get(orderprocessor.OrderProcessor.inventory.size() - 1));
                orderprocessor.OrderProcessor.inventory.remove(orderprocessor.OrderProcessor.inventory.size() - 1);  //remove the last element from the array

            }
        } else {
       //     System.out.print("Cashier #" + identifier);
            //  System.out.println(", transaction NOT allowed to complete - we don't have that many widgets left in inventory.");
            lostSales = lostSales + howManyItems;
        }
    }

    public void removeItemFromCheckout(Item item) {

        cart.remove(item);
        orderprocessor.OrderProcessor.inventory.add(item);
    }

    public void returnItems(int numItemsReturned) {

        for (int i = 0; i < numItemsReturned; i++) {
            Item a = new Item();
            // put item back in inventory
            orderprocessor.OrderProcessor.inventory.add(a);
            // refund money
            till.bankAccount = till.bankAccount - a.getItemPrice();

        }

        System.out.println("Cashier #" + identifier + ", Transaction #" + (orderprocessor.OrderProcessor.transactions) + " returning " + numItemsReturned + " items to inventory");
        orderprocessor.OrderProcessor.transactions++;
    }

    public synchronized void processTransaction() {

        int counter = cart.size() - 1;

        for (int i = counter; i >= 0; i--) {
            //showValues("pre cart size: " + cart.size());
            currentCustomer.accountBalance = currentCustomer.accountBalance - cart.get(i).getItemPrice();
            till.addMoney(cart.get(i).getItemPrice());
            cart.remove(i);
            orderprocessor.OrderProcessor.totalItemsSold++;

            // System.out.println("items in cart: " + cart.size());
            //showValues("post cart size: " + cart.size());
        }
        orderprocessor.OrderProcessor.transactions++;
    }

    public void showValues(String identifier) {
        System.out.println("171 - Cashier #" + identifier);
        System.out.println("172 - (identifier #" + identifier + ",) after " + orderprocessor.OrderProcessor.transactions + " transactions, we sold " + itemsSold + " items. Customer balance: " + currentCustomer.accountBalance + ", till: " + till.bankAccount + "\n");

    }

    public void setInventory(int unitsInInventory) {

        for (int i = 0; i < unitsInInventory; i++) {
            Item item = new Item();
            orderprocessor.OrderProcessor.inventory.add(item);
        }

    } // END SET INVENTORY

    public void checkTillForErrors() {
        System.out.println("\nRobo cashier # " + identifier + " counting the drawer at the end of it's shift...");
        if ((orderprocessor.OrderProcessor.totalItemsSold * i.getItemPrice()) == till.bankAccount) {
            System.out.println("the money is correct");
        } else {
            System.out.println("the money is NOT correct - something is wrong");
        }
        System.out.print("Cashier #" + identifier);
        System.out.print(", due to insufficient inventory, we lost $");
        //System.out.format("%.2f%", lostSales*i.getItemPrice());     // -->  "3.142"
        System.out.format("%.2f%n", lostSales * i.getItemPrice());     // -->  "3.142" the N is a new line character
        // System.out.print(" due to insufficient inventory");
    }

    public int offerLesserAmount(int amountOrdered, int inventoryOnHand) {

        int lesserAmount;

        Random random = new Random();
        int r = 1 + random.nextInt(10);
        if (r % 3 == 0 && inventoryOnHand > 0) { //33% chance the customer won't complete the order if there isn't enough
            lesserAmount = inventoryOnHand;
            System.out.println("Customer needs " + amountOrdered + " units, but is willing to take the last " + lesserAmount + " units that we have on hand");
            int opportunityCost = amountOrdered - inventoryOnHand;
            lostSales = lostSales + opportunityCost;
        } else {

            lesserAmount = amountOrdered;
            System.out.println("LOST SALE - (Customer needs the " + amountOrdered + " units)");
            lostSales = lostSales + amountOrdered;
        }

        // otherwise, sell them everything that we have!   
        return lesserAmount;
    }

} // end
