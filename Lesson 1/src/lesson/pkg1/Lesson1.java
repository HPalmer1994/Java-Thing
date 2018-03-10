/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lesson.pkg1;
import java.util.Scanner;  //Library that takes input from the keyboard
/**
 *
 * @author Ben
 */
public class Lesson1 {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // Printing out to the console window
       System.out.println("Hello Youtube!"); 
       System.out.println("This works well!");
       
       // Declaring Variables
       double tuna;
       tuna = 5.28;
       System.out.println("whats the date");
       System.out.println(tuna);
       
       //Getting user input
       Scanner Ben = new Scanner(System.in); 
       System.out.println(Ben.nextLine()); 
       
       
       // Creating a Calculator
       Scanner Calculator = new Scanner(System.in);
       double fnum, snum, tnum, result;
       
       
       System.out.println("Enter the first number please");
       fnum = Calculator.nextDouble();
       System.out.println("Enter the Second number please");
       snum = Calculator.nextDouble();
       
       result = fnum + snum;
       System.out.println(result);
       
       // Math Operators
       Scanner maths = new Scanner(System.in);
       
       double girls, boys, people, places, init;
       girls = 6;
       boys = 3;
       people = girls + boys;
       places = girls * boys;
       init = girls % boys;
       
       System.out.println(people);
       System.out.println(places);
       System.out.println(init);
       
       // Increment operator
       Scanner increment = new Scanner(System.in);
       
       int nottuna =5;
       int bass = 18;
       ++tuna;
       int spicy = 12;
       spicy = spicy + 1;
       spicy *=8;
       
       System.out.println(++nottuna);
       
       //If statements 
       
       int test =6;
       
       if (test ==9)
           System.out.println("Equel");
       else
            System.out.println("Not Equel");
    
       // Logical Operators
       
       int boy1,girl1;
       boy1 = 18;
       girl1 =68;
       
       if (boy1 > 10 && girl1 < 60){
           System.out.println("Hello");
       }else{
           System.out.println("Not Hello");
       }
     
       // Switch statements 
       
       int age;
       age = 3;
       
       switch (age){
           case 1:
               System.out.println("You can crawl");
               break;
           case 2:
               System.out.println("You can talk");
               break;
           case 3:
               System.out.println("hello there");
               break;
           default:
               System.out.println("this is words of a text");
               break;
       }
               // While Loop
               
               int counter =0;
               
               while (counter < 10){
                   System.out.println(counter);
                   counter++;
               
               
                      
       }
               
               // Multiple Classes 
               // public class newclass Click on the left where it says newclass
                   
               }
               
    }
    
public class newclass{
    
}

