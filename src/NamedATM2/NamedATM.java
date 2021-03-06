/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NamedATM2;
import java.io.*;
import java.util.*;
/**
 *
 * @author ml4kb
 */
public class NamedATM 
{
    int numberOfAccounts = 3;
    private Account[] myAccounts = new Account[numberOfAccounts];    
    public static void main(String[] args) throws Exception
    {
        NamedATM A = new NamedATM();
        A.loadAccounts();
        A.greeting();
        A.runATM();
    }
    
    void greeting()
    {       
        Scanner sc= new Scanner(System.in);
        System.out.println("\nWelcome, is this your first time using the ATM? (Y/N)");
        String input = sc.next();
        if(input.equalsIgnoreCase("y"))
        {
        populateAcct();
        }
        else if(input.equalsIgnoreCase("n"))
        {
        //the main method or runATM will take it to runATM
        }
        else
        {            
        System.out.println("\nPlese type in Y or N, either case is acceptable\n");
        greeting();
        }
    }
    
    void runATM() 
    {
        try
        {
           saveAccounts();
           selectAcct();
        }   
        catch(NullPointerException exc)
        {
            System.out.println("\nInvalid Input\n");    
        }   
        saveAccounts();
        greeting();
        runATM();      
    }
    
    void populateAcct()
    {
        Scanner sc = new Scanner(System.in);
        int index = -99;
        for(int i = 0; i<myAccounts.length; i++)
        {
            if(myAccounts[i] == null)
            {
               index = i;
               break;
            }
        }
        if(index !=-99)
        {
           System.out.println("\nPlease enter a name to be associated with the type of account you want");
           String name = sc.nextLine();
           for(int i = 0; i<myAccounts.length; i++)
           {
               if(myAccounts[i] != null && name.equalsIgnoreCase(myAccounts[i].getName()))
               {
                   System.out.println("\nThat name has already been used\n");
                   greeting();
                   return;                
               }
           }                  
           System.out.println("\nPlease enter a PIN to use with this account");
           String PIN = sc.nextLine();
           System.out.println("\nWhat type of account would you like to open, checking, savings, or super saver?");
           String type = sc.nextLine();

           if(type.equalsIgnoreCase("checking"))
           {
               myAccounts[index] = new Checking(100, index, name, PIN);
               System.out.println("\nBalances start at $100, and the annual interest rate is 5%");
           }   
           else if(type.equalsIgnoreCase("savings"))
           {
               myAccounts[index] = new Savings(100, index, name, PIN);
               System.out.println("\nBalances start at $100, and the annual interest rate is 30%");
           }
           else if(type.equalsIgnoreCase("super saver"))
           {
               myAccounts[index] = new SuperSaver(100, index, name, PIN);
               System.out.println("\nBalances start at $100, and the annual interest rate is 50%");
               System.out.println("In a super saver account, you can only make 12 withdrawls a year");
           }
           else
           {
               System.out.println("\nInvalid account type");
               populateAcct();
           }
        }         
        else
        {
            System.out.println("\nSorry, all availible accounts are full\n");
            greeting();
        }    
    }            
    void selectAcct()
    {
        Scanner sc = new Scanner(System.in);
        int num = -99;
        String name, PIN;
        
        try
        {
           System.out.println("\nEnter your account name");
           name = sc.nextLine();
           System.out.println("\nEnter your PIN");
           PIN = sc.nextLine();
           for(int i = 0; i<myAccounts.length; i++)
           {
               if(myAccounts[i] != null && name.equalsIgnoreCase(myAccounts[i].getName()) && PIN.equalsIgnoreCase(myAccounts[i].getPIN()))
               {
                 num = i;
               }
           }  
           System.out.println();
           myAccounts[num].Menu();
        }
        catch(ArrayIndexOutOfBoundsException exc)
        {
             System.out.println("\nInvalid Input");
             greeting();
        } 
    }   
    
    public void loadAccounts()
    {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("file1.out")))
        {
            myAccounts = (Account[]) ois.readObject();
        }
        catch(IOException | ClassNotFoundException e) 
        {
            myAccounts = new Account[numberOfAccounts]; 
        }
    }
    
    public void saveAccounts()
    {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("file1.out")))
        {
            oos.writeObject(myAccounts);
            oos.flush();
        }
        catch (Throwable e)
	{
            System.err.println(e);
        }
    }
}