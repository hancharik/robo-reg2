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
public class Sale extends TransactionProcessor{
    
    
    public Sale( Customer c, ArrayList<Item>  i){
        
        super();
        
        getCustomer(c);
        setInventory(i);
        
    }
    
    public void getCustomer(Customer c){
      this.customer = c;
  }  
    
 
       public void setInventory(ArrayList<Item>  i){
      this.items = i;
  }
       
       
     
       public ArrayList getInventory(ArrayList<Item>  i){
           
      return this.items;
      
  }
       
         
       
       
       
       
         public void processOrder(){
             
      
         }
        
         
         
         
         public void updateInventory(){
      
        }
    
     public void updateCustomerBalance(){
      
        }   
    
      
    
    
}
