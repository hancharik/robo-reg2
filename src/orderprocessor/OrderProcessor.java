/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderprocessor;

import java.util.ArrayList;

/**
 *
 * @author Mark
 */
public class OrderProcessor {

    public static CashRegister register1;
    public static CashRegister register2;
    public static CashRegister register3;
    public static CashRegister register4;
    public static ArrayList<Item> inventory;
   public static BusinessAccount mainAccount;
    public static int transactions;// = 0;
   public static int totalItemsSold;
   public static int registerTransactions;
   
    public static void main(String[] args) {
        
        setInventory(100);
        registerTransactions = 100;
        mainAccount = new BusinessAccount();
       
        
        
        System.out.println("Starting inventory = " + inventory.size() + ", till = " + mainAccount.bankAccount);
        transactions = 1; // set this to one so that the first transaction is #1
        totalItemsSold = 0;
        //inventory = new ArrayList();
        
        register1 = new CashRegister(1);
        register2 = new CashRegister(2);
        register3 = new CashRegister(3);
        register4 = new CashRegister(4);
        register1.start();
        register2.start();
       register3.start();
        register4.start();
        System.out.println("Ending inventory = " + inventory.size() + ", till = " + mainAccount.bankAccount);
    } // end main

     private static void setInventory(int unitsInInventory) {

         inventory = new ArrayList();
         
        for (int i = 0; i < unitsInInventory; i++) {
            Item item = new Item();
            inventory.add(item);
        }

    } // END SET INVENTORY   
    

    
    
    
} // end class
