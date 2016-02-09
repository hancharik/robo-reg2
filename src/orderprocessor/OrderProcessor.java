/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderprocessor;

import java.util.ArrayList;

/**
 *https://docs.oracle.com/javase/tutorial/essential/concurrency/runthread.html
 * @author Mark
 */
public class OrderProcessor implements Runnable {

    public static CashRegister register1;
    public static CashRegister register2;
    public static CashRegister register3;
    public static CashRegister register4;
    public static ArrayList<Item> inventory;
     public static ArrayList<String> results;
   public static BusinessAccount mainAccount;
    public static int transactions;// how many transactions
   public static int totalItemsSold;
   public static int registerTransactions;  // the counter
   
    public static void main(String[] args) throws InterruptedException {
        
         (new Thread(new OrderProcessor())).start();
        
        
        
        setInventory(100);
        transactions = 10;
        
        
        registerTransactions = 0;  // set counter to zero
         
        totalItemsSold = 0;
        mainAccount = new BusinessAccount();
       
        
        
        System.out.println("Starting inventory = " + inventory.size() + ", till = " + mainAccount.bankAccount);
        
        //inventory = new ArrayList();
        
        register1 = new CashRegister(1);
        register2 = new CashRegister(2);
        register3 = new CashRegister(3);
        register4 = new CashRegister(4);
        register1.start();
        register2.start();
       register3.start();
        register4.start();
        register4.join();
        
        System.out.println("Ending inventory = " + inventory.size() + ", till = " + mainAccount.bankAccount);
        
      Thread.sleep(3000);
       System.out.println("after 3 seconds");
       printResults();
       Thread.sleep(3000);
       System.out.println("after 6 seconds");
       printResults();
    } // end main

     private static void setInventory(int unitsInInventory) {

         inventory = new ArrayList();
         // might as well do this while we're here...
         results = new ArrayList();
         
         
        for (int i = 0; i < unitsInInventory; i++) {
            Item item = new Item();
            inventory.add(item);
        }

    } // END SET INVENTORY   
    

     public static void printResults() {
         
         
         System.out.println("!!!!!!!!!!!!!!PRINTING RESULTS!!!!!!!!!!!!!\n!!!!!!!!!!!!!PRINTING RESULTS!!!!!!!!!!!!!\n!!!!!!!!!!!!!!PRINTING RESULTS!!!!!!!!!!!!!\n!!!!!!!!!!!!!PRINTING RESULTS!!!!!!!!!!!!!");
         
         
       for(int i = 0; i < results.size(); i++){
         
         System.out.println(results.get(i));
       }  
         
         
     }

    @Override
    public void run() {
        printResults();
    }
    
    
    
} // end class
