/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NamedATM2;

import java.text.*;
import java.util.*;

/**
 *
 * @author Michael
 */
public class SuperSaver extends Account
{
    int numOfWithdrawls;
    int yeardiff2;
    
    public SuperSaver(int bal, int num, String name, String PIN)
    {
        super(bal, num, name, PIN);
    }
    
    @Override
    protected void Withdraw()
    {
        if(yeardiff2 != 0)
        { 
            numOfWithdrawls = 0;
        }                
        if(numOfWithdrawls < 12)
        {
            Scanner sc = new Scanner(System.in);
            System.out.print("\nHow much would you like to withdraw? This will be withdrawl number " + (numOfWithdrawls + 1) + " for this year. $");
            double withdrawl = sc.nextDouble();
            if(withdrawl > 0)
            {
               if(withdrawl<=balance)
               {
                    balance -= withdrawl;
                    System.out.println("\nHere is " + NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(withdrawl));
                    numOfWithdrawls++;
                  
               }
               else
               {
                   System.out.println("\nInsufficent Funds");
               }
            }
            else
            {
                System.out.println("\nYou must enter a positive number");
            }
        }
        else
        {
            System.out.println("\nSorry, you have already made 12 withdrawls this year");
        }
    }
    
    @Override
    protected void getInterest()
    {
        int datediff = seconddate - firstdate;
        yeardiff2 = secondyear - firstyear;
        rate = .5/365;
        double ratetime = Math.pow(1+rate, datediff);
                
        if(secondyear - firstyear > 0) //allows for different years to be input
        {
            int yeardiff = secondyear - firstyear;
            ratetime *= Math.pow(1.5, yeardiff);
        }
        balance *= ratetime;
        firstdate = seconddate;
        firstyear = secondyear;
    }
}